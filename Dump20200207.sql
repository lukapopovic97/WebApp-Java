CREATE DATABASE  IF NOT EXISTS `hibernateproj` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `hibernateproj`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: hibernateproj
-- ------------------------------------------------------
-- Server version	5.7.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `anketa`
--

DROP TABLE IF EXISTS `anketa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `anketa` (
  `id` int(11) NOT NULL,
  `autorUsername` varchar(255) DEFAULT NULL,
  `datumKraja` datetime DEFAULT NULL,
  `datumPocetka` datetime DEFAULT NULL,
  `naziv` varchar(255) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `personalizovana` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anketa`
--

LOCK TABLES `anketa` WRITE;
/*!40000 ALTER TABLE `anketa` DISABLE KEYS */;
INSERT INTO `anketa` VALUES (3,'autor','2020-02-15 00:00:00','2020-02-04 00:00:00','Prva anketa','opis',_binary '\0'),(4,'autor','2020-02-28 00:00:00','2020-02-03 00:00:00','Druga anketa','opis',_binary '');
/*!40000 ALTER TABLE `anketa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anketa_pitanjeanketa`
--

DROP TABLE IF EXISTS `anketa_pitanjeanketa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `anketa_pitanjeanketa` (
  `ankete_id` int(11) NOT NULL,
  `pitanja_id` int(11) NOT NULL,
  KEY `FK_56r6ddfegyq840318nk9owd6` (`pitanja_id`),
  KEY `FK_lpwx8c07x2c92f2fj5unqjcmp` (`ankete_id`),
  CONSTRAINT `FK_56r6ddfegyq840318nk9owd6` FOREIGN KEY (`pitanja_id`) REFERENCES `pitanjeanketa` (`id`),
  CONSTRAINT `FK_lpwx8c07x2c92f2fj5unqjcmp` FOREIGN KEY (`ankete_id`) REFERENCES `anketa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anketa_pitanjeanketa`
--

LOCK TABLES `anketa_pitanjeanketa` WRITE;
/*!40000 ALTER TABLE `anketa_pitanjeanketa` DISABLE KEYS */;
INSERT INTO `anketa_pitanjeanketa` VALUES (3,1),(3,2),(3,3),(3,4),(4,1),(4,4);
/*!40000 ALTER TABLE `anketa_pitanjeanketa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anketaodgovori`
--

DROP TABLE IF EXISTS `anketaodgovori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `anketaodgovori` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAnkete` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anketaodgovori`
--

LOCK TABLES `anketaodgovori` WRITE;
/*!40000 ALTER TABLE `anketaodgovori` DISABLE KEYS */;
INSERT INTO `anketaodgovori` VALUES (1,3,'pera');
/*!40000 ALTER TABLE `anketaodgovori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anketaodgovori_odgovoranketa`
--

DROP TABLE IF EXISTS `anketaodgovori_odgovoranketa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `anketaodgovori_odgovoranketa` (
  `AnketaOdgovori_id` int(11) NOT NULL,
  `odgovori_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ib6npg7gvndvqcxphnx6i2lwx` (`odgovori_id`),
  KEY `FK_7s6e85j9btp769vqa9jrtw0h4` (`AnketaOdgovori_id`),
  CONSTRAINT `FK_7s6e85j9btp769vqa9jrtw0h4` FOREIGN KEY (`AnketaOdgovori_id`) REFERENCES `anketaodgovori` (`id`),
  CONSTRAINT `FK_ib6npg7gvndvqcxphnx6i2lwx` FOREIGN KEY (`odgovori_id`) REFERENCES `odgovoranketa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anketaodgovori_odgovoranketa`
--

LOCK TABLES `anketaodgovori_odgovoranketa` WRITE;
/*!40000 ALTER TABLE `anketaodgovori_odgovoranketa` DISABLE KEYS */;
INSERT INTO `anketaodgovori_odgovoranketa` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `anketaodgovori_odgovoranketa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('upitnici',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnici`
--

DROP TABLE IF EXISTS `korisnici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `korisnici` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jmbg` varchar(255) DEFAULT NULL,
  `datum_rodjenja` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `mesto_rodjenja` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `telefon` varchar(255) DEFAULT NULL,
  `tip` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnici`
--

LOCK TABLES `korisnici` WRITE;
/*!40000 ALTER TABLE `korisnici` DISABLE KEYS */;
INSERT INTO `korisnici` VALUES (1,'1111111111111','1997-02-26','admin@gmail.com','Admin','Beograd','Sifra123.','Admin','066123456','admin','admin'),(2,'2222222222222','1998-05-05','autor@gmail.com','Autor','Beograd','Sifra123.','Autor','060123123','autor','autor'),(3,'3333333333333','1991-01-01','autor@gmail.com','Autor','Beograd','Sifra123.','Autor','061222222','autor','a'),(4,'2020202020202','2000-02-01','pera@gmail.com','Pera','Pirot','Sifra123.','Peric','2020202020','ispitanik','pera');
/*!40000 ALTER TABLE `korisnici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `odgovoranketa`
--

DROP TABLE IF EXISTS `odgovoranketa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `odgovoranketa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPitanja` int(11) NOT NULL,
  `odgovori` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `odgovoranketa`
--

LOCK TABLES `odgovoranketa` WRITE;
/*!40000 ALTER TABLE `odgovoranketa` DISABLE KEYS */;
INSERT INTO `odgovoranketa` VALUES (1,1,'odg'),(2,2,'odg'),(3,3,'1'),(4,4,'b#c#');
/*!40000 ALTER TABLE `odgovoranketa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `odgovortest`
--

DROP TABLE IF EXISTS `odgovortest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `odgovortest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPitanja` int(11) NOT NULL,
  `maxPoeni` double NOT NULL,
  `odgovori` varchar(255) DEFAULT NULL,
  `poeni` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `odgovortest`
--

LOCK TABLES `odgovortest` WRITE;
/*!40000 ALTER TABLE `odgovortest` DISABLE KEYS */;
INSERT INTO `odgovortest` VALUES (1,1,3,'tacan',3),(2,2,3,'2',3),(3,3,3,'tacan',3),(4,4,3,'tacan1#netacan2#tacan2#',1.5);
/*!40000 ALTER TABLE `odgovortest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pitanjeanketa`
--

DROP TABLE IF EXISTS `pitanjeanketa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pitanjeanketa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `obavezno` bit(1) NOT NULL,
  `ponudjeni_odgovori` varchar(255) DEFAULT NULL,
  `tekst_pitanja` varchar(255) DEFAULT NULL,
  `tip` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pitanjeanketa`
--

LOCK TABLES `pitanjeanketa` WRITE;
/*!40000 ALTER TABLE `pitanjeanketa` DISABLE KEYS */;
INSERT INTO `pitanjeanketa` VALUES (1,_binary '',NULL,'Prvo',1),(2,_binary '\0',NULL,'Drugo',3),(3,_binary '\0','1#2#','Trece',4),(4,_binary '\0','a#b#c#d#','Cetvrto',5);
/*!40000 ALTER TABLE `pitanjeanketa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pitanjetest`
--

DROP TABLE IF EXISTS `pitanjetest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pitanjetest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `netacni_odgovori` varchar(255) DEFAULT NULL,
  `poeni` double DEFAULT NULL,
  `tacni_odgovori` varchar(255) DEFAULT NULL,
  `tekst_pitanja` varchar(255) DEFAULT NULL,
  `tip` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pitanjetest`
--

LOCK TABLES `pitanjetest` WRITE;
/*!40000 ALTER TABLE `pitanjetest` DISABLE KEYS */;
INSERT INTO `pitanjetest` VALUES (1,NULL,3,'tacan#','Prvo',1),(2,NULL,3,'2#','Drugo',2),(3,'netacan#',3,'tacan#','Trece',4),(4,'netacan1#netacan2#',3,'tacan1#tacan2#','Cetrvto',5);
/*!40000 ALTER TABLE `pitanjetest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `test` (
  `id` int(11) NOT NULL,
  `autorUsername` varchar(255) DEFAULT NULL,
  `datumKraja` datetime DEFAULT NULL,
  `datumPocetka` datetime DEFAULT NULL,
  `naziv` varchar(255) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `trajanje` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'autor','2020-02-21 00:00:00','2020-02-05 00:00:00','Prvi test','opis prvog testa',1),(2,'autor','2020-02-21 00:00:00','2020-02-10 00:00:00','Drugi test','opis drugog testa',1);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_pitanjetest`
--

DROP TABLE IF EXISTS `test_pitanjetest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `test_pitanjetest` (
  `testovi_id` int(11) NOT NULL,
  `pitanja_id` int(11) NOT NULL,
  KEY `FK_5bxr6f2f0jj2gxs4bgi7r129i` (`pitanja_id`),
  KEY `FK_py4fs5a2873u4iceayb6pf5nn` (`testovi_id`),
  CONSTRAINT `FK_5bxr6f2f0jj2gxs4bgi7r129i` FOREIGN KEY (`pitanja_id`) REFERENCES `pitanjetest` (`id`),
  CONSTRAINT `FK_py4fs5a2873u4iceayb6pf5nn` FOREIGN KEY (`testovi_id`) REFERENCES `test` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_pitanjetest`
--

LOCK TABLES `test_pitanjetest` WRITE;
/*!40000 ALTER TABLE `test_pitanjetest` DISABLE KEYS */;
INSERT INTO `test_pitanjetest` VALUES (1,1),(1,2),(1,3),(1,4),(2,1),(2,2),(2,3),(2,4);
/*!40000 ALTER TABLE `test_pitanjetest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testodgovori`
--

DROP TABLE IF EXISTS `testodgovori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `testodgovori` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTesta` int(11) NOT NULL,
  `maxPoeni` double NOT NULL,
  `poeni` double NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testodgovori`
--

LOCK TABLES `testodgovori` WRITE;
/*!40000 ALTER TABLE `testodgovori` DISABLE KEYS */;
INSERT INTO `testodgovori` VALUES (1,1,12,10.5,'pera');
/*!40000 ALTER TABLE `testodgovori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testodgovori_odgovortest`
--

DROP TABLE IF EXISTS `testodgovori_odgovortest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `testodgovori_odgovortest` (
  `TestOdgovori_id` int(11) NOT NULL,
  `odgovori_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ypolyt4ewsx99xmi1gcnu4gj` (`odgovori_id`),
  KEY `FK_1mpnuu4m5r9rqljglwkoeea2m` (`TestOdgovori_id`),
  CONSTRAINT `FK_1mpnuu4m5r9rqljglwkoeea2m` FOREIGN KEY (`TestOdgovori_id`) REFERENCES `testodgovori` (`id`),
  CONSTRAINT `FK_ypolyt4ewsx99xmi1gcnu4gj` FOREIGN KEY (`odgovori_id`) REFERENCES `odgovortest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testodgovori_odgovortest`
--

LOCK TABLES `testodgovori_odgovortest` WRITE;
/*!40000 ALTER TABLE `testodgovori_odgovortest` DISABLE KEYS */;
INSERT INTO `testodgovori_odgovortest` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `testodgovori_odgovortest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zahtevi`
--

DROP TABLE IF EXISTS `zahtevi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zahtevi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jmbg` varchar(255) DEFAULT NULL,
  `datum_rodjenja` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `mesto_rodjenja` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `telefon` varchar(255) DEFAULT NULL,
  `tip` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zahtevi`
--

LOCK TABLES `zahtevi` WRITE;
/*!40000 ALTER TABLE `zahtevi` DISABLE KEYS */;
/*!40000 ALTER TABLE `zahtevi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-07 15:43:36
