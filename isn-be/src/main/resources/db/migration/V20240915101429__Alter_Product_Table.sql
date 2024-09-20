ALTER TABLE price_alert_products
ADD
    price DECIMAL(7, 2) NULL,
    imagePath NVARCHAR(1024) NOT NULL UNIQUE