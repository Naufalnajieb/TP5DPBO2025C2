-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 26, 2025 at 09:10 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_mahasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `ID` int(11) NOT NULL,
  `nim` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `agama` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`ID`, `nim`, `nama`, `jenis_kelamin`, `agama`) VALUES
(1, '2311119', 'Muhammad Radhi Maulana', 'Laki-laki', 'Islam'),
(2, '2304879', 'Muhammad Alvinza', 'Laki-laki', 'Islam'),
(3, '2301093', 'Marco Henrik Abineno', 'Laki-laki', 'Kristen'),
(4, '2301410', 'Nuansa Bening Aura Jelita', 'Perempuan', 'Islam'),
(5, '2305274', 'Zakiyah Hasanah', 'Perempuan', 'Islam'),
(6, '2310978', 'Haniel Septian Putra Alren', 'Laki-laki', 'Kristen'),
(7, '2300330', 'Muhammad Fathan Putra', 'Laki-laki', 'Islam'),
(8, '2304934', 'Zaki Adam', 'Laki-laki', 'Islam'),
(9, '2300484', 'Julian Dwi Satrio', 'Laki-laki', 'Islam'),
(10, '2301102', 'Abdurrahman Rauf Budiman', 'Laki-laki', 'Islam'),
(11, '2312120', 'Natasha Adinda Cantika', 'Perempuan', 'Islam'),
(12, '2304330', 'Hanif Ahmad Syauqi', 'Laki-laki', 'Islam'),
(13, '2304742', 'Muhammad Akhtar Rizki Ramadha', 'Laki-laki', 'Islam'),
(14, '2304820', 'Kasyful Haq Bachariputra', 'Laki-laki', 'Islam'),
(15, '2310850', 'Muhammad Naufal Arbanin', 'Laki-laki', 'Islam'),
(16, '2301579', 'Jovan Rius Hulus', 'Laki-laki', 'Kristen'),
(17, '2309357', 'Meisya Amalia', 'Perempuan', 'Islam');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
