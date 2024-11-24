-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: testschema
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS `applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications` (
  `idApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idJob` int NOT NULL,
  PRIMARY KEY (`idApplication`),
  UNIQUE KEY `idApplication_UNIQUE` (`idApplication`),
  KEY `idWorker_idx` (`idWorker`),
  KEY `idJobkey_idx` (`idJob`),
  CONSTRAINT `idJobkey` FOREIGN KEY (`idJob`) REFERENCES `jobs` (`idJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorkerkey` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `benefits`
--

DROP TABLE IF EXISTS `benefits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benefits` (
  `idbenefit` int NOT NULL AUTO_INCREMENT,
  `benefit` varchar(45) NOT NULL,
  `idJob` int NOT NULL,
  PRIMARY KEY (`idbenefit`),
  KEY `idJob_idx` (`idJob`),
  CONSTRAINT `idJob` FOREIGN KEY (`idJob`) REFERENCES `jobs` (`idJob`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `certifications`
--

DROP TABLE IF EXISTS `certifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certifications` (
  `idCertifications` int NOT NULL AUTO_INCREMENT,
  `idResume` int NOT NULL,
  `certificationName` varchar(45) NOT NULL,
  PRIMARY KEY (`idCertifications`),
  KEY `idResume_idx` (`idResume`),
  CONSTRAINT `idResume` FOREIGN KEY (`idResume`) REFERENCES `resumes` (`idResume`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employers`
--

DROP TABLE IF EXISTS `employers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employers` (
  `idEmployer` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `contactno` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idEmployer`),
  UNIQUE KEY `idEmployer_UNIQUE` (`idEmployer`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `engineeringapplications`
--

DROP TABLE IF EXISTS `engineeringapplications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `engineeringapplications` (
  `idEngineeringApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idEngineeringJob` int NOT NULL,
  PRIMARY KEY (`idEngineeringApplication`),
  KEY `idEngineeringJob_idx` (`idEngineeringJob`),
  KEY `idWorker_idx` (`idWorker`),
  CONSTRAINT `idEngineeringJobs` FOREIGN KEY (`idEngineeringJob`) REFERENCES `engineeringjobs` (`idEngineeringJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorkers` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `engineeringbenefits`
--

DROP TABLE IF EXISTS `engineeringbenefits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `engineeringbenefits` (
  `idEngineeringBenefit` int NOT NULL AUTO_INCREMENT,
  `idEngineeringJob` int NOT NULL,
  `benefit` varchar(45) NOT NULL,
  PRIMARY KEY (`idEngineeringBenefit`),
  KEY `idEngineeringJob_idx` (`idEngineeringJob`),
  CONSTRAINT `idEngineeringJob` FOREIGN KEY (`idEngineeringJob`) REFERENCES `engineeringjobs` (`idEngineeringJob`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `engineeringjobs`
--

DROP TABLE IF EXISTS `engineeringjobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `engineeringjobs` (
  `idEngineeringJob` int NOT NULL AUTO_INCREMENT,
  `projectType` varchar(45) NOT NULL,
  `locationType` varchar(45) NOT NULL,
  `travelRequirements` varchar(45) NOT NULL,
  `contractType` varchar(45) NOT NULL,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `salary` int NOT NULL,
  `idEmployer` int NOT NULL,
  PRIMARY KEY (`idEngineeringJob`),
  KEY `idEmployer_idx` (`idEmployer`),
  CONSTRAINT `idEmployerkey` FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `engineeringoccupations`
--

DROP TABLE IF EXISTS `engineeringoccupations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `engineeringoccupations` (
  `idEngineeringOccupation` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idEngineeringJob` int NOT NULL,
  PRIMARY KEY (`idEngineeringOccupation`),
  KEY `idWorker_idx` (`idWorker`),
  KEY `idEngineeringJob_idx` (`idEngineeringJob`),
  CONSTRAINT `idEngineeringJob_foreign` FOREIGN KEY (`idEngineeringJob`) REFERENCES `engineeringjobs` (`idEngineeringJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorker_foreign` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobs` (
  `idJob` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `salary` int NOT NULL,
  `idEmployer` int NOT NULL,
  PRIMARY KEY (`idJob`),
  KEY `idEmployer_idx` (`idEmployer`),
  CONSTRAINT `idEmployer` FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `managementapplications`
--

DROP TABLE IF EXISTS `managementapplications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `managementapplications` (
  `idManagementApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idManagementJob` int NOT NULL,
  PRIMARY KEY (`idManagementApplication`),
  KEY `idManagementJob_managementapplications_idx` (`idManagementJob`),
  KEY `idWorker_managementapplications_idx` (`idWorker`),
  CONSTRAINT `idManagementJob_managementapplications` FOREIGN KEY (`idManagementJob`) REFERENCES `managementjobs` (`idManagementJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorker_managementapplications` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `managementbenefits`
--

DROP TABLE IF EXISTS `managementbenefits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `managementbenefits` (
  `idManagementBenefit` int NOT NULL AUTO_INCREMENT,
  `idManagementJob` int NOT NULL,
  `benefit` varchar(45) NOT NULL,
  PRIMARY KEY (`idManagementBenefit`),
  KEY `idManagementJob_idx` (`idManagementJob`),
  CONSTRAINT `idManagementJob` FOREIGN KEY (`idManagementJob`) REFERENCES `managementjobs` (`idManagementJob`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `managementjobs`
--

DROP TABLE IF EXISTS `managementjobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `managementjobs` (
  `idManagementJob` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `salary` int NOT NULL,
  `idEmployer` int NOT NULL,
  `teamSize` int NOT NULL,
  `leadershipLevel` varchar(45) NOT NULL,
  `department` varchar(45) NOT NULL,
  PRIMARY KEY (`idManagementJob`),
  KEY `idEmployer_idx` (`idEmployer`),
  CONSTRAINT `idEmployers` FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `managementoccupations`
--

DROP TABLE IF EXISTS `managementoccupations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `managementoccupations` (
  `idManagemenntOccupations` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idManagementJob` int NOT NULL,
  PRIMARY KEY (`idManagemenntOccupations`),
  KEY `idWorker_managementoccupations_idx` (`idWorker`),
  KEY `idManagementJob_managementoccupations_idx` (`idManagementJob`),
  CONSTRAINT `idManagementJob_managementoccupations` FOREIGN KEY (`idManagementJob`) REFERENCES `managementjobs` (`idManagementJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorker_managementoccupations` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicalapplications`
--

DROP TABLE IF EXISTS `medicalapplications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicalapplications` (
  `idMedicalApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idMedicalJob` int NOT NULL,
  PRIMARY KEY (`idMedicalApplication`),
  KEY `idWorker_medicalApp_idx` (`idWorker`),
  KEY `idMedicalJob_medicalApp_idx` (`idMedicalJob`),
  CONSTRAINT `idMedicalJob_medicalApp` FOREIGN KEY (`idMedicalJob`) REFERENCES `medicaljobs` (`idMedicalJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorker_medicalApp` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicalbenefits`
--

DROP TABLE IF EXISTS `medicalbenefits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicalbenefits` (
  `idMedicalBenefits` int NOT NULL AUTO_INCREMENT,
  `idMedicalJob` int NOT NULL,
  `benefit` varchar(45) NOT NULL,
  PRIMARY KEY (`idMedicalBenefits`),
  KEY `idMedicalJob_idx` (`idMedicalJob`),
  CONSTRAINT `idMedicalJob` FOREIGN KEY (`idMedicalJob`) REFERENCES `medicaljobs` (`idMedicalJob`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicaljobs`
--

DROP TABLE IF EXISTS `medicaljobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicaljobs` (
  `idMedicalJob` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `salary` int NOT NULL,
  `idEmployer` int NOT NULL,
  `department` varchar(45) NOT NULL,
  `shift` varchar(45) NOT NULL,
  PRIMARY KEY (`idMedicalJob`),
  KEY `idEmployerkey_idx` (`idEmployer`),
  CONSTRAINT `idEmployerkeys` FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicaloccupations`
--

DROP TABLE IF EXISTS `medicaloccupations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicaloccupations` (
  `idMedicalOccupation` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idMedicalJob` int NOT NULL,
  PRIMARY KEY (`idMedicalOccupation`),
  KEY `idMedicalJob_medicaloccupations_idx` (`idMedicalJob`),
  KEY `idWorker_medicaloccupations_idx` (`idWorker`),
  CONSTRAINT `idMedicalJob_medicaloccupations` FOREIGN KEY (`idMedicalJob`) REFERENCES `medicaljobs` (`idMedicalJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorker_medicaloccupations` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `occupations`
--

DROP TABLE IF EXISTS `occupations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `occupations` (
  `idOccupation` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idJob` int NOT NULL,
  PRIMARY KEY (`idOccupation`),
  KEY `idWorker_idx` (`idWorker`),
  KEY `idJob_idx` (`idJob`),
  CONSTRAINT `idJobkeys` FOREIGN KEY (`idJob`) REFERENCES `jobs` (`idJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idWorkerkeys` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resumes`
--

DROP TABLE IF EXISTS `resumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resumes` (
  `idResume` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `Summary` varchar(600) DEFAULT NULL,
  PRIMARY KEY (`idResume`),
  KEY `idWorker_idx` (`idWorker`),
  CONSTRAINT `idWorker` FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workers`
--

DROP TABLE IF EXISTS `workers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workers` (
  `idWorker` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `contactno` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idWorker`),
  UNIQUE KEY `idUsers_UNIQUE` (`idWorker`),
  UNIQUE KEY `email_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workexperience`
--

DROP TABLE IF EXISTS `workexperience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workexperience` (
  `idWorkExperience` int NOT NULL AUTO_INCREMENT,
  `idResume` int NOT NULL,
  `experience` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`idWorkExperience`),
  KEY `idResume_idx` (`idResume`),
  CONSTRAINT `idResumekey` FOREIGN KEY (`idResume`) REFERENCES `resumes` (`idResume`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-24 21:29:10
