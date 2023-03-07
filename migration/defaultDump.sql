-- MariaDB dump 10.19  Distrib 10.6.9-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: mentalizr
-- ------------------------------------------------------
-- Server version       10.6.9-MariaDB-1:10.6.9+maria~ubu2004use mentalizr;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient_program`
--

DROP TABLE IF EXISTS `patient_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_program` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `program_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `blocking` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`program_id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `fk_program_id` (`program_id`),
  CONSTRAINT `fk_program_id` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `role_patient` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_program`
--

LOCK TABLES `patient_program` WRITE;
/*!40000 ALTER TABLE `patient_program` DISABLE KEYS */;
INSERT INTO `patient_program` VALUES ('0a95e12f-2aa3-4bc4-887b-ae6e3901dd1b','netstep-depression-2020',0),('0ca4b844-c69e-4e1f-8779-a46793532c92','netstep-soziale-phobie',0),('143fd86b-ccfa-45ee-91e2-94d6b874f77d','netstep-depression-2021',0),('23beb987-e26c-4fed-ba0f-3d5715e4b270','gostress-fit-im-stress',0),('2e1f3d2d-0bca-4e11-8245-8beb81b459a7','netstep-angst',0),('3163d4d7-e50a-4bd5-bba8-1069a35f3fdf','netstep-depression-loesungen',0),('3b26511d-e29d-4b26-8fb1-71db53b16cc8','gostress-fit-im-stress',0),('450158a0-d6b3-48cc-b124-e0b764538526','gostress-fit-im-stress',0),('52d2b71e-7af3-44a1-85d6-a99783a8d291','gostress-fit-im-stress',0),('61b8a67a-757f-4f67-80ee-cc4d409c4317','gostress-alkohol',0),('671da106-611e-477a-9c2f-8a0cd2589436','netstep-depression',0),('69a23f3a-dde7-4e0f-bafb-508c9ffbdfbe','gostress-fit-im-stress',0),('76bd478d-4f20-4d00-96b8-6803c0f6c1d2','gostress-fit-im-stress',0),('7a212e91-4eda-4b56-a243-4e48c4894123','sport',0),('82908a05-a587-4070-abb0-75de4bc1b8a0','gostress-fit-im-stress',0),('9ed40f5e-7296-46b1-aabd-f782f66daef1','netstep-depression-gruebel',0),('affdf486-0c96-4af5-af0b-17801ffa472a','gostress-fit-im-stress',0),('b2de0870-7147-4021-9156-7eb643629ed3','gostress-stimmung',0),('cb91ad63-3cea-45a1-9a49-38e88f5afdf8','gostress-regeneration',0),('ce520335-fa6d-4574-9ec4-f096ec7b3052','netstep-depression-stress',0),('d9055de3-44c1-4ca3-8740-3a1376b7a9d8','supkiju',0),('db4912d9-5811-48e7-a9d4-2a30db3f199d','gostress-dankbarkeit',0),('eb8c05e3-e762-4b21-857a-0d66e1dbd005','gostress-fit-im-stress',0),('ef239453-2528-456e-a529-d34bf63e368d','gostress-fit-im-stress',0),('ef524e60-f790-4a5a-8f99-cca7402a5433','gostress-fit-im-stress',0),('f1250a2c-8863-4507-bc32-4498b7bf4959','netstep-depression-schlaf',0),('f8643370-8af0-450e-b127-411c860a754e','netstep-depression',1);
/*!40000 ALTER TABLE `patient_program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `program` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` VALUES ('demo'),('gostress-alkohol'),('gostress-dankbarkeit'),('gostress-fit-im-stress'),('gostress-regeneration'),('gostress-stimmung'),('netstep-angst'),('netstep-depression'),('netstep-depression-2020'),('netstep-depression-2021'),('netstep-depression-gruebel'),('netstep-depression-loesungen'),('netstep-depression-schlaf'),('netstep-depression-stress'),('netstep-soziale-phobie'),('sport'),('supkiju');
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_admin`
--

DROP TABLE IF EXISTS `role_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_admin` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_admin_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_admin`
--

LOCK TABLES `role_admin` WRITE;
/*!40000 ALTER TABLE `role_admin` DISABLE KEYS */;
INSERT INTO `role_admin` VALUES ('aaaaaaaa-bbbb-1234567890abcdefaaaaaa');
/*!40000 ALTER TABLE `role_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_patient`
--

DROP TABLE IF EXISTS `role_patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_patient` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `therapist_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_therapist_id` (`therapist_id`),
  CONSTRAINT `fk_patient_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_therapist_id` FOREIGN KEY (`therapist_id`) REFERENCES `role_therapist` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_patient`
--

LOCK TABLES `role_patient` WRITE;
/*!40000 ALTER TABLE `role_patient` DISABLE KEYS */;
INSERT INTO `role_patient` VALUES ('0a95e12f-2aa3-4bc4-887b-ae6e3901dd1b','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('0ca4b844-c69e-4e1f-8779-a46793532c92','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('143fd86b-ccfa-45ee-91e2-94d6b874f77d','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('23beb987-e26c-4fed-ba0f-3d5715e4b270','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('2e1f3d2d-0bca-4e11-8245-8beb81b459a7','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('3163d4d7-e50a-4bd5-bba8-1069a35f3fdf','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('3b26511d-e29d-4b26-8fb1-71db53b16cc8','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('450158a0-d6b3-48cc-b124-e0b764538526','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('52d2b71e-7af3-44a1-85d6-a99783a8d291','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('61b8a67a-757f-4f67-80ee-cc4d409c4317','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('671da106-611e-477a-9c2f-8a0cd2589436','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('69a23f3a-dde7-4e0f-bafb-508c9ffbdfbe','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('76bd478d-4f20-4d00-96b8-6803c0f6c1d2','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('7a212e91-4eda-4b56-a243-4e48c4894123','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('82908a05-a587-4070-abb0-75de4bc1b8a0','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('9ed40f5e-7296-46b1-aabd-f782f66daef1','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('affdf486-0c96-4af5-af0b-17801ffa472a','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('b2de0870-7147-4021-9156-7eb643629ed3','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('cb91ad63-3cea-45a1-9a49-38e88f5afdf8','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('ce520335-fa6d-4574-9ec4-f096ec7b3052','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('d9055de3-44c1-4ca3-8740-3a1376b7a9d8','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('db4912d9-5811-48e7-a9d4-2a30db3f199d','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('eb8c05e3-e762-4b21-857a-0d66e1dbd005','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('ef239453-2528-456e-a529-d34bf63e368d','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('ef524e60-f790-4a5a-8f99-cca7402a5433','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('f1250a2c-8863-4507-bc32-4498b7bf4959','08b6d439-1984-4aef-8b8d-e84a2cbc36e8'),('f8643370-8af0-450e-b127-411c860a754e','8347c63c-f5df-45e6-ba7d-e258e1505983');
/*!40000 ALTER TABLE `role_patient` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `role_therapist`
--

DROP TABLE IF EXISTS `role_therapist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_therapist` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_therapist_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_therapist`
--

LOCK TABLES `role_therapist` WRITE;
/*!40000 ALTER TABLE `role_therapist` DISABLE KEYS */;
INSERT INTO `role_therapist` VALUES ('08b6d439-1984-4aef-8b8d-e84a2cbc36e8',NULL),('8347c63c-f5df-45e6-ba7d-e258e1505983',NULL);
/*!40000 ALTER TABLE `role_therapist` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `firstActive` date DEFAULT NULL,
  `lastActive` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('08b6d439-1984-4aef-8b8d-e84a2cbc36e8',1,NULL,NULL),('0a95e12f-2aa3-4bc4-887b-ae6e3901dd1b',1,NULL,NULL),('0ca4b844-c69e-4e1f-8779-a46793532c92',1,NULL,NULL),('143fd86b-ccfa-45ee-91e2-94d6b874f77d',1,NULL,NULL),('23beb987-e26c-4fed-ba0f-3d5715e4b270',1,NULL,NULL),('2e1f3d2d-0bca-4e11-8245-8beb81b459a7',1,NULL,NULL),('3163d4d7-e50a-4bd5-bba8-1069a35f3fdf',1,NULL,NULL),('3b26511d-e29d-4b26-8fb1-71db53b16cc8',1,NULL,NULL),('450158a0-d6b3-48cc-b124-e0b764538526',1,NULL,NULL),('52d2b71e-7af3-44a1-85d6-a99783a8d291',1,NULL,NULL),('61b8a67a-757f-4f67-80ee-cc4d409c4317',1,NULL,NULL),('671da106-611e-477a-9c2f-8a0cd2589436',1,NULL,NULL),('69a23f3a-dde7-4e0f-bafb-508c9ffbdfbe',1,NULL,NULL),('76bd478d-4f20-4d00-96b8-6803c0f6c1d2',1,NULL,NULL),('7a212e91-4eda-4b56-a243-4e48c4894123',1,NULL,NULL),('82908a05-a587-4070-abb0-75de4bc1b8a0',1,NULL,NULL),('8347c63c-f5df-45e6-ba7d-e258e1505983',1,NULL,NULL),('9ed40f5e-7296-46b1-aabd-f782f66daef1',1,NULL,NULL),('aaaaaaaa-bbbb-1234567890abcdefaaaaaa',1,NULL,NULL),('affdf486-0c96-4af5-af0b-17801ffa472a',1,NULL,NULL),('b2de0870-7147-4021-9156-7eb643629ed3',1,NULL,NULL),('cb91ad63-3cea-45a1-9a49-38e88f5afdf8',1,NULL,NULL),('ce520335-fa6d-4574-9ec4-f096ec7b3052',1,NULL,NULL),('d9055de3-44c1-4ca3-8740-3a1376b7a9d8',1,NULL,NULL),('db4912d9-5811-48e7-a9d4-2a30db3f199d',1,NULL,NULL),('eb8c05e3-e762-4b21-857a-0d66e1dbd005',1,NULL,NULL),('ef239453-2528-456e-a529-d34bf63e368d',1,NULL,NULL),('ef524e60-f790-4a5a-8f99-cca7402a5433',1,NULL,NULL),('f1250a2c-8863-4507-bc32-4498b7bf4959',1,NULL,NULL),('f8643370-8af0-450e-b127-411c860a754e',1,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_access_key`
--

DROP TABLE IF EXISTS `user_access_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_access_key` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `accessKey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_accessKey` (`accessKey`),
  CONSTRAINT `fk_user_access_key_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_access_key`
--

LOCK TABLES `user_access_key` WRITE;
/*!40000 ALTER TABLE `user_access_key` DISABLE KEYS */;
INSERT INTO `user_access_key` VALUES ('52d2b71e-7af3-44a1-85d6-a99783a8d291','X2MPDP4ZUCYP'),('450158a0-d6b3-48cc-b124-e0b764538526','X5AAHG7M6QGR'),('3b26511d-e29d-4b26-8fb1-71db53b16cc8','X9TUBP7VT5SF'),('ef239453-2528-456e-a529-d34bf63e368d','XC7YGXSUHBEU'),('ef524e60-f790-4a5a-8f99-cca7402a5433','XHYE8NL6XHYU'),('23beb987-e26c-4fed-ba0f-3d5715e4b270','XJVY3PJDTVMA'),('69a23f3a-dde7-4e0f-bafb-508c9ffbdfbe','XLNT6UHJFQ5Z'),('76bd478d-4f20-4d00-96b8-6803c0f6c1d2','XMDVBCTPFMUX'),('82908a05-a587-4070-abb0-75de4bc1b8a0','XPNMJEYUH557'),('affdf486-0c96-4af5-af0b-17801ffa472a','XYNQG65N7V6W');
/*!40000 ALTER TABLE `user_access_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login`
--

DROP TABLE IF EXISTS `user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_login` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_username` (`username`),
  CONSTRAINT `fk_user_login_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login`
--

LOCK TABLES `user_login` WRITE;
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
INSERT INTO `user_login` VALUES ('08b6d439-1984-4aef-8b8d-e84a2cbc36e8','therapeut1','$argon2i$v=19$m=65536,t=20,p=1$0m6s0RB7G2ol+uyXyLofeA$8MnPNzYxegEjR4RZJuJMWie5LaTqERE8j0MuMKqvzLc','therapeut1@example.org','Psycho','Therapeutin',0),('0a95e12f-2aa3-4bc4-887b-ae6e3901dd1b','d20','$argon2i$v=19$m=65536,t=20,p=1$1NBEf7GU4Tgp2Vv+WwYXFw$nlR3udV855Owanf/+gOw3Ifq0xK53WSktD/wRFLgYJs','mm@example.org','Max','Mustermann',1),('0ca4b844-c69e-4e1f-8779-a46793532c92','s','$argon2i$v=19$m=65536,t=20,p=1$VeotN+1I1KACr+2t3sGVDQ$8Xj2n/eSi2B7XVWbnoqxq3wUwtKFx3KGp0iID354gn8','mm@example.org','Max','Mustermann',0),('143fd86b-ccfa-45ee-91e2-94d6b874f77d','d21','$argon2i$v=19$m=65536,t=20,p=1$KGVO2kTpxI8vrF/t8xoHkw$q0Efbviro6lb1HxsoEN+xdry58IYOIt0X/w84orK20U','max.mustermann@example.org','Max','Mustermann',1),('2e1f3d2d-0bca-4e11-8245-8beb81b459a7','a','$argon2i$v=19$m=65536,t=20,p=1$mtRpSqpMFSPGyh4EeScP5g$qYt5zsMuFz8mDPz1i94W4b8ApviogwhgCWwuRxiM6Qo','mm@example.org','Max','Mustermann',1),('3163d4d7-e50a-4bd5-bba8-1069a35f3fdf','loesungen','$argon2i$v=19$m=65536,t=20,p=1$uwRFjrbV8GNpP3dGlPKvGg$4wdXroWxQGhAK58KrnEc702RtjSdKMnr+YxmFfO83uM','max.mustermann@example.org','Max','Mustermann',1),('61b8a67a-757f-4f67-80ee-cc4d409c4317','alk','$argon2i$v=19$m=65536,t=20,p=1$cPF+9PdXa/9Nr1Ey6o+w/g$Fn8CC20byANXe4aN/ybzrhnb/NLobNaH3ErqJD3WoK0','mm@example.org','Max','Mustermann',1),('671da106-611e-477a-9c2f-8a0cd2589436','d','$argon2i$v=19$m=65536,t=20,p=1$NXacQSM+s6fLhlK6FO6V/Q$6Iws+GoBvjHFG3ii5vgkKBy2cLLBB8Ei8RVOFFMK3wM','mm@example.org','Max','Mustermann',1),('7a212e91-4eda-4b56-a243-4e48c4894123','sport','$argon2i$v=19$m=65536,t=20,p=1$ql+9/5CFl1G6gRtzFBgrWQ$h8j9KRZwFobOcXzvzVgl7Zy/j3mHQNXWEiGPnF76sRo','max.mustermann@example.org','Max','Mustermann',1),('8347c63c-f5df-45e6-ba7d-e258e1505983','t','$argon2i$v=19$m=65536,t=20,p=1$xrO3YIBiXseLxWomvpqo1Q$69j5omfb/VhBerFzS84rY43VUjgK6wFCY8cdjAjkHYo','max.mustermann@example.org','Max','Mustermann',1),('9ed40f5e-7296-46b1-aabd-f782f66daef1','gruebel','$argon2i$v=19$m=65536,t=20,p=1$Vv3jXGB51KT4txBhAZ24cA$Wc9Kq+NZaYBfAWSIPSrRVC6EkBGTn6X6nNjEJmIVXMI','max.mustermann@example.org','Max','Mustermann',1),('aaaaaaaa-bbbb-1234567890abcdefaaaaaa','mentalizr','$argon2i$v=19$m=65536,t=20,p=1$GOoxJ4PJo60LGTw3I4FMKA$hHd85moaSVAdlhgJY5Fq7ikDxqAkzCCU4KRU8BA1GHc','mentalizr@example.org','admin','admin',1),('b2de0870-7147-4021-9156-7eb643629ed3','sti','$argon2i$v=19$m=65536,t=20,p=1$605F8lrW5LRxWS0glis1+A$EA0QTMX4unEDxXuMU/7mR1WL05GNK7HX1fW0FcZsL8Q','mm@example.org','Max','Mustermann',1),('cb91ad63-3cea-45a1-9a49-38e88f5afdf8','reg','$argon2i$v=19$m=65536,t=20,p=1$ixvR1oiJqNz95/62uANoYg$S0oQ0U36/iylm/Xd//4x6IFQVl1sODntsx8fluUoIA0','mm@example.org','Max','Mustermann',1),('ce520335-fa6d-4574-9ec4-f096ec7b3052','stress','$argon2i$v=19$m=65536,t=20,p=1$l1zTgWmSr4F9jAdOwDPM0g$OzFmQXarM8GGa+1eFLc15IK5wOfmRgvYRu52nqFqJaU','max.mustermann@example.org','Max','Mustermann',1),('d9055de3-44c1-4ca3-8740-3a1376b7a9d8','kj','$argon2i$v=19$m=65536,t=20,p=1$RuN1bmCB6+0MMYwBk7iV6Q$g0n95P8JB1cnkFBH0PPABrQ/zmyhpOti2ldf+GtEqHw','mm@example.org','Max','Mustermann',0),('db4912d9-5811-48e7-a9d4-2a30db3f199d','dank','$argon2i$v=19$m=65536,t=20,p=1$+Z8l+q6I2yrzf6hZAwWBfg$Rzi6H/AjOKXE+55oVcBlXPS8oaqnaDQKiYoJMNBQRe8','mm@example.org','Max','Mustermann',1),('eb8c05e3-e762-4b21-857a-0d66e1dbd005','fit','$argon2i$v=19$m=65536,t=20,p=1$uDgScX4UC7vd+xs7YeDJuQ$JzrOnMRiGWqOTSodDvTv889t2QpmIJdpmBIDWtY/juA','mm@example.org','Max','Mustermann',1),('f1250a2c-8863-4507-bc32-4498b7bf4959','schlaf','$argon2i$v=19$m=65536,t=20,p=1$zqpae+NCL2QvKin+zjLEHw$MnllQGOIyA6NB6R4SNe+mn4Sfedc/MVKLhiP4ndTny0','max.mustermann@example.org','Max','Mustermann',1),('f8643370-8af0-450e-b127-411c860a754e','db','$argon2i$v=19$m=65536,t=20,p=1$rE1n2o5nRL3C+SNARJbkRQ$uCZb8SdMJSY4EZwLfLj0UJj3scfmok+Mci+6pbnZszk','blocking.depression@example.org','Blocking','Depression',1);
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
