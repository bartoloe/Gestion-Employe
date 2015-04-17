SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
DROP DATABASE IF EXISTS `gestionemploye`;
CREATE DATABASE IF NOT EXISTS `gestionemploye` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `gestionemploye`;

/*CREATE TABLE IF NOT EXISTS `administrateur` (
  `id_administrateur` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_root` tinyint(1) DEFAULT NULL,
  `id_emp` bigint(20),
  PRIMARY KEY (`id_administrateur`),
  KEY `FK_Administrateur_id_emp` (`id_emp`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

INSERT INTO `administrateur` (`id_administrateur`, `is_root`,`id_emp`) VALUES
(1, 1, 1),
(2, 0, 2);*/

CREATE TABLE IF NOT EXISTS `employe` (
  `id_emp` bigint(20) AUTO_INCREMENT,
  `nom_emp` varchar(50) DEFAULT NULL,
  `prenom_emp` varchar(50) DEFAULT NULL,
  `mail_emp` varchar(50) DEFAULT NULL,
  `hashPassword` varchar(50) DEFAULT NULL,
 /* `id_administrateur` bigint(20) DEFAULT NULL,*/
  `administrateur` tinyint(1) DEFAULT 0,
  `id_ligue` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_emp`),
 /* KEY `FK_Employe_administrateur_id_administrateur` (`id_administrateur`),*/
  KEY `FK_Employe_id_ligue` (`id_ligue`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

INSERT INTO `employe` (`id_emp`, `nom_emp`, `prenom_emp`, `mail_emp`, `hashPassword`, `administrateur`, `id_ligue`) VALUES
(1, 'thibault', NULL, NULL, '1294a308300293cccdea4b1e07a0ea7940d450ef', 1, NULL),
(2, 'Meksavanh', 'Teddy', NULL, '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, 1),
(3, 'Lassale', 'Quentin', 'lassalequentin@gmail.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', 0, 1),
(4, 'Querad', 'Mamed', 'mamedquerad@gmail.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', 0, 1),
(5, 'Lenfant', 'Lenfant', 'valentinlenfant@gmail.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', 0, 1),
(6, 'Rillet', 'Alexandre', 'alexandre@gmail.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', 0, 1),
(7, 'Khhatharina', 'Chou', NULL , '7c4a8d09ca3762af61e59520943dc26494f8941b', 0, 1);

CREATE TABLE IF NOT EXISTS `ligue` (
  `id_ligue` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom_ligue` varchar(20),
  `id_empAdmin` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_ligue`),
  KEY `FK_Ligue_id_empAdmin` (`id_empAdmin`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

INSERT INTO `ligue` (`id_ligue`, `nom_ligue`, `id_empAdmin`) VALUES
(1, 'Tennis', 2),
(2, 'Basket', NULL);

ALTER TABLE `employe`
 /* ADD CONSTRAINT `FK_Employe_administrateur_id_administrateur` FOREIGN KEY (`id_administrateur`) REFERENCES `administrateur` (`id_administrateur`) ON DELETE CASCADE,*/
  ADD CONSTRAINT `FK_Employe_id_ligue` FOREIGN KEY (`id_ligue`) REFERENCES `ligue` (`id_ligue`);
 
 /* ON UPDATE CASCADE;*/

ALTER TABLE `ligue`
  ADD CONSTRAINT `FK_Ligue_id_empAdmin` FOREIGN KEY (`id_empAdmin`) REFERENCES `employe` (`id_emp`) ON DELETE CASCADE;
  
 /* ON UPDATE CASCADE;*/
/*ALTER TABLE `administrateur`
  ADD CONSTRAINT `FK_Administrateur_id_emp` FOREIGN KEY (`id_emp`) REFERENCES `employe` (`id_emp`) ON DELETE CASCADE;*/

/*CREATE TRIGGER Trg_createAdmin
	AFTER INSERT
    ON administrateur 
    FOR EACH ROW
Declare
	LCChaine VARCHAR2(100);
BEGIN
    id_$admin=SELECT max(id_administrateur) FROM administrateur;
    id_$emp=SELECT id_emp FROM administrateur WHERE id_empAdmin=id_$admin;
    UPDATE employe SET id_administrateur=id_$admin WHERE id_emp = id_$emp;
END;*/