IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='price_alert_websites' AND xtype='U')
    CREATE TABLE price_alert_websites (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        url NVARCHAR(1024) NOT NULL UNIQUE,
    );
GO
