-- 1. Create Database
CREATE DATABASE PropertyRentalManagement;
GO

-- 2. Use the Database
USE PropertyRentalManagement;
GO

-- 3. Create Users Table (Admin, Owners, Tenants)
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    Name NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Password NVARCHAR(100) NOT NULL,
    Role NVARCHAR(50) CHECK (Role IN ('Admin', 'Owner', 'Tenant')),
    Phone NVARCHAR(15),
    CONSTRAINT CK_Users_Role CHECK (Role IN ('Admin', 'Owner', 'Tenant'))
);
GO

-- 4. Create Properties Table (Property details with foreign key reference to Users table)
CREATE TABLE Properties (
    PropertyID INT PRIMARY KEY IDENTITY(1,1),
    OwnerID INT FOREIGN KEY REFERENCES Users(UserID) ON DELETE NO ACTION, -- Changed from ON DELETE CASCADE to ON DELETE NO ACTION
    Address NVARCHAR(255) NOT NULL,
    Rent DECIMAL(10, 2) NOT NULL,
    Size INT NOT NULL, -- in square feet
    NumberOfRooms INT NOT NULL,
    AvailabilityStatus NVARCHAR(20) CHECK (AvailabilityStatus IN ('Available', 'Rented')),
    Location NVARCHAR(100) NOT NULL
);
GO

-- 5. Create RentalContracts Table (Contract details for renting properties)
CREATE TABLE RentalContracts (
    ContractID INT PRIMARY KEY IDENTITY(1,1),
    TenantID INT FOREIGN KEY REFERENCES Users(UserID) ON DELETE NO ACTION, -- Set ON DELETE NO ACTION to prevent multiple cascade paths
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID) ON DELETE NO ACTION, -- Set ON DELETE NO ACTION to prevent multiple cascade paths
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    RentAmount DECIMAL(10, 2) NOT NULL
);
GO

-- 6. Create Payments Table (Payments made by tenants for rented properties)
CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY IDENTITY(1,1),
    TenantID INT FOREIGN KEY REFERENCES Users(UserID) ON DELETE NO ACTION, -- Set ON DELETE NO ACTION to prevent multiple cascade paths
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID) ON DELETE NO ACTION, -- Set ON DELETE NO ACTION to prevent multiple cascade paths
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentDate DATE NOT NULL
);
GO

-- 7. Create MaintenanceRequests Table (Requests for repairs and maintenance)
CREATE TABLE MaintenanceRequests (
    RequestID INT PRIMARY KEY IDENTITY(1,1),
    TenantID INT FOREIGN KEY REFERENCES Users(UserID) ON DELETE NO ACTION, -- Set ON DELETE NO ACTION to prevent multiple cascade paths
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID) ON DELETE NO ACTION, -- Set ON DELETE NO ACTION to prevent multiple cascade paths
    Description NVARCHAR(255) NOT NULL,
    Status NVARCHAR(50) CHECK (Status IN ('Pending', 'Resolved'))
);
GO


INSERT INTO Users (Name, Email, Password, Role, Phone)
VALUES ('John Doe', 'john.doe@example.com', 'password123', 'Owner', '1234567890');

INSERT INTO Properties (OwnerID, Address, Rent, Size, NumberOfRooms, AvailabilityStatus, Location)
VALUES (1, '123 Main St', 1500.00, 1500, 3, 'Available', 'New York');

INSERT INTO Users (Name, Email, Password, Role, Phone)
VALUES ('Jane Smith', 'jane.smith@example.com', 'password123', 'Tenant', '0987654321');

INSERT INTO RentalContracts (TenantID, PropertyID, StartDate, EndDate, RentAmount)
VALUES (2, 1, '2024-10-01', '2025-09-30', 1500.00);

INSERT INTO Payments (TenantID, PropertyID, Amount, PaymentDate)
VALUES (2, 1, 1500.00, '2024-10-01');

INSERT INTO MaintenanceRequests (TenantID, PropertyID, Description, Status)
VALUES (2, 1, 'Fixing the window lock', 'Pending');

SELECT * FROM Properties 
WHERE Location = 'New York' AND Rent BETWEEN 1000 AND 2000 AND AvailabilityStatus = 'Available';

SELECT * FROM Users 
WHERE Role = 'Tenant' AND (Name LIKE '%Jane%' OR Email LIKE '%example.com%');

SELECT * FROM RentalContracts 
WHERE PropertyID = 1 OR TenantID = 2;

DELETE FROM Properties WHERE PropertyID = 1;

DELETE FROM Users WHERE UserID = 2 AND Role = 'Tenant';

DELETE FROM RentalContracts WHERE ContractID = 1;



-- 1. Insert sample data into Users (Admin, Owners, Tenants)
INSERT INTO Users (Name, Email, Password, Role, Phone)
VALUES 
    ('Alice Smith', 'alice.smith@example.com', 'ownerpass123', 'Owner', '321-654-9870'), 
    ('Bob Johnson', 'bob.johnson@example.com', 'tenantpass123', 'Tenant', '213-546-8790'),
    ('Emily Davis', 'emily.davis@example.com', 'tenantpass456', 'Tenant', '123-789-6540');

-- 2. Insert sample data into Properties
INSERT INTO Properties (OwnerID, Address, Rent, Size, NumberOfRooms, AvailabilityStatus, Location)
VALUES 
    (2, '123 Main St, Cityville', 1500.00, 1200, 3, 'Available', 'Cityville'),
    (2, '456 Oak Ave, Townland', 1800.00, 1500, 4, 'Rented', 'Townland');

-- 3. Insert sample data into RentalContracts
INSERT INTO RentalContracts (TenantID, PropertyID, StartDate, EndDate, RentAmount)
VALUES 
(4, 2, '2024-01-01', '2025-01-01', 1800.00);

-- 4. Insert sample data into Payments
INSERT INTO Payments (TenantID, PropertyID, Amount, PaymentDate)
VALUES 
    (4, 2, 1800.00, '2024-01-05'),  
    (4, 2, 1800.00, '2024-02-05');

-- 5. Insert sample data into MaintenanceRequests
INSERT INTO MaintenanceRequests (TenantID, PropertyID, Description, Status)
VALUES 
    (4, 2, 'Leaky faucet in the kitchen', 'Pending'),
    (4, 2, 'Broken window in the living room', 'Resolved');

	SELECT * FROM Properties
	SELECT * FROM Users 
	SELECT * FROM RentalContracts 
	SELECT * FROM Payments 
	SELECT * FROM MaintenanceRequests 
