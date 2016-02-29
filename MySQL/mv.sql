-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 25. Feb 2016 um 11:51
-- Server Version: 5.5.16
-- PHP-Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `mv`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `medien`
--

CREATE TABLE IF NOT EXISTS `medien` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` text COLLATE utf8_unicode_ci NOT NULL,
  `Typ` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Daten für Tabelle `medien`
--

INSERT INTO `medien` (`ID`, `Name`, `Typ`) VALUES
(1, 'R105', 'PCRaum'),
(2, 'Beamer 1', 'Beamer'),
(3, 'Beamer 2', 'Beamer'),
(4, 'R115', 'SmartBoard');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` text COLLATE utf8_unicode_ci NOT NULL,
  `Passwort` text COLLATE utf8_unicode_ci NOT NULL,
  `Admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`ID`, `Name`, `Passwort`, `Admin`) VALUES
(1, 'Stallkamp', 'esel', 1),
(2, 'Fritsch', 'hallo', 1),
(3, 'Gutsche', 'info', 0),
(4, 'Halm', 'dumm', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `verwaltung`
--

CREATE TABLE IF NOT EXISTS `verwaltung` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `MedienID` int(11) NOT NULL,
  `Datum` date NOT NULL,
  `Stunde` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `verwaltung`
--

INSERT INTO `verwaltung` (`ID`, `UserID`, `MedienID`, `Datum`, `Stunde`, `timestamp`) VALUES
(1, 1, 1, '2016-02-12', 3, '2016-02-11 11:17:18'),
(2, 2, 3, '2016-02-09', 5, '2016-02-25 10:50:23'),
(3, 3, 1, '2016-02-04', 2, '2016-02-25 10:50:23');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
