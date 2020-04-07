-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema musica_bd
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema musica_bd
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `musica_bd` DEFAULT CHARACTER SET utf8 ;
USE `musica_bd` ;

-- -----------------------------------------------------
-- Table `musica_bd`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musica_bd`.`usuario` (
  `Idusuario` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Apellido` VARCHAR(45) NOT NULL,
  `Correo` VARCHAR(45) NOT NULL,
  `Pasword` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `musica_bd`.`cancion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musica_bd`.`cancion` (
  `IdCancion` INT(11) NOT NULL AUTO_INCREMENT,
  `genero` VARCHAR(45) NOT NULL,
  `autor` VARCHAR(45) NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `fecha` DATE NOT NULL,
  `duracion` TIME NOT NULL,
  `Usuario_Idusuario` INT(11) NOT NULL,
  PRIMARY KEY (`IdCancion`),
  INDEX `fk_Cancion_Usuario1_idx` (`Usuario_Idusuario` ASC) ,
  CONSTRAINT `fk_Cancion_Usuario1`
    FOREIGN KEY (`Usuario_Idusuario`)
    REFERENCES `musica_bd`.`usuario` (`Idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `musica_bd`.`listadereproduccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musica_bd`.`listadereproduccion` (
  `idListadeReproduccion` INT(11) NOT NULL AUTO_INCREMENT,
  `nombreLista` VARCHAR(45) NOT NULL,
  `Usuario_Idusuario` INT(11) NOT NULL,
  PRIMARY KEY (`idListadeReproduccion`),
  INDEX `fk_ListadeReproduccion_Usuario1_idx` (`Usuario_Idusuario` ASC) ,
  CONSTRAINT `fk_ListadeReproduccion_Usuario1`
    FOREIGN KEY (`Usuario_Idusuario`)
    REFERENCES `musica_bd`.`usuario` (`Idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `musica_bd`.`cancion_has_listadereproduccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musica_bd`.`cancion_has_listadereproduccion` (
  `Cancion_IdCancion` INT(11) NOT NULL,
  `ListadeReproduccion_idListadeReproduccion` INT(11) NOT NULL,
  PRIMARY KEY (`Cancion_IdCancion`, `ListadeReproduccion_idListadeReproduccion`),
  INDEX `fk_Cancion_has_ListadeReproduccion_ListadeReproduccion1_idx` (`ListadeReproduccion_idListadeReproduccion` ASC) ,
  INDEX `fk_Cancion_has_ListadeReproduccion_Cancion_idx` (`Cancion_IdCancion` ASC) ,
  CONSTRAINT `fk_Cancion_has_ListadeReproduccion_Cancion`
    FOREIGN KEY (`Cancion_IdCancion`)
    REFERENCES `musica_bd`.`cancion` (`IdCancion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cancion_has_ListadeReproduccion_ListadeReproduccion1`
    FOREIGN KEY (`ListadeReproduccion_idListadeReproduccion`)
    REFERENCES `musica_bd`.`listadereproduccion` (`idListadeReproduccion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `musica_bd` ;

-- -----------------------------------------------------
-- procedure SP_I_Cancion
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Cancion`(`pgenero` VARCHAR(45), `pautor` VARCHAR(45), `ptitulo` VARCHAR(45), `pfecha` DATE,
`pduracion` TIME, `pUsuario_Idusuario` INT)
BEGIN		
		INSERT INTO cancion(genero,autor ,titulo,fecha ,duracion ,Usuario_Idusuario)
		VALUES(pgenero,pautor,ptitulo,pfecha,pduracion,pUsuario_Idusuario);	
        END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_I_ListadeReproduccion
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_ListadeReproduccion`(`pnombreLista` VARCHAR(45), `pUsuario_Idusuario` INT)
BEGIN		
		INSERT INTO listadeReproduccion(nombreLista,Usuario_Idusuario)
		VALUES(pnombreLista,pUsuario_Idusuario);	
        END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_I_Usuario
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Usuario`(`pnombre` VARCHAR(45),
 `papellido` VARCHAR(45), `pcorreo` VARCHAR(45), `ppasword` VARCHAR(45))
BEGIN		
		INSERT INTO usuario(Nombre,Apellido,Correo,Pasword)
		VALUES(pnombre,papellido,pcorreo,ppasword);
        END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_Cancion
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Cancion`(`pUsuario` INT(11))
BEGIN
		SELECT * FROM Cancion AS c WHERE c.Usuario_Idusuario= pUsuario;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_CancionPorParametro
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_CancionPorParametro`(`pcriterio` VARCHAR(20), `pbusqueda` VARCHAR(20))
BEGIN
	IF pcriterio = "id" THEN
		SELECT cn.IdCancion,cn.genero,cn.autor,cn.titulo,cn.fecha,cn.duracion,cn.Usuario_Idusuario FROM cancion AS cn WHERE cn.IdCancion =pbusqueda;
	ELSEIF pcriterio = "genero" THEN
		SELECT cn.IdCancion,cn.genero,cn.autor,cn.titulo,cn.fecha,cn.duracion,cn.Usuario_Idusuario FROM cancion AS cn WHERE cn.genero LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "autor" THEN
		SELECT cn.IdCancion,cn.genero,cn.autor,cn.titulo,cn.fecha,cn.duracion,cn.Usuario_Idusuario FROM cancion AS cn WHERE cn.autor LIKE CONCAT("%",pbusqueda,"%");
   ELSEIF pcriterio = "titulo" THEN
		SELECT cn.IdCancion,cn.genero,cn.autor,cn.titulo,cn.fecha,cn.duracion,cn.Usuario_Idusuario FROM cancion AS cn WHERE cn.titulo LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "fecha" THEN
		SELECT cn.IdCancion,cn.genero,cn.autor,cn.titulo,cn.fecha,cn.duracion,cn.Usuario_Idusuario FROM cancion AS cn WHERE cn.fecha=pbusqueda;
	ELSEIF pcriterio = "usuario" THEN
		SELECT cn.IdCancion,cn.genero,cn.autor,cn.titulo,cn.fecha,cn.duracion,cn.Usuario_Idusuario FROM cancion AS cn WHERE cn.Usuario_Idusuario=pbusqueda;
	ELSE
		SELECT cn.IdCancion,cn.genero,cn.autor,cn.titulo,cn.fecha,cn.duracion,cn.Usuario_Idusuario FROM cancion AS cn WHERE cn;
	END IF; 
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_Lista
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Lista`()
BEGIN
		SELECT * FROM ListadeReproduccion;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_ListaPorParametro
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_ListaPorParametro`(`pcriterio` VARCHAR(20), `pbusqueda` VARCHAR(20))
BEGIN
	IF pcriterio = "id" THEN
		SELECT lr.idListadeReproduccion,lr.nombreLista,lr.Usuario_Idusuario FROM listadereproduccion AS lr WHERE lr.idListadeReproduccion =pbusqueda;
	ELSEIF pcriterio = "nombre" THEN
		SELECT lr.idListadeReproduccion,lr.nombreLista,lr.Usuario_Idusuario FROM listadereproduccion AS lr WHERE lr.nombreLista LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "usuario" THEN
		SELECT lr.idListadeReproduccion,lr.nombreLista,lr.Usuario_Idusuario FROM listadereproduccion AS lr WHERE lr.Usuario_Idusuario=pbusqueda;
	ELSE
		SELECT lr.idListadeReproduccion,lr.nombreLista,lr.Usuario_Idusuario FROM listadereproduccion AS lr WHERE lr;
	END IF; 
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_Listadereproduccion
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Listadereproduccion`(pUsuario INT(11))
BEGIN
		SELECT * FROM ListadeReproduccion  WHERE Usuario_Idusuario = pUsuario;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_TCancion
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_TCancion`()
BEGIN
		SELECT * FROM Cancion;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_Usuario
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_Usuario`()
BEGIN
		SELECT * FROM Usuario;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_S_UsuarioPorParametro
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_S_UsuarioPorParametro`(`pcriterio` VARCHAR(20), `pbusqueda` VARCHAR(20))
BEGIN
	IF pcriterio = "id" THEN
		SELECT u.Idusuario,u.Nombre,u.Apellido,u.Correo,u.Pasword FROM usuario AS u WHERE u.idusuario=pbusqueda;
	ELSEIF pcriterio = "nombre" THEN
		SELECT u.Idusuario,u.Nombre,u.Apellido,u.Correo,u.Pasword FROM usuario AS u WHERE u.nombre LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "apellido" THEN
		SELECT u.Idusuario,u.Nombre,u.Apellido,u.Correo,u.Pasword FROM usuario AS u WHERE u.apellido LIKE CONCAT("%",pbusqueda,"%");
	ELSEIF pcriterio = "correo" THEN
		SELECT u.Idusuario,u.Nombre,u.Apellido,u.Correo,u.Pasword FROM usuario AS u WHERE u.correo LIKE CONCAT("%",pbusqueda,"%");
	ELSE
		SELECT u.Idusuario,u.Nombre,u.Apellido,u.Correo,u.Pasword FROM usuario AS u;
	END IF; 
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_U_Cancion
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Cancion`(`pidcancion`INT(11),`pgenero` VARCHAR(45),`pautor` VARCHAR(45),
 `ptitulo` VARCHAR(45), `pfecha` DATE, `pduracion` TIME,`pUsuario_Idusuario` INT(11))
BEGIN
		UPDATE Cancion SET
			genero=pgenero,
            autor=pautor,
			titulo=ptitulo,
			fecha=pfecha,
			duracion=pduracion,
            Usuario_Idusuario=pUsuario_Idusuario
		WHERE IdCancion= pidcancion;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_U_ListadeReproduccion
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_ListadeReproduccion`(`pidListadeReproduccion` INT,`pnombreLista` VARCHAR(45),`pUsuario_Idusuario` INT(11))
BEGIN
		UPDATE ListadeReproduccion SET
			nombreLista=pnombreLista,
            Usuario_Idusuario=pUsuario_Idusuario
		WHERE idListadeReproduccion= pidListadeReproduccion;
	END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SP_U_Usuario
-- -----------------------------------------------------

DELIMITER $$
USE `musica_bd`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_U_Usuario`( `pid` VARCHAR(45),`pnombre` VARCHAR(45),
 `papellido` VARCHAR(45), `pcorreo` VARCHAR(45), `ppasword` VARCHAR(45))
BEGIN
		UPDATE Usuario SET
			Nombre=pnombre,
			Apellido=papellido,
			Correo=pcorreo,
			Pasword=ppasword
		WHERE Idusuario= pid;
	END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
