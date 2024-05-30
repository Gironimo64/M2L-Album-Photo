<?php
/**
 * Ce fichier contient deux fonctions pour établir une connexion à la base de données MySQL en utilisant PDO.
 *
 * Les fonctions disponibles sont :
 *
 * - connexionPDOLecteur() : Cette fonction établit une connexion à la base de données en tant que lecteur avec les informations d'identification prédéfinies.
 *                            Elle retourne un objet PDO représentant la connexion établie.
 *
 * - connexionPDOModificateur() : Cette fonction établit une connexion à la base de données en tant que modificateur avec les informations d'identification prédéfinies.
 *                                 Elle retourne un objet PDO représentant la connexion établie.
 *
 * Les informations d'identification (nom d'utilisateur, mot de passe, nom de la base de données et serveur) sont prédéfinies dans chaque fonction.
 *
 * @package AlbumPhoto
 */

/**
 * Fonction pour établir une connexion à la base de données en tant que lecteur.
 *
 * @return PDO|null Retourne un objet PDO représentant la connexion établie ou null en cas d'erreur.
 */
function connexionPDOLecteur() {
    // Informations d'identification pour le lecteur
    $login = "lecteur";
    $mdp = "P@ssw0rd";
    $bd = "albumphoto";
    $serveur = "localhost";

    try {
        // Tentative d'établir la connexion à la base de données
        $conn = new PDO("mysql:host=$serveur;dbname=$bd", $login, $mdp, array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES \'UTF8\''));
        // Activation du mode d'affichage des erreurs PDO
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        return $conn; // Retourne l'objet PDO représentant la connexion établie
    } catch (PDOException $e) {
        // Gestion des erreurs de connexion
        print "Erreur de connexion PDO : " . $e->getMessage();
        die(); // Arrêt de l'exécution du script en cas d'erreur
    }
}

/**
 * Fonction pour établir une connexion à la base de données en tant que modificateur.
 *
 * @return PDO|null Retourne un objet PDO représentant la connexion établie ou null en cas d'erreur.
 */
function connexionPDOModificateur() {
    // Informations d'identification pour le modificateur
    $login = "Modificateur";
    $mdp = "Modificateur123";
    $bd = "albumphoto";
    $serveur = "localhost";

    try {
        // Tentative d'établir la connexion à la base de données
        $conn = new PDO("mysql:host=$serveur;dbname=$bd", $login, $mdp, array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES \'UTF8\''));
        // Activation du mode d'affichage des erreurs PDO
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        return $conn; // Retourne l'objet PDO représentant la connexion établie
    } catch (PDOException $e) {
        // Gestion des erreurs de connexion
        print "Erreur de connexion PDO : " . $e->getMessage();
        die(); // Arrêt de l'exécution du script en cas d'erreur
    }
}
?>
