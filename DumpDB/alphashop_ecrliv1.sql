-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: alphashop
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ecrliv1`
--

DROP TABLE IF EXISTS `ecrliv1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ecrliv1` (
  `Id` char(2) NOT NULL,
  `Descrizione` varchar(30) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ecrliv1`
--

LOCK TABLES `ecrliv1` WRITE;
/*!40000 ALTER TABLE `ecrliv1` DISABLE KEYS */;
INSERT INTO `ecrliv1` VALUES ('01','DROGHERIA ALIMENTARE          '),('02','BEVANDE                       '),('03','FRESCHI                       '),('04','SURGELATI                     '),('05','CURA CASA                     '),('06','CURA PERSONA                  '),('07','PET CARE                      '),('08','ORTOFRUTTA                    '),('09','CARNI                         '),('10','ORTOFRUTTA LOYAPP             '),('50','ELETTRONICA ELETTRODOMESTICI  '),('51','TESSILE ABB CALZATURE         '),('52','BRICOLAGE                     '),('53','ARREDAMENTO                   '),('54','CASALINGHI                    '),('55','CARTOLERIA LIBRI GIORNALI     '),('56','GIOCHI PUERICULTURA           '),('57','ARREDI ESTERNI CAMPEGGIO      '),('58','SERVIZI                       ');
/*!40000 ALTER TABLE `ecrliv1` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-20 15:18:49
