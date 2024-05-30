<?php
/**
 * Ce fichier gère l'inscription d'un nouvel utilisateur dans la base de données.
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait le pseudo et le mot de passe de l'utilisateur,
 * puis exécute une requête SQL INSERT pour insérer ces informations dans la table 'utilisateur'
 * de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme :
 * {
 *     "pseudo": "Nom_d_utilisateur",
 *     "mdp": "Mot_de_passe"
 * }
 *
 * Le fichier utilise PDO pour se connecter à la base de données MySQL.
 *
 * @package AlbumPhotoMobil
 */

// Informations de connexion à la base de données
$host = "localhost";
$username = "Modificateur";
$password = "Modificateur123";
$db_name = "albumphoto";

// Connexion à la base de données via PDO
$con = new PDO('mysql:host='.$host.';dbname='.$db_name, $username, $password);

// Récupération des données JSON reçues
$data = json_decode(file_get_contents("php://input"), true);

// Préparation de la requête SQL pour insérer le nouvel utilisateur avec le pseudo et le mot de passe spécifiés
$sql = $con->prepare("INSERT INTO `utilisateur`(`pseudo`, `mdp`) VALUES (:pseudo, :mdp)");
$sql->bindValue(':pseudo', $data['pseudo'], PDO::PARAM_STR);
$sql->bindValue(':mdp', $data['mdp'], PDO::PARAM_STR);
$sql->execute(); // Exécution de la requête SQL pour insérer l'utilisateur dans la base de données
?>
