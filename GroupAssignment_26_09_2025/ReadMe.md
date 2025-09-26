
## Group members:
### 1. TUYIZERE Elie     223007197
### 2. HABUMUGISHA Eric  223009063
### 3. NIKUBWAYO Leandre 223015716
# Screenshoots of Our Assignment Hostel Management System
## Login
<img width="308" height="265" alt="Screenshot 2025-09-26 175500" src="https://github.com/user-attachments/assets/5eb4563d-074c-4503-8dfb-aaa9b8a24ac6" />

## UserPanel
<img width="906" height="610" alt="Screenshot 2025-09-26 175901" src="https://github.com/user-attachments/assets/54ad1d28-1869-4fd7-967f-8723f3c7f4c8" />
<img width="891" height="609" alt="Screenshot 2025-09-26 180039" src="https://github.com/user-attachments/assets/6679dc62-3f51-4a86-adf8-fd1723e621fc" />

# Exported file from Database of Hostel Management System

[hostelmanagementsystem.sql](https://github.com/user-attachments/files/22565007/hostelmanagementsystem.sql)
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 26, 2025 at 04:33 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hostelmanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `BookingID` int NOT NULL AUTO_INCREMENT,
  `BookingDate` date NOT NULL,
  `Status` enum('Pending','Confirmed','Cancelled') DEFAULT 'Pending',
  `Amount` decimal(10,2) DEFAULT NULL,
  `Notes` text,
  `RoomID` int DEFAULT NULL,
  PRIMARY KEY (`BookingID`),
  KEY `RoomID` (`RoomID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `PaymentID` int NOT NULL AUTO_INCREMENT,
  `ReferenceNo` varchar(100) DEFAULT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `Date` date NOT NULL,
  `Method` enum('Cash','Card','Online','MobileMoney') DEFAULT NULL,
  `Status` enum('Pending','Paid','Failed') DEFAULT 'Pending',
  `BookingID` int DEFAULT NULL,
  PRIMARY KEY (`PaymentID`),
  UNIQUE KEY `ReferenceNo` (`ReferenceNo`),
  KEY `BookingID` (`BookingID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `RoomID` int NOT NULL AUTO_INCREMENT,
  `RoomNumber` varchar(20) NOT NULL,
  `RoomType` enum('Single','Double','Dorm') NOT NULL,
  `Capacity` int NOT NULL,
  `Status` enum('Available','Occupied','Maintenance') DEFAULT 'Available',
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UserID` int DEFAULT NULL,
  PRIMARY KEY (`RoomID`),
  UNIQUE KEY `RoomNumber` (`RoomNumber`),
  KEY `UserID` (`UserID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`RoomID`, `RoomNumber`, `RoomType`, `Capacity`, `Status`, `CreatedAt`, `UserID`) VALUES
(1, 'A12', 'Double', 2, 'Available', '2025-09-26 16:00:05', 2),
(2, 'A13', 'Double', 3, 'Available', '2025-09-26 16:00:30', 4);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) NOT NULL,
  `PasswordHash` varchar(255) NOT NULL,
  `Role` enum('Admin','Warden','Student') NOT NULL,
  `Email` varchar(150) DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username` (`Username`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Username`, `PasswordHash`, `Role`, `Email`, `CreatedAt`) VALUES
(1, 'admin1', 'pass123', 'Admin', 'admin1@hostel.com', '2025-09-26 14:29:13'),
(2, 'warden1', 'pass123', 'Warden', 'warden1@hostel.com', '2025-09-26 14:29:13'),
(3, 'student1', 'pass123', 'Student', 'student1@university.com', '2025-09-26 14:29:13'),
(4, 'eric', '123456', 'Student', 'eric@university.com', '2025-09-26 15:58:45');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
