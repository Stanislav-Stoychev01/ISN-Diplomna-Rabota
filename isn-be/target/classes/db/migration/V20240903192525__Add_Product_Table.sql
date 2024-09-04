IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='price_alert_products' AND xtype='U')
    CREATE TABLE price_alert_products (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        name NVARCHAR NOT NULL UNIQUE
    );
GO
