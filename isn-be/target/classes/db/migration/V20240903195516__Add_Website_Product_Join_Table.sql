IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='price_alert_website_product' AND xtype='U')
    CREATE TABLE price_alert_website_product (
        website_id UNIQUEIDENTIFIER NOT NULL,
        product_id UNIQUEIDENTIFIER NOT NULL,
        PRIMARY KEY (website_id, product_id),
        FOREIGN KEY (website_id) REFERENCES price_alert_websites(id),
        FOREIGN KEY (product_id) REFERENCES price_alert_products(id)
    );
GO
