CREATE DATABASE bank_db;

USE bank_db;

CREATE DATABASE restaurant_db;
use restaurant_db;

-- Users Table
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    currentBalance DECIMAL(10, 2) DEFAULT 0
);

-- Transactions Table
CREATE TABLE Transactions (
	transactions_id INT PRIMARY KEY AUTO_INCREMENT,
    transactions_type varchar(50),
    transactions_amount decimal(50,2),
    transactions_date DATE,
    transactions_time TIME,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- ReservedTables Table
CREATE TABLE ReservedTables (
    table_id INT AUTO_INCREMENT PRIMARY KEY,
    table_reservation TIMESTAMP,
    user_id INT,
    transaction_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (transaction_id) REFERENCES Transactions(transactions_id)
);

-- Menu Table
CREATE TABLE Menu (
    menu_id INT AUTO_INCREMENT PRIMARY KEY,
    menu_name VARCHAR(255) NOT NULL,
    menu_price DECIMAL(10, 2) NOT NULL,
    menu_stock INT NOT NULL,
    user_id INT,
    table_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (table_id) REFERENCES ReservedTables(table_id)
);



INSERT INTO users (id, username, password, currentBalance)
VALUES (1,"Hakim", "HiKim", 100000);

use restaurant_db;
SELECT * FROM Users;
SELECT * FROM menu;
SELECT * FROM reserved_tables;
SELECT * FROM transactions;
