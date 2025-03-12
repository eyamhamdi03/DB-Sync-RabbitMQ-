-- Insert sample data for BO1_DB
USE BO1_DB;
INSERT INTO product_sales (sale_date, region, product, qty, cost, amt, tax, total) VALUES
('10-October-2023', 'North', 'Laptop', 5, 800.00, 4000.00, 320.00, 4320.00),
('11-October-2023', 'West', 'Monitor', 3, 150.00, 450.00, 36.00, 486.00),
('12-October-2023', 'East', 'Keyboard', 10, 25.00, 250.00, 20.00, 270.00),
('13-October-2023', 'South', 'Mouse', 8, 10.00, 80.00, 6.40, 86.40),
('14-October-2023', 'Central', 'Printer', 2, 200.00, 400.00, 32.00, 432.00),
('15-October-2023', 'North', 'Tablet', 6, 300.00, 1800.00, 144.00, 1944.00);

-- Insert sample data for BO2_DB
USE BO2_DB;
INSERT INTO product_sales (sale_date, region, product, qty, cost, amt, tax, total) VALUES
('10-October-2023', 'East', 'Server', 2, 2500.00, 5000.00, 400.00, 5400.00),
('11-October-2023', 'West', 'Router', 4, 120.00, 480.00, 38.40, 518.40),
('12-October-2023', 'Central', 'Switch', 5, 80.00, 400.00, 32.00, 432.00),
('13-October-2023', 'South', 'Cables', 20, 5.00, 100.00, 8.00, 108.00),
('14-October-2023', 'North', 'Software', 3, 150.00, 450.00, 36.00, 486.00),
('15-October-2023', 'West', 'Headset', 7, 45.00, 315.00, 25.20, 340.20);