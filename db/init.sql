CREATE DATABASE  IF NOT EXISTS `testschema`;
USE `testschema`;


DROP TABLE IF EXISTS `workers`;
CREATE TABLE `workers` (
  `idWorker` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `contactno` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idWorker`),
  UNIQUE (`name`)
);


INSERT INTO `workers` VALUES 
(1,'sampleworker','sampleworker','worker@workersite.com','091912928'),
(2,'sampleworker2','sampleworker',NULL,NULL),
(3,'sampleworker3','sampleworker3',NULL,NULL),
(4,'sampleworker4','sampleworker4',NULL,NULL),
(5,'Noel Saludo','noel1234','noelsaludo@gmail.com','829372132'),
(6,'brian perez','brianperez1234','briankristoferperez@gmail.com','19132654'),
(7,'raim gonda','gonda12345678',NULL,NULL),
(8,'erix crisostomo','erizz12345',NULL,NULL),
(9,'kalel sta teresa','1234kalel','staTeresa@g.batstate-u.edu.ph','761873572'),
(10,'chester calog','chester45678',NULL,NULL);


DROP TABLE IF EXISTS `employers`;
CREATE TABLE `employers` (
  `idEmployer` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `contactno` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idEmployer`),
  UNIQUE (`name`)
);


INSERT INTO `employers` VALUES 
(1,'SampleEmployer','SampleEmployer','SampleEmployer@outlook.com','0919321456'),
(2,'Joel chavez','123456','JoelChavez@batangascity.com','192-123-45681');


DROP TABLE IF EXISTS `resumes`;
CREATE TABLE `resumes` (
  `idResume` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `Summary` varchar(600) DEFAULT NULL,
  PRIMARY KEY (`idResume`),
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `resumes` VALUES 
(1,1,'Sample SWE'),
(2,2,NULL),
(3,3,NULL),
(4,4,NULL),
(5,5,'Junior software engineer'),
(6,6,'Junior software engineer, with minor proficiency in java and python'),
(7,7,NULL),
(8,8,NULL),
(9,9,'Junior Med Tech'),
(10,10,NULL);


DROP TABLE IF EXISTS `jobs`;
CREATE TABLE `jobs` (
  `idJob` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `salary` int NOT NULL,
  `idEmployer` int NOT NULL,
  PRIMARY KEY (`idJob`),
  FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `jobs` VALUES 
(1,'Full time security guard','Day shift, will be guarding the front door',20000,1),
(2,'CAD modeler','Will be designing or formulating CAD designs for civilian infrastructure',150000,2);



DROP TABLE IF EXISTS `engineeringjobs`;
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
  FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `engineeringjobs` VALUES 
(1,'Enterprise Systems Software','Remote','Occasional','Permanent','Junior Software Engineer','Develops systems and software, must be proficient in Java',20000,1),
(2,'Database systems engineering','Remote','Never','Permanent','Senior software engineer','Must be proficient in git, SQL, and modern frameworks',100000,2);


DROP TABLE IF EXISTS `medicaljobs`;
CREATE TABLE `medicaljobs` (
  `idMedicalJob` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `salary` int NOT NULL,
  `idEmployer` int NOT NULL,
  `department` varchar(45) NOT NULL,
  `shift` varchar(45) NOT NULL,
  PRIMARY KEY (`idMedicalJob`),
  FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `medicaljobs` VALUES 
(1,'Assistant Doctor','Will assist the Lead Doctor for day to day operations',35000,1,'Cardiology','Day'),
(2,'On field surgeon','Will be administering first aid to immediate workers',150000,2,'First aid','Day');


DROP TABLE IF EXISTS `managementjobs`;
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
  FOREIGN KEY (`idEmployer`) REFERENCES `employers` (`idEmployer`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `managementjobs` VALUES 
(1,'Manager 2','Manages finance operations',60000,1,6,'Team Leader','Finance Operations'),
(2,'Project manager','Will be overseeing modern construction',120000,2,15,'Team leader','Construction');


DROP TABLE IF EXISTS `applications`;
CREATE TABLE `applications` (
  `idApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idJob` int NOT NULL,
  PRIMARY KEY (`idApplication`),
  FOREIGN KEY (`idJob`) REFERENCES `jobs` (`idJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `applications` VALUES (1,9,2),(2,10,2),(3,7,2);

DROP TABLE IF EXISTS `benefits`;
CREATE TABLE `benefits` (
  `idbenefit` int NOT NULL AUTO_INCREMENT,
  `benefit` varchar(45) NOT NULL,
  `idJob` int NOT NULL,
  PRIMARY KEY (`idbenefit`),
  FOREIGN KEY (`idJob`) REFERENCES `jobs` (`idJob`) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `benefits` VALUES
(1,'14 days vacation leave annually',2),
(2,'Company laptop provided',2),
(3,'CAD software license provided',2);


DROP TABLE IF EXISTS `certifications`;
CREATE TABLE `certifications` (
  `idCertifications` int NOT NULL AUTO_INCREMENT,
  `idResume` int NOT NULL,
  `certificationName` varchar(45) NOT NULL,
  PRIMARY KEY (`idCertifications`),
  FOREIGN KEY (`idResume`) REFERENCES `resumes` (`idResume`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `certifications` VALUES 
(1,5,'BS Computer science'),
(2,5,'Microsoft Certification w/ Azure DevOps'),
(3,6,'BS Computer Science'),
(4,6,'MS Computer Science'),
(5,9,'Passed doctor licensure examination'),
(6,9,'Passed civil service examination'),
(7,9,'DOST Merit Scholar'),
(8,9,'BS Pharmaceuticals');


DROP TABLE IF EXISTS `engineeringapplications`;
CREATE TABLE `engineeringapplications` (
  `idEngineeringApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idEngineeringJob` int NOT NULL,
  PRIMARY KEY (`idEngineeringApplication`),
  FOREIGN KEY (`idEngineeringJob`) REFERENCES `engineeringjobs` (`idEngineeringJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `engineeringapplications` VALUES (1,5,2),(2,6,2);


DROP TABLE IF EXISTS `engineeringbenefits`;
CREATE TABLE `engineeringbenefits` (
  `idEngineeringBenefit` int NOT NULL AUTO_INCREMENT,
  `idEngineeringJob` int NOT NULL,
  `benefit` varchar(45) NOT NULL,
  PRIMARY KEY (`idEngineeringBenefit`),
  FOREIGN KEY (`idEngineeringJob`) REFERENCES `engineeringjobs` (`idEngineeringJob`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `engineeringbenefits` VALUES 
(1,1,'Free company laptop'),
(2,1,'Free home office set'),
(3,1,'Weekly meetups'),
(4,2,'Health insurance'),
(5,2,'company laptop'),
(6,2,'free lunch');


DROP TABLE IF EXISTS `engineeringoccupations`;
CREATE TABLE `engineeringoccupations` (
  `idEngineeringOccupation` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idEngineeringJob` int NOT NULL,
  PRIMARY KEY (`idEngineeringOccupation`),
  FOREIGN KEY (`idEngineeringJob`) REFERENCES `engineeringjobs` (`idEngineeringJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `engineeringoccupations` VALUES (1,1,1);


DROP TABLE IF EXISTS `managementapplications`;
CREATE TABLE `managementapplications` (
  `idManagementApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idManagementJob` int NOT NULL,
  PRIMARY KEY (`idManagementApplication`),
  FOREIGN KEY (`idManagementJob`) REFERENCES `managementjobs` (`idManagementJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `managementapplications` VALUES (1,7,2),(2,8,2);


DROP TABLE IF EXISTS `managementbenefits`;
CREATE TABLE `managementbenefits` (
  `idManagementBenefit` int NOT NULL AUTO_INCREMENT,
  `idManagementJob` int NOT NULL,
  `benefit` varchar(45) NOT NULL,
  PRIMARY KEY (`idManagementBenefit`),
  FOREIGN KEY (`idManagementJob`) REFERENCES `managementjobs` (`idManagementJob`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `managementbenefits` VALUES 
(1,1,'Monthly Bonuses'),
(2,1,'Laptop provided'),
(3,1,'14 days of Annual vacation leave'),
(4,2,'Company car'),
(5,2,'Health insurance'),
(6,2,'free travel');


DROP TABLE IF EXISTS `managementoccupations`;
CREATE TABLE `managementoccupations` (
  `idManagemenntOccupations` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idManagementJob` int NOT NULL,
  PRIMARY KEY (`idManagemenntOccupations`),
  FOREIGN KEY (`idManagementJob`) REFERENCES `managementjobs` (`idManagementJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `managementoccupations` VALUES (1,3,1);


DROP TABLE IF EXISTS `medicalapplications`;
CREATE TABLE `medicalapplications` (
  `idMedicalApplication` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idMedicalJob` int NOT NULL,
  PRIMARY KEY (`idMedicalApplication`),
  FOREIGN KEY (`idMedicalJob`) REFERENCES `medicaljobs` (`idMedicalJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `medicalapplications` VALUES (1,9,2);


DROP TABLE IF EXISTS `medicalbenefits`;
CREATE TABLE `medicalbenefits` (
  `idMedicalBenefits` int NOT NULL AUTO_INCREMENT,
  `idMedicalJob` int NOT NULL,
  `benefit` varchar(45) NOT NULL,
  PRIMARY KEY (`idMedicalBenefits`),
  FOREIGN KEY (`idMedicalJob`) REFERENCES `medicaljobs` (`idMedicalJob`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `medicalbenefits` VALUES 
(1,1,'Competitive salary'),
(2,1,'14 days leave annually'),
(3,2,'annual bonuses'),
(4,2,'free lunch');


DROP TABLE IF EXISTS `medicaloccupations`;
CREATE TABLE `medicaloccupations` (
  `idMedicalOccupation` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idMedicalJob` int NOT NULL,
  PRIMARY KEY (`idMedicalOccupation`),
  FOREIGN KEY (`idMedicalJob`) REFERENCES `medicaljobs` (`idMedicalJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `medicaloccupations` VALUES (1,2,1);


DROP TABLE IF EXISTS `occupations`;
CREATE TABLE `occupations` (
  `idOccupation` int NOT NULL AUTO_INCREMENT,
  `idWorker` int NOT NULL,
  `idJob` int NOT NULL,
  PRIMARY KEY (`idOccupation`),
  FOREIGN KEY (`idJob`) REFERENCES `jobs` (`idJob`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idWorker`) REFERENCES `workers` (`idWorker`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `occupations` VALUES (1,4,1);


DROP TABLE IF EXISTS `workexperience`;
CREATE TABLE `workexperience` (
  `idWorkExperience` int NOT NULL AUTO_INCREMENT,
  `idResume` int NOT NULL,
  `experience` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`idWorkExperience`),
  FOREIGN KEY (`idResume`) REFERENCES `resumes` (`idResume`) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO `workexperience` VALUES 
(1,1,'Small java projects'),
(2,1,'Small python projects'),
(3,5,'Sample java project 1'),
(4,5,'Sample java project 2'),
(5,6,'PowerBI development portfolio'),
(6,6,'Worked on a thesis regarding data analysis'),
(7,6,'Implemented java project'),
(8,6,'sample experience here'),
(9,9,'5 years experience in [sample] hospital');
