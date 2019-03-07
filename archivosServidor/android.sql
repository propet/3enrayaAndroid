-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 08, 2019 at 12:45 AM
-- Server version: 5.7.25-0ubuntu0.18.04.2
-- PHP Version: 7.2.15-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `android`
--

-- --------------------------------------------------------

--
-- Table structure for table `mensaje`
--

CREATE TABLE `mensaje` (
  `id_mensaje` int(11) NOT NULL,
  `id_usuario1` int(11) NOT NULL,
  `id_usuario2` int(11) NOT NULL,
  `mensaje` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(10) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `pass` varchar(25) DEFAULT NULL,
  `ELO` int(6) NOT NULL,
  `conectado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `pass`, `ELO`, `conectado`) VALUES
(1, 'GUEST_1', NULL, 0, 0),
(2, 'GUEST_2', NULL, 0, 0),
(3, 'GUEST_3', NULL, 0, 0),
(4, 'manu', 'manu', 0, 0),
(5, 'luis', 'luis', 0, 0),
(6, 'GUEST_6', NULL, 0, 0),
(7, 'GUEST_7', NULL, 0, 0),
(8, 'pepe', 'pepe', 0, 0),
(9, 'GUEST_9', NULL, 0, 0),
(10, 'GUEST_10', NULL, 0, 0),
(11, 'GUEST_11', NULL, 0, 0),
(12, 'GUEST_12', NULL, 0, 0),
(13, 'GUEST_13', NULL, 0, 0),
(14, 'GUEST_14', NULL, 0, 0),
(15, 'GUEST_15', NULL, 0, 0),
(16, 'GUEST_16', NULL, 0, 0),
(17, 'GUEST_17', NULL, 0, 0),
(18, 'GUEST_18', NULL, 0, 0),
(19, 'GUEST_19', NULL, 0, 0),
(20, 'GUEST_20', NULL, 0, 0),
(21, 'GUEST_21', NULL, 0, 0),
(22, 'GUEST_22', NULL, 0, 0),
(23, 'GUEST_23', NULL, 0, 0),
(24, 'GUEST_24', NULL, 0, 0),
(25, 'GUEST_25', NULL, 0, 0),
(26, 'GUEST_26', NULL, 0, 0),
(27, 'GUEST_27', NULL, 0, 0),
(28, 'GUEST_28', NULL, 0, 0),
(29, 'GUEST_29', NULL, 0, 0),
(30, 'GUEST_30', NULL, 0, 0),
(31, 'GUEST_31', NULL, 0, 0),
(32, 'GUEST_32', NULL, 0, 0),
(33, 'GUEST_33', NULL, 0, 0),
(34, 'GUEST_34', NULL, 0, 0),
(35, 'GUEST_35', NULL, 0, 0),
(36, 'GUEST_36', NULL, 0, 0),
(37, 'GUEST_37', NULL, 0, 0),
(38, 'GUEST_38', NULL, 0, 0),
(39, 'GUEST_39', NULL, 0, 0),
(40, 'GUEST_40', NULL, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id_mensaje`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mensaje`
--
ALTER TABLE `mensaje`
  MODIFY `id_mensaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
