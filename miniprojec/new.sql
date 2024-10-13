CREATE TABLE Users (
    UserID SERIAL PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    Password VARCHAR(100),
    Role VARCHAR(50),
    Phone VARCHAR(15)
);

CREATE TABLE Properties (
    PropertyID SERIAL PRIMARY KEY,
    OwnerID INT REFERENCES Users(UserID) ON DELETE CASCADE,
    Address VARCHAR(255),
    Rent NUMERIC(10, 2),
    Size INT,
    NumberOfRooms INT,
    AvailabilityStatus VARCHAR(20),
    Location VARCHAR(100)
);

select * from users;
select * from properties;

INSERT INTO Users (Name, Email, Password, Role, Phone)
VALUES ('John Doe', 'john@example2.com', 'password123', 'Owner', '123-456-7890');




select * from properties;
select * from users;
select * from Payments;
-- Table for Rental Contracts
CREATE TABLE RentalContracts (
    ContractID SERIAL PRIMARY KEY,
    PropertyID INT NOT NULL,
    UserID INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    RentAmount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Table for Payments
CREATE TABLE Payments (
    PaymentID SERIAL PRIMARY KEY,
    ContractID INT NOT NULL,
    PaymentDate DATE NOT NULL,
    AmountPaid DECIMAL(10, 2) NOT NULL,
    PaymentMethod VARCHAR(50),
    FOREIGN KEY (ContractID) REFERENCES RentalContracts(ContractID)
);

-- Table for Maintenance Requests
CREATE TABLE MaintenanceRequests (
    RequestID SERIAL PRIMARY KEY,
    PropertyID INT NOT NULL,
    RequestDate DATE NOT NULL,
    Description TEXT NOT NULL,
    Status VARCHAR(50) NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

select * from RentalContracts;
select * from Payments;
select * from MaintenanceRequests;
