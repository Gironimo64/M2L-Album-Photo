-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 06 mai 2024 à 23:34
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `albumphoto`
--

DELIMITER $$
--
-- Procédures
--
DROP PROCEDURE IF EXISTS `archivePhoto6Mois`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `archivePhoto6Mois` ()   BEGIN
    DECLARE date_actuel timestamp;
    DECLARE photo_date timestamp;
    
    SET date_actuel = CURRENT_TIMESTAMP;
    
    -- Sélectionnez les photos qui ont plus de 6 mois et déplacez-les vers la table d'archives
    INSERT INTO archive_photos (id_photo, lien_photo, legende, date_poster, id_utilisateur)
    SELECT id_photo, lien_photo, legende, date_poster, id_utilisateur
    FROM photos
    WHERE DATEDIFF(date_actuel, date_poster) > 180;
    
    DELETE FROM photos
    WHERE DATEDIFF(date_actuel, date_poster) > 180;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `archive_photos`
--

DROP TABLE IF EXISTS `archive_photos`;
CREATE TABLE IF NOT EXISTS `archive_photos` (
  `id_photo` bigint NOT NULL,
  `lien_photo` varchar(500) NOT NULL,
  `legende` varchar(500) NOT NULL,
  `date_poster` timestamp NOT NULL,
  `id_utilisateur` int NOT NULL,
  `date_archive` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_photo`),
  KEY `fk_archivePhoto_utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `archive_photos`
--

INSERT INTO `archive_photos` (`id_photo`, `lien_photo`, `legende`, `date_poster`, `id_utilisateur`, `date_archive`) VALUES
(32, 'surf.jpg', 'Nous somme heureux d\'organiser une colo de surf du:\r\n16 juin au 4 juillet.\r\nMerci de nous contacter si vous êtes intéressé', '2022-11-23 22:39:58', 1, '2024-04-04 13:10:42'),
(33, '1er course.jpg', 'Enfin mes effort on payé première au championnat régional ', '2023-04-16 21:23:40', 9, '2024-04-05 14:41:25');

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id_com` bigint NOT NULL AUTO_INCREMENT,
  `commentaire` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `date_com` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_photo` bigint NOT NULL,
  `id_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_com`),
  KEY `fk_commentaire_photos` (`id_photo`),
  KEY `fk_commentaire_utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`id_com`, `commentaire`, `date_com`, `id_photo`, `id_utilisateur`) VALUES
(25, 'c\'est bizarre une entorse à cinq minute de la fin ;)', '2023-11-09 22:45:54', 24, 9),
(26, 'mon fils à été ravie, je pense l\'inscrire à un de vos club prochainement!', '2023-11-09 22:47:28', 25, 9),
(27, 'Quel sont les licences proposé l\'année prochaine?', '2023-11-09 22:48:54', 25, 9),
(28, 'j\'aurai aimée pouvoir voir son match', '2023-11-09 22:52:19', 26, 9),
(29, 'Peut on inscrire plusieurs personne?', '2023-11-09 22:53:37', 27, 9),
(30, 'hâte de venir testé le terrain  ', '2023-11-09 22:54:16', 28, 9),
(31, 'la chance tous ça parce que tu n\'as pas eu d\'entorse toi', '2023-11-09 22:57:06', 23, 10),
(32, 'mon fils s\'est cassé une dent...', '2023-11-09 22:58:53', 25, 10),
(33, 'et moi on m\'as pas féliciter alors que je suis arrivé 2iéme', '2023-11-09 23:00:00', 26, 10),
(34, 'je travaille à se moment là...', '2023-11-09 23:00:30', 27, 10),
(35, 'c\'est un vrai sport? ', '2023-11-09 23:01:01', 28, 10);

-- --------------------------------------------------------

--
-- Structure de la table `photos`
--

DROP TABLE IF EXISTS `photos`;
CREATE TABLE IF NOT EXISTS `photos` (
  `id_photo` bigint NOT NULL AUTO_INCREMENT,
  `lien_photo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `legende` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `date_poster` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_photo`),
  KEY `fk_photos_utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Déchargement des données de la table `photos`
--

INSERT INTO `photos` (`id_photo`, `lien_photo`, `legende`, `date_poster`, `id_utilisateur`) VALUES
(23, '1er course.jpg', 'Enfin mes effort on payé première au championnat régional ', '2023-11-09 22:23:40', 9),
(24, 'badbingtone.jpg', 'je me suis malheureusement fait une entorse en final sinon j\'aurai put gagner!!', '2023-11-09 22:24:41', 10),
(25, 'football.jpg', 'Heureux d\'avoir put organiser cette journée de découvert au sport pour les enfants!', '2023-11-09 22:35:23', 1),
(26, 'volley.jpg', 'Nous félicitons le gagnant de la 3émié édition de notre championnat régionaux   ', '2023-11-09 22:37:52', 1),
(27, 'surf.jpg', 'Nous somme heureux d\'organiser une colo de surf du:\n16 juin au 4 juillet.\nMerci de nous contacter si vous êtes intéressé', '2023-11-09 22:39:58', 1),
(28, 'petanque.jpg', 'Nous sommes heureux de vous annoncer l\'ouverture de notre fédération de bouliste!', '2023-11-09 22:41:24', 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(50) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `pseudo`, `mdp`) VALUES
(1, 'M2L', '$2a$10$9JUkpNvcoLKQ9rFPC/ALPu5aq1wR2Vej.H4/XaA0WN3h6ebf41oKe'),
(9, 'stéphanie', '$2a$10$oXDo0QZopDv8CRNxHRZo9eFfkBoHhSQPIpc7iUSt9KaV8xu7j3LuG'),
(10, 'Jérome', '$2a$10$oXDo0QZopDv8CRNxHRZo9eFfkBoHhSQPIpc7iUSt9KaV8xu7j3LuG'),
(11, 'thomas', '$2a$10$oXDo0QZopDv8CRNxHRZo9eFfkBoHhSQPIpc7iUSt9KaV8xu7j3LuG'),
(12, 'LeGirophare', '$2a$10$oXDo0QZopDv8CRNxHRZo9eFfkBoHhSQPIpc7iUSt9KaV8xu7j3LuG'),
(15, 'test', '$2a$10$gSGF4rjtV/g/J5FNZr2qO.bEPS0O.fot15.utnZdf5rNyR121t5oq');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `archive_photos`
--
ALTER TABLE `archive_photos`
  ADD CONSTRAINT `fk_archivePhoto_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `fk_commentaire_photos` FOREIGN KEY (`id_photo`) REFERENCES `photos` (`id_photo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_commentaire_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `photos`
--
ALTER TABLE `photos`
  ADD CONSTRAINT `fk_photos_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

DELIMITER $$
--
-- Évènements
--
DROP EVENT IF EXISTS `archive_photos_Event`$$
CREATE DEFINER=`root`@`localhost` EVENT `archive_photos_Event` ON SCHEDULE EVERY 1 WEEK STARTS '2024-04-05 16:41:25' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
    CALL archivePhoto6Mois();
END$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
