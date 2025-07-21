CREATE DATABASE HP_29_DoAnTotNghiep
use HP_29_DoAnTotNghiep

CREATE TABLE Category (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Name VARCHAR(255),
    Status BIT
);

CREATE TABLE Brand (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Name VARCHAR(255),
    Note VARCHAR(255),
    Status BIT
);

CREATE TABLE Product (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Name VARCHAR(255),
    FK_Category INT,
    CONSTRAINT FK_Product_Category FOREIGN KEY (FK_Category) REFERENCES Category(ID)
);

CREATE TABLE Variation (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    FK_Product INT,
    SKU VARCHAR(255) UNIQUE,  -- Đảm bảo SKU là duy nhất
    Price DECIMAL(10, 2),
    Quantity INT,
    FK_Brand INT,
    Material VARCHAR(255),
    Weight DECIMAL(10, 2),
    Status BIT,
    CONSTRAINT FK_Variation_Product FOREIGN KEY (FK_Product) REFERENCES Product(ID),
    CONSTRAINT FK_Variation_Brand FOREIGN KEY (FK_Brand) REFERENCES Brand(ID)
);

CREATE TABLE Images (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    FK_Product INT,
    Cd_Images VARCHAR(255),
    Set_Default BIT,
    CONSTRAINT FK_Images_Product FOREIGN KEY (FK_Product) REFERENCES Product(ID)
);

CREATE TABLE Customer (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Name VARCHAR(255),
    Username VARCHAR(255) UNIQUE,  -- Đảm bảo Username là duy nhất
    Password VARCHAR(255),
    Address VARCHAR(255),
    Phone VARCHAR(255),
    Status BIT,
    Creation_date DATE,
    Edit_Date DATE
);

CREATE TABLE POSOder (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    FK_InvoiceDetail INT,
    FK_Customer INT,
    FK_User INT,
    Creation_date DATE,
    Code_Voucher UNIQUEIDENTIFIER DEFAULT NEWID(),  -- Code_Voucher là UNIQUEIDENTIFIER
    Total_Amount DECIMAL(10, 2),
    Discount_Amount DECIMAL(10, 2),
    Total_Payment DECIMAL(10, 2),
    FK_PaymentMethod INT,
    Note VARCHAR(255),
    Status BIT,
    Edit_Date DATE,
    Type_Oder INT,
    CONSTRAINT FK_POSOder_Customer FOREIGN KEY (FK_Customer) REFERENCES Customer(ID),
    CONSTRAINT FK_POSOder_User FOREIGN KEY (FK_User) REFERENCES Users(ID),
    CONSTRAINT FK_POSOder_PaymentMethod FOREIGN KEY (FK_PaymentMethod) REFERENCES PaymentMethod(ID)
);

CREATE TABLE OrderLine (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    FK_Oder INT,
    FK_Product INT,
    Quantity INT,
    Unit_Price DECIMAL(10, 2),
    Price DECIMAL(10, 2),
    Status BIT,
    Creation_date DATE,
    Edit_Date DATE,
    CONSTRAINT FK_OrderLine_Order FOREIGN KEY (FK_Oder) REFERENCES POSOder(ID),
    CONSTRAINT FK_OrderLine_Product FOREIGN KEY (FK_Product) REFERENCES Product(ID)
);

CREATE TABLE WareHouse (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Code_Inventory VARCHAR(255) UNIQUE,  -- Đảm bảo Code_Inventory là duy nhất
    FK_Staff INT,
    Creation_date DATE,
    Note VARCHAR(255),
    FK_Supplier INT,
    Status BIT,
    Edit_Date DATE,
    CONSTRAINT FK_WareHouse_Staff FOREIGN KEY (FK_Staff) REFERENCES Users(ID),
    CONSTRAINT FK_WareHouse_Supplier FOREIGN KEY (FK_Supplier) REFERENCES Supplier(ID)
);

CREATE TABLE WareHouseDetails (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    FK_Import INT,
    FK_Product INT,
    Quantity INT,
    Price DECIMAL(10, 2),
    Total_Amount DECIMAL(10, 2),
    Note VARCHAR(255),
    Status BIT,
    CONSTRAINT FK_WareHouseDetails_Import FOREIGN KEY (FK_Import) REFERENCES WareHouse(ID),
    CONSTRAINT FK_WareHouseDetails_Product FOREIGN KEY (FK_Product) REFERENCES Product(ID)
);

CREATE TABLE Supplier (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Name VARCHAR(255),
    Phone VARCHAR(255),
    Address VARCHAR(255),
    Note VARCHAR(255),
    Status BIT,
    Creation_date DATE,
    Edit_Date DATE
);

CREATE TABLE Users (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Name VARCHAR(255),
    Email VARCHAR(255) UNIQUE,  -- Đảm bảo Email là duy nhất
    Username VARCHAR(255) UNIQUE,  -- Đảm bảo Username là duy nhất
    Password VARCHAR(255),
    FK_Role INT,
    IsActive BIT,
    Creation_date DATE,
    Edit_Date DATE,
    CONSTRAINT FK_Users_Role FOREIGN KEY (FK_Role) REFERENCES Role(ID)
);

CREATE TABLE Role (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    CanCreate BIT,
    CanUpdate BIT,
    CanDelete BIT
);

CREATE TABLE Voucher (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Code UNIQUEIDENTIFIER DEFAULT NEWID(),  -- Code là UNIQUEIDENTIFIER
    DiscountValue DECIMAL(10, 2),
    DiscountAmount DECIMAL(10, 2),
    StartDate DATE,
    EndDate DATE,
    TypeVoucher VARCHAR(255),
    Status BIT,
    Creation_date DATE,
    Edit_Date DATE
);

CREATE TABLE PaymentMethod (
    ID INT IDENTITY(1,1) PRIMARY KEY,  -- ID tự tăng
    Type VARCHAR(255),
    Note VARCHAR(255),
    Status BIT
);
