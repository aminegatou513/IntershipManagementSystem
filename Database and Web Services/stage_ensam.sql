-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 13, 2021 at 01:41 AM
-- Server version: 5.7.31
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stage_ensam`
--

-- --------------------------------------------------------

--
-- Table structure for table `demande`
--

DROP TABLE IF EXISTS `demande`;
CREATE TABLE IF NOT EXISTS `demande` (
  `id_demande` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(250) NOT NULL,
  `nom` varchar(250) NOT NULL,
  `prenom` varchar(250) NOT NULL,
  `lieu_naiss` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `CIN` varchar(250) NOT NULL,
  `Code_Apoge` varchar(250) NOT NULL,
  `adresse` varchar(250) NOT NULL,
  `langue` varchar(250) NOT NULL,
  `logiciel` varchar(250) NOT NULL,
  `statut` varchar(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id_demande`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `demande`
--

INSERT INTO `demande` (`id_demande`, `titre`, `nom`, `prenom`, `lieu_naiss`, `email`, `CIN`, `Code_Apoge`, `adresse`, `langue`, `logiciel`, `statut`, `user_id`) VALUES
(1, 'Java', 'gatou', 'amine', 'El Jadida', 'amine.gatou98@gmail.com', 'M602387', '12345', 'Hahmqsdqsdqs', 'Francais-Anglais-Allemand-', 'Matlab-Office', 'accepted', 3),
(2, 'Conception Mecanique', 'Ezzahraoui', 'Haroun', 'Casablanca', 'haroun.ezz@gmail.com', 'B45854', '55555', 'CASA', 'Francais-Anglais', 'Matlab-Office-', 'pending', 4),
(5, 'Stage Production', 'Gatou', 'Anas', 'ElJadida', 'anas.hh02345@gmail.com', 'M8655', '8697', 'Eddd', 'Francais--Arabe--Anglais--', 'Office--', 'accepted', 5);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `role` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `role`) VALUES
(1, 'admin', 'admin.01@gmail.com', 'admin', 'admin'),
(3, 'aminegatou', 'amine.gatou98@gmail.com', 'aminegatou', 'user'),
(4, 'haroun ezzahraoui', 'haroun.ezz@gmail.com', '123456', 'user'),
(5, 'anasgatou', 'anas.gatou123@gmail.com', 'anas', 'user'),
(6, 'ElMostafa Lifi', 'mostafalifi@gmail.com', 'lifi12345', 'user');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `demande`
--
ALTER TABLE `demande`
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
