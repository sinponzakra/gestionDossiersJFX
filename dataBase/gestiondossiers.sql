-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 20 mai 2019 à 21:43
-- Version du serveur :  5.7.23
-- Version de PHP :  7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gestiondossiers`
--

-- --------------------------------------------------------

--
-- Structure de la table `acquereur`
--

DROP TABLE IF EXISTS `acquereur`;
CREATE TABLE IF NOT EXISTS `acquereur` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `acquereur`
--

INSERT INTO `acquereur` (`id`) VALUES
(1),
(3);

-- --------------------------------------------------------

--
-- Structure de la table `authentification`
--

DROP TABLE IF EXISTS `authentification`;
CREATE TABLE IF NOT EXISTS `authentification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `dossier`
--

DROP TABLE IF EXISTS `dossier`;
CREATE TABLE IF NOT EXISTS `dossier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `acquereur_id` int(11) DEFAULT NULL,
  `lebien_acquereurId` int(11) DEFAULT NULL,
  `lebien_dateContrat` date DEFAULT NULL,
  `lebien_vendeurId` int(11) DEFAULT NULL,
  `vendeur_id` int(11) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6i8ma00stg4s23ivupu856hfb` (`acquereur_id`),
  KEY `FK_frl1mku9u44topmqemern4daa` (`lebien_acquereurId`,`lebien_dateContrat`,`lebien_vendeurId`),
  KEY `FK_dcsbube1jpycvvxuwb2xonhs9` (`vendeur_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `dossier`
--

INSERT INTO `dossier` (`id`, `date`, `acquereur_id`, `lebien_acquereurId`, `lebien_dateContrat`, `lebien_vendeurId`, `vendeur_id`, `etat`) VALUES
(1, '2019-05-14', 1, 1, '2019-05-31', 5, 4, 'Active'),
(2, '2019-05-10', 1, 1, '2019-05-24', 2, 5, 'Inactive');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
CREATE TABLE IF NOT EXISTS `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `lebien`
--

DROP TABLE IF EXISTS `lebien`;
CREATE TABLE IF NOT EXISTS `lebien` (
  `acquereurId` int(11) NOT NULL,
  `dateContrat` date NOT NULL,
  `vendeurId` int(11) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `avance` double DEFAULT NULL,
  `charge` varchar(255) DEFAULT NULL,
  `consistance` varchar(255) DEFAULT NULL,
  `delaiDuCompromisDeVente` date DEFAULT NULL,
  `prixCession` double DEFAULT NULL,
  `rc` varchar(255) DEFAULT NULL,
  `ri` varchar(255) DEFAULT NULL,
  `situationLocative` varchar(255) DEFAULT NULL,
  `situationSyndic` varchar(255) DEFAULT NULL,
  `superficie` varchar(255) DEFAULT NULL,
  `chargesEtTaxes` varchar(255) DEFAULT NULL,
  `tf` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`acquereurId`,`dateContrat`,`vendeurId`),
  KEY `FK_7rkdhabyb1ahrx515kel5crm3` (`vendeurId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `lebien`
--

INSERT INTO `lebien` (`acquereurId`, `dateContrat`, `vendeurId`, `adresse`, `avance`, `charge`, `consistance`, `delaiDuCompromisDeVente`, `prixCession`, `rc`, `ri`, `situationLocative`, `situationSyndic`, `superficie`, `chargesEtTaxes`, `tf`) VALUES
(1, '2019-05-24', 2, 'chi  blasa', 250000, 'maerftch', 'maerftch', '2019-05-23', 500000, 'non', 'nono', 'oui', 'non', '4500M²', 'fabor', 'non'),
(1, '2019-05-31', 5, 'test ', 50, 'k,k,', 'k,k,', '2019-05-22', 50, 'ok', 'ok', 'k,k,', 'k,k,', '2121', '50', 'ok');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `PERSONNE_TYPE` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adresse` varchar(255) DEFAULT NULL,
  `adresseCourriel` varchar(255) DEFAULT NULL,
  `associe` varchar(255) DEFAULT NULL,
  `ben` varchar(255) DEFAULT NULL,
  `cin` varchar(255) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fonction` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `regimeMariage` varchar(255) DEFAULT NULL,
  `situationFamiliale` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `typePersonne` varchar(255) DEFAULT NULL,
  `lieuMariage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`PERSONNE_TYPE`, `id`, `adresse`, `adresseCourriel`, `associe`, `ben`, `cin`, `dateNaissance`, `email`, `fonction`, `nom`, `prenom`, `regimeMariage`, `situationFamiliale`, `telephone`, `typePersonne`, `lieuMariage`) VALUES
('Acquereur', 1, 'Azli sud 2 n347 jhjh kjkj khkjh', 'Azli sud 2 N347', 'non', 'Lahcen', 'EE851405', '1997-05-09', 'oussamaabourial97@gmail.com', 'Etudiant', 'Abourial', 'Oussama', 'non', 'Celibataire', '0643277039', 'Personne Physique', 'non'),
('Vendeur', 2, 'Sidi Bouathman rak fahm', 'tma nit ', 'non', 'Mohammed', 'EA196732', '1997-01-13', 'pandaeca@gmail.com', 'Etudiant', 'Ouaqasse', 'Mounsif', 'non', 'Celibataire', '06666666', 'Personne Physique', 'non'),
('Acquereur', 3, 'Azli sud 2 n347 jhjh kjkj khkjh', 'Azli sud 2 N347', 'non', 'Lahcen', 'EE851405', '1997-05-09', 'oussamaabourial97@gmail.com', 'Etudiant', 'wahya', 'test', 'non', 'Celibataire', '0643277039', 'Personne Physique', 'non'),
('Vendeur', 4, 'Sidi Bouathman rak fahm', 'tma nit ', 'non', 'said', 'EA196732', '1997-01-13', 'pandaeca@gmail.com', 'Etudiant', 'Ouaqasse', 'Mounsif', 'non', 'Celibataire', '06666666', 'Personne Physique', 'non'),
('Vendeur', 5, 'Sidi Bouathman rak fahm', 'tma nit ', 'non', 'said', 'EA196732', '1997-01-13', 'pandaeca@gmail.com', 'Etudiant', 'Ewala', 'Mounsif', 'non', 'Celibataire', '06666666', 'Personne Physique', 'non');

-- --------------------------------------------------------

--
-- Structure de la table `vendeur`
--

DROP TABLE IF EXISTS `vendeur`;
CREATE TABLE IF NOT EXISTS `vendeur` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `vendeur`
--

INSERT INTO `vendeur` (`id`) VALUES
(2),
(4),
(5);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
