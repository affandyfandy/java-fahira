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

-- CREATE TABLE REVENUE_REPORT
CREATE TABLE revenue_report (
    id INT AUTO_INCREMENT PRIMARY KEY,
    year INT NOT NULL,
    month INT NULL,
    day INT NULL,
    amount DECIMAL(10, 2) NOT NULL
);

-- Procedure to calculate and store daily revenue
DELIMITER //
CREATE PROCEDURE calculate_and_store_daily_revenue(day_of_year INT)
BEGIN
    DECLARE day_revenue DECIMAL(10, 2);
    
    SELECT SUM(amount) INTO day_revenue
    FROM invoice
    WHERE DAYOFYEAR(created_date) = day_of_year;
    
    INSERT INTO revenue_report (year, month, day, amount)
    VALUES (YEAR(CURDATE()), NULL, day_of_year, day_revenue);
END//
DELIMITER ;

-- Procedure to calculate and store monthly revenue
DELIMITER //
CREATE PROCEDURE calculate_and_store_monthly_revenue(month_of_year INT)
BEGIN
    DECLARE month_revenue DECIMAL(10, 2);
    
    SELECT SUM(amount) INTO month_revenue
    FROM invoice
    WHERE MONTH(created_date) = month_of_year AND YEAR(created_date) = YEAR(CURDATE());
    
    INSERT INTO revenue_report (year, month, day, amount)
    VALUES (YEAR(CURDATE()), month_of_year, NULL, month_revenue);
END//
DELIMITER ;

-- Procedure to calculate and store yearly revenue
DELIMITER //
CREATE PROCEDURE calculate_and_store_yearly_revenue(year_of_year INT)
BEGIN
    DECLARE year_revenue DECIMAL(10, 2);
    
    SELECT SUM(amount) INTO year_revenue
    FROM invoice
    WHERE YEAR(created_date) = year_of_year;
    
    INSERT INTO revenue_report (year, month, day, amount)
    VALUES (year_of_year, NULL, NULL, year_revenue);
END//
DELIMITER ;