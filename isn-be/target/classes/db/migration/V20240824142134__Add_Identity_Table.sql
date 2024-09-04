IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='price_alert_users' AND xtype='U')
    CREATE TABLE price_alert_users (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        email NVARCHAR(255) NOT NULL UNIQUE,
        first_name NVARCHAR(32) NOT NULL,
        last_name NVARCHAR(32) NOT NULL,
        username NVARCHAR(64) NOT NULL UNIQUE,
        password NVARCHAR(72) NOT NULL,
    );
GO