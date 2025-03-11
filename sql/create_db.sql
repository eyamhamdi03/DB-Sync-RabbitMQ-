CREATE DATABASE IF NOT EXISTS HO_DB;
USE HO_DB;
CREATE TABLE product_sales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100),
    quantity INT,
    sale_date DATE,
    synced BOOLEAN DEFAULT FALSE
);