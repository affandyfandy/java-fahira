-- CREATE TABLE CUSTOMER
CREATE TABLE customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20)
);

-- CREATE TABLE CASHIER
CREATE TABLE cashier (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

-- CREATE TABLE INVOICE
CREATE TABLE invoice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    cashier_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (cashier_id) REFERENCES cashier(id)
);

-- CREATE TABLE PRODUCT
CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- CREATE TABLE INVOICE DETAIL
CREATE TABLE invoice_detail (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quantity INT NOT NULL,
    product_id INT NOT NULL,
    product_price DECIMAL(10, 2) NOT NULL,
    invoice_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoice(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);