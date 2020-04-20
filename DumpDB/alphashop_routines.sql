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
-- Dumping events for database 'alphashop'
--

--
-- Dumping routines for database 'alphashop'
--
/*!50003 DROP FUNCTION IF EXISTS `IsNumeric` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `IsNumeric`(
	Input varchar(50)
) RETURNS smallint
    DETERMINISTIC
BEGIN

DECLARE TEST INT;
DECLARE retVal SMALLINT;

SELECT 
    COUNT(*)
INTO TEST FROM
    iva
WHERE
    Input REGEXP '^[0-9]+$';

IF TEST > 0 THEN
SET retVal = 1;
ELSE
SET retVal = 0;
END IF;

RETURN (retVal); 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_DelArt` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Sp_DelArt`(
	Codice varchar(20)
)
BEGIN

DECLARE test smallint;

SELECT COUNT(*) INTO test FROM articoli WHERE CODART = Codice;

IF (test > 0) THEN
DELETE FROM articoli WHERE CODART = Codice;
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_InsAdminUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_InsAdminUser`(
	v_Password varchar(100)
)
BEGIN

DECLARE test smallint;
SELECT COUNT(*) INTO test FROM utenti WHERE USERID = 'ADMIN';

IF (test = 0) THEN
	INSERT INTO utenti
	VALUES('-1','ADMIN',v_Password,'Si');
ELSE
	UPDATE utenti
	SET PASSWORD = v_Password
	WHERE USERID = 'ADMIN';
END IF;

DELETE FROM profili WHERE CODFIDELITY = -1;

INSERT INTO profili (CODFIDELITY,TIPO)
VALUES('-1','USER');

INSERT INTO profili (CODFIDELITY,TIPO)
VALUES('-1','ADMIN');


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_InsArt` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Sp_InsArt`(
	Codice varchar(20),
	Descrizione varchar(60),
	Um char(4),
	CodStat varchar(20),
	PzCart smallint,
	PesoNetto float,
	IdIva int,
	IdStatoArt char(2),
	IdFamAss int
)
BEGIN

DECLARE test smallint;

SELECT COUNT(*) INTO test FROM articoli WHERE CODART = Codice;

IF (test > 0) THEN
UPDATE articoli
	SET 
	DESCRIZIONE = Descrizione,
	UM = Um,
	CODSTAT = CodStat,
	PZCART = PzCart,
	PESONETTO = PesoNetto,
	IDIVA = IdIva,
	IDSTATOART = IdStatoArt,
	IDFAMASS = IdFamAss
	WHERE CODART = Codice;
ELSE
	INSERT INTO articoli
	VALUES(Codice,Descrizione,Um,CodStat,PzCart,PesoNetto,IdIva,IdStatoArt,CURDATE(),IdFamAss);
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_InsDatiTrasm` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_InsDatiTrasm`(
	v_Data Date,
	v_IdTerminale nchar(10),
	v_Barcode varchar(13),
	v_Qta int
)
BEGIN

INSERT INTO trasmissioni(IdTerminale,Data,Barcode,Qta)
VALUES(v_IdTerminale,v_Data,v_Barcode,v_Qta);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_InsIngredienti` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_InsIngredienti`(
	v_CodArt varchar(20),
	v_Info varchar(300)
)
BEGIN

IF (SELECT COUNT(*) FROM ingredienti WHERE CODART = v_CodArt > 0 AND length(v_CodArt)) THEN
	BEGIN
		UPDATE ingredienti
		SET INFO = v_Info
		WHERE CODART = v_CodArt; 
	END;
ELSE
	BEGIN
		INSERT INTO ingredienti
		VALUES(v_CodArt,v_Info); 
    END;
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_SelActivePromoFid` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_SelActivePromoFid`(
	v_CodFid varchar(20),
	v_CodArt varchar(20)
)
BEGIN

SELECT * FROM dettpromo
WHERE CODFID = v_CodFid
AND CODART = v_CodArt
AND CAST(CURDATE() AS DATE) BETWEEN INIZIO AND FINE
ORDER BY OGGETTO ASC
LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_SelArt` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_SelArt`(
	 v_filtro varchar(50),
     v_listino smallint 
     
)
BEGIN

SET @row_number = 0;


IF (SELECT COUNT(*) FROM articoli WHERE CODART = v_filtro > 0 AND length(v_filtro)) THEN
	SELECT 
	(@row_number:=@row_number + 1) as RIGA,
	A.CODART,
	A.DESCRIZIONE,
	IFNULL(AA.PREZZO, 0) AS PREZZO,
	CASE WHEN IFNULL(A.PESONETTO,0) > 0 THEN ROUND(IFNULL(AA.PREZZO, 0) / IFNULL(A.PESONETTO,0),2) ELSE 0 END AS PRZKG,
	A.UM,
	IFNULL(A.CODSTAT,'') AS CODSTAT,
	IFNULL(A.PZCART,0) AS PZCART,
	ROUND(IFNULL(A.PESONETTO,0),2) AS PESONETTO,
	IFNULL(BB.Magazzino,0) AS QTAMAG,
	A.IDIVA,
	IFNULL(A.IDSTATOART,'3') AS IDSTATOART,
	IFNULL(A.IDFAMASS,-1) AS IDFAMASS,
	IFNULL(B.FAMASS,'NON DISPONIBILE') AS FAMASS,
	A.DATACREAZIONE
	FROM articoli A 
	LEFT JOIN (SELECT ID, DESCRIZIONE AS FAMASS FROM famassort) B ON A.IDFAMASS = B.ID 
	LEFT JOIN (SELECT CODART AS CODICE, PREZZO FROM dettlistini WHERE IdList = v_listino) AA ON A.CODART = AA.CODICE 
	LEFT JOIN (SELECT CODART AS CODICE, (ACQUISTATO - RESO - VENDUTO - USCITE - SCADUTI) AS Magazzino FROM movimenti) BB ON A.CODART = BB.CODICE  
    WHERE A.CODART = v_filtro
    ORDER BY CODART; 
ELSEIF (IsNumeric(v_filtro) = 0 AND length(v_filtro)) THEN
	SELECT 
	(@row_number:=@row_number + 1) as RIGA,
	A.CODART,
	A.DESCRIZIONE,
	IFNULL(AA.PREZZO, 0) AS PREZZO,
	CASE WHEN IFNULL(A.PESONETTO,0) > 0 THEN ROUND(IFNULL(AA.PREZZO, 0) / IFNULL(A.PESONETTO,0),2) ELSE 0 END AS PRZKG,
	A.UM,
	IFNULL(A.CODSTAT,'') AS CODSTAT,
	IFNULL(A.PZCART,0) AS PZCART,
	ROUND(IFNULL(A.PESONETTO,0),2) AS PESONETTO,
	IFNULL(BB.Magazzino,0) AS QTAMAG,
	A.IDIVA,
	IFNULL(A.IDSTATOART,'3') AS IDSTATOART,
	IFNULL(A.IDFAMASS,-1) AS IDFAMASS,
	IFNULL(B.FAMASS,'NON DISPONIBILE') AS FAMASS,
	A.DATACREAZIONE
	FROM articoli A 
	LEFT JOIN (SELECT ID, DESCRIZIONE AS FAMASS FROM famassort) B ON A.IDFAMASS = B.ID 
	LEFT JOIN (SELECT CODART AS CODICE, PREZZO FROM dettlistini WHERE IdList = v_listino) AA ON A.CODART = AA.CODICE 
	LEFT JOIN (SELECT CODART AS CODICE, (ACQUISTATO - RESO - VENDUTO - USCITE - SCADUTI) AS Magazzino FROM movimenti) BB ON A.CODART = BB.CODICE  
	WHERE A.DESCRIZIONE LIKE CONCAT('%',v_filtro,'%')  
    ORDER BY CODART; 
ELSEIF (length(v_filtro) = 0) THEN
	SELECT 
	(@row_number:=@row_number + 1) as RIGA,
	A.CODART,
	A.DESCRIZIONE,
	IFNULL(AA.PREZZO, 0) AS PREZZO,
	CASE WHEN IFNULL(A.PESONETTO,0) > 0 THEN ROUND(IFNULL(AA.PREZZO, 0) / IFNULL(A.PESONETTO,0),2) ELSE 0 END AS PRZKG,
	A.UM,
	IFNULL(A.CODSTAT,'') AS CODSTAT,
	IFNULL(A.PZCART,0) AS PZCART,
	ROUND(IFNULL(A.PESONETTO,0),2) AS PESONETTO,
	IFNULL(BB.Magazzino,0) AS QTAMAG,
	A.IDIVA,
	IFNULL(A.IDSTATOART,'3') AS IDSTATOART,
	IFNULL(A.IDFAMASS,-1) AS IDFAMASS,
	IFNULL(B.FAMASS,'NON DISPONIBILE') AS FAMASS,
	A.DATACREAZIONE
	FROM articoli A 
	LEFT JOIN (SELECT ID, DESCRIZIONE AS FAMASS FROM famassort) B ON A.IDFAMASS = B.ID 
	LEFT JOIN (SELECT CODART AS CODICE, PREZZO FROM dettlistini WHERE IdList = v_listino) AA ON A.CODART = AA.CODICE 
	LEFT JOIN (SELECT CODART AS CODICE, (ACQUISTATO - RESO - VENDUTO - USCITE - SCADUTI) AS Magazzino FROM movimenti) BB ON A.CODART = BB.CODICE  
	ORDER BY CODART; 
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_SelArtTest` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_SelArtTest`(
	 v_filtro varchar(50),
     v_listino smallint 
     
)
BEGIN

SET @row_number = 0;
SET @SQLText = CONCAT('','','');

IF (SELECT COUNT(*) FROM articoli WHERE CODART = v_filtro > 0 AND length(v_filtro)) THEN
	SELECT 
	(@row_number:=@row_number + 1) as RIGA,
	A.CODART,
	A.DESCRIZIONE,
	IFNULL(AA.PREZZO, 0) AS PREZZO,
	CASE WHEN IFNULL(A.PESONETTO,0) > 0 THEN ROUND(IFNULL(AA.PREZZO, 0) / IFNULL(A.PESONETTO,0),2) ELSE 0 END AS PRZKG,
	A.UM,
	IFNULL(A.CODSTAT,'') AS CODSTAT,
	IFNULL(A.PZCART,0) AS PZCART,
	ROUND(IFNULL(A.PESONETTO,0),2) AS PESONETTO,
	IFNULL(BB.Magazzino,0) AS QTAMAG,
	A.IDIVA,
	IFNULL(A.IDSTATOART,'3') AS IDSTATOART,
	IFNULL(A.IDFAMASS,-1) AS IDFAMASS,
	IFNULL(B.FAMASS,'NON DISPONIBILE') AS FAMASS,
	A.DATACREAZIONE
	FROM articoli A 
	LEFT JOIN (SELECT ID, DESCRIZIONE AS FAMASS FROM famassort) B ON A.IDFAMASS = B.ID 
	LEFT JOIN (SELECT CODART AS CODICE, PREZZO FROM dettlistini WHERE IdList = v_listino) AA ON A.CODART = AA.CODICE 
	LEFT JOIN (SELECT CODART AS CODICE, (ACQUISTATO - RESO - VENDUTO - USCITE - SCADUTI) AS Magazzino FROM movimenti) BB ON A.CODART = BB.CODICE  
    WHERE A.CODART = v_filtro
    ORDER BY CODART; 
ELSEIF (IsNumeric(v_filtro) = 0 AND length(v_filtro)) THEN
	SELECT 
	(@row_number:=@row_number + 1) as RIGA,
	A.CODART,
	A.DESCRIZIONE,
	IFNULL(AA.PREZZO, 0) AS PREZZO,
	CASE WHEN IFNULL(A.PESONETTO,0) > 0 THEN ROUND(IFNULL(AA.PREZZO, 0) / IFNULL(A.PESONETTO,0),2) ELSE 0 END AS PRZKG,
	A.UM,
	IFNULL(A.CODSTAT,'') AS CODSTAT,
	IFNULL(A.PZCART,0) AS PZCART,
	ROUND(IFNULL(A.PESONETTO,0),2) AS PESONETTO,
	IFNULL(BB.Magazzino,0) AS QTAMAG,
	A.IDIVA,
	IFNULL(A.IDSTATOART,'3') AS IDSTATOART,
	IFNULL(A.IDFAMASS,-1) AS IDFAMASS,
	IFNULL(B.FAMASS,'NON DISPONIBILE') AS FAMASS,
	A.DATACREAZIONE
	FROM articoli A 
	LEFT JOIN (SELECT ID, DESCRIZIONE AS FAMASS FROM famassort) B ON A.IDFAMASS = B.ID 
	LEFT JOIN (SELECT CODART AS CODICE, PREZZO FROM dettlistini WHERE IdList = v_listino) AA ON A.CODART = AA.CODICE 
	LEFT JOIN (SELECT CODART AS CODICE, (ACQUISTATO - RESO - VENDUTO - USCITE - SCADUTI) AS Magazzino FROM movimenti) BB ON A.CODART = BB.CODICE  
	WHERE A.DESCRIZIONE LIKE CONCAT('%',v_filtro,'%')  
    ORDER BY CODART; 
ELSE
	SELECT 
	(@row_number:=@row_number + 1) as RIGA,
	A.CODART,
	A.DESCRIZIONE,
	IFNULL(AA.PREZZO, 0) AS PREZZO,
	CASE WHEN IFNULL(A.PESONETTO,0) > 0 THEN ROUND(IFNULL(AA.PREZZO, 0) / IFNULL(A.PESONETTO,0),2) ELSE 0 END AS PRZKG,
	A.UM,
	IFNULL(A.CODSTAT,'') AS CODSTAT,
	IFNULL(A.PZCART,0) AS PZCART,
	ROUND(IFNULL(A.PESONETTO,0),2) AS PESONETTO,
	IFNULL(BB.Magazzino,0) AS QTAMAG,
	A.IDIVA,
	IFNULL(A.IDSTATOART,'3') AS IDSTATOART,
	IFNULL(A.IDFAMASS,-1) AS IDFAMASS,
	IFNULL(B.FAMASS,'NON DISPONIBILE') AS FAMASS,
	A.DATACREAZIONE
	FROM articoli A 
	LEFT JOIN (SELECT ID, DESCRIZIONE AS FAMASS FROM famassort) B ON A.IDFAMASS = B.ID 
	LEFT JOIN (SELECT CODART AS CODICE, PREZZO FROM dettlistini WHERE IdList = v_listino) AA ON A.CODART = AA.CODICE 
	LEFT JOIN (SELECT CODART AS CODICE, (ACQUISTATO - RESO - VENDUTO - USCITE - SCADUTI) AS Magazzino FROM movimenti) BB ON A.CODART = BB.CODICE  
	ORDER BY CODART; 
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_SelDatiTrasm` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_SelDatiTrasm`(
	v_IdTerminale nchar(10)
)
BEGIN

SELECT 
A.Data,
A.IdTerminale,
A.Barcode,
C.DESCRIZIONE,
A.Qta
FROM trasmissioni A
LEFT JOIN barcode B ON A.Barcode = B.BARCODE
LEFT JOIN articoli C ON B.CODART = C.CODART
WHERE IdTerminale = v_IdTerminale;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_SelMovimenti` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_SelMovimenti`(
	 v_filtro varchar(50) 
)
BEGIN

IF (SELECT COUNT(*) FROM articoli WHERE CODART = v_filtro > 0 AND length(v_filtro)) THEN
	BEGIN
		SELECT 
        A.CODART,
        B.DESCRIZIONE,
        A.PRZACQ,
        B.UM,
        A.ACQUISTATO,
        A.RESO,
        A.VENDUTO,
        A.USCITE,
        A.SCADUTI,
		(A.ACQUISTATO - A.RESO - A.VENDUTO - A.USCITE - A.SCADUTI) AS QTAMAG,
		(A.SCADUTI / A.ACQUISTATO) AS INCSCAD  
		FROM movimenti A JOIN articoli B ON A.CODART = B.CODART  
        WHERE B.CODART = v_filtro; 
	END;
ELSEIF (IsNumeric(v_filtro) = 0 AND length(v_filtro)) THEN
	BEGIN
		SELECT 
        A.CODART,
        B.DESCRIZIONE,
        A.PRZACQ,
        B.UM,
        A.ACQUISTATO,
        A.RESO,
        A.VENDUTO,
        A.USCITE,
        A.SCADUTI,
		(A.ACQUISTATO - A.RESO - A.VENDUTO - A.USCITE - A.SCADUTI) AS QTAMAG,
		(A.SCADUTI / A.ACQUISTATO) AS INCSCAD  
		FROM movimenti A JOIN articoli B ON A.CODART = B.CODART  
        WHERE B.DESCRIZIONE LIKE CONCAT('%',v_filtro,'%'); 
    END;
ELSE
	BEGIN
		SELECT 
        A.CODART,
        B.DESCRIZIONE,
        A.PRZACQ,
        B.UM,
        A.ACQUISTATO,
        A.RESO,
        A.VENDUTO,
        A.USCITE,
        A.SCADUTI,
		(A.ACQUISTATO - A.RESO - A.VENDUTO - A.USCITE - A.SCADUTI) AS QTAMAG,
		(A.SCADUTI / A.ACQUISTATO) AS INCSCAD  
		FROM movimenti A JOIN articoli B ON A.CODART = B.CODART;  
    END;
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Sp_SelPromoActive` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Nicola`@`%` PROCEDURE `Sp_SelPromoActive`()
BEGIN

SELECT * FROM dettpromo
WHERE CAST(CURDATE() AS DATE) BETWEEN INIZIO AND FINE -- PROMO ATTIVE
AND IDTIPOPROMO = 1; -- SOLO TAGLIO PREZZO

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-20 15:18:51
