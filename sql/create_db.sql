-- Drop existing databases if they exist
DROP DATABASE IF EXISTS HO_DB;
DROP DATABASE IF EXISTS BO1_DB;
DROP DATABASE IF EXISTS BO2_DB;

-- Create HO_DB
CREATE DATABASE HO_DB;
USE HO_DB;
CREATE TABLE product_sales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sale_date VARCHAR(20),  -- Format: DD-MonthName-YYYY
    region VARCHAR(50),
    product VARCHAR(100),
    qty INT,
    cost DECIMAL(10,2),
    amt DECIMAL(10,2),
    tax DECIMAL(10,2),
    total DECIMAL(10,2),
);

-- Create BO1_DB
CREATE DATABASE BO1_DB;
USE BO1_DB;
CREATE TABLE product_sales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sale_date VARCHAR(20),
    region VARCHAR(50),
    product VARCHAR(100),
    qty INT,
    cost DECIMAL(10,2),
    amt DECIMAL(10,2),
    tax DECIMAL(10,2),
    total DECIMAL(10,2),
    synced BOOLEAN DEFAULT FALSE
);

-- Create BO2_DB
CREATE DATABASE BO2_DB;
USE BO2_DB;
CREATE TABLE product_sales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sale_date VARCHAR(20),
    region VARCHAR(50),
    product VARCHAR(100),
    qty INT,
    cost DECIMAL(10,2),
    amt DECIMAL(10,2),
    tax DECIMAL(10,2),
    total DECIMAL(10,2),
    synced BOOLEAN DEFAULT FALSE
);