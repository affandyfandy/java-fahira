-- MOCK DATA
INSERT INTO customer (name, phone) VALUES
    ('Kelly Wakasa', '123-456-7890'),
    ('Luke Eich', '987-654-3210'),
    ('Michael Jordan', '555-123-4567');

INSERT INTO cashier (name) VALUES
    ('Kobe'),
    ('Jude');

INSERT INTO product (name, price) VALUES
    ('Television', 100.99),
    ('Computer', 12.49),
    ('Printer', 5.99);

INSERT INTO invoice (customer_id, cashier_id, amount, created_date) VALUES
    (1, 1, 215.96, '2024-06-28'),
    (2, 2, 37.47, '2024-06-28'), 
    (3, 1, 145.92, '2024-06-27'); 

INSERT INTO invoice_detail (quantity, product_id, product_price, invoice_id, amount) VALUES
    (2, 1, 100.99, 1, 201.98),
    (1, 3, 5.99, 1, 5.99); 

INSERT INTO invoice_detail (quantity, product_id, product_price, invoice_id, amount) VALUES
    (3, 2, 12.49, 2, 37.47); 

INSERT INTO invoice_detail (quantity, product_id, product_price, invoice_id, amount) VALUES
    (2, 1, 100.99, 3, 201.98),
    (1, 2, 12.49, 3, 12.49); 

-- CREATE VIEW customer_product_list
CREATE VIEW customer_product_list AS
SELECT
    c.id AS customer_id,
    c.name AS customer_name,
    p.id AS product_id,
    p.name AS product_name,
    id.quantity AS quantity,
    id.amount AS amount,
    i.created_date AS created_date
FROM
    invoice_detail id
JOIN
    invoice i ON id.invoice_id = i.id
JOIN
    customer c ON i.customer_id = c.id
JOIN
    product p ON id.product_id = p.id;

-- CREATE FUNCTION calculate_revenue_by_cashier
DROP FUNCTION IF EXISTS calculate_revenue_by_cashier;

DELIMITER //
CREATE FUNCTION calculate_revenue_by_cashier(cashier_id INT) RETURNS DECIMAL(10, 2)
BEGIN
    DECLARE total_revenue DECIMAL(10, 2);

    SELECT COALESCE(SUM(id.amount), 0.00)
    INTO total_revenue
    FROM invoice_detail id
    JOIN invoice i ON id.invoice_id = i.id
    WHERE i.cashier_id = cashier_id;

    RETURN total_revenue;

END //
DELIMITER ;

-- CREATE TABLE REVENUE_REPORT
CREATE TABLE revenue_report (
    id INT AUTO_INCREMENT PRIMARY KEY,
    year INT,
    month INT,
    day INT,
    amount DECIMAL(10, 2)
);

-- Procedure to calculate and store daily revenue
DELIMITER //
CREATE PROCEDURE calculate_daily_revenue(day DATE)
BEGIN
    DECLARE day_revenue DECIMAL(10, 2);
    SELECT SUM(amount) INTO day_revenue
    FROM invoice
    WHERE DATE(created_date) = day;
    INSERT INTO revenue_report (year, month, day, amount)
    VALUES (YEAR(day), MONTH(day), DAY(day), day_revenue);
END//

-- Procedure to calculate and store monthly revenue
CREATE PROCEDURE calculate_monthly_revenue(month DATE)
BEGIN
    DECLARE month_revenue DECIMAL(10, 2);
    SELECT SUM(amount) INTO month_revenue
    FROM invoice
    WHERE YEAR(created_date) = YEAR(month) AND MONTH(created_date) = MONTH(month);
    INSERT INTO revenue_report (year, month, day, amount)
    VALUES (YEAR(month), MONTH(month), NULL, month_revenue);
END//

-- Procedure to calculate and store yearly revenue
CREATE PROCEDURE calculate_yearly_revenue(IN report_year INT)
BEGIN
    DECLARE year_revenue DECIMAL(10, 2);
    SELECT COALESCE(SUM(amount), 0) INTO year_revenue
    FROM invoice
    WHERE YEAR(created_date) = report_year;
    INSERT INTO revenue_report (year, month, day, amount)
    VALUES (YEAR(year), NULL, NULL, year_revenue);
END//
DELIMITER ;