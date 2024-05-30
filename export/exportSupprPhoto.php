<?php
/**
 * Ce fichier gère la suppression d'une photo de la base de données.
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait l'identifiant de la photo à supprimer,
 * puis exécute une requête SQL DELETE pour supprimer la photo correspondante de la table 'photos'
 * de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme :
 * {
 *     "id_photo": ID_de_la_photo
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

// Préparation de la requête SQL pour supprimer la photo avec l'identifiant spécifié
$sql = $con->prepare("DELETE FROM photos WHERE id_photo=:idPhoto");
$sql->bindValue(':idPhoto', $data['id_photo'], PDO::PARAM_INT);
$sql->execute(); // Exécution de la requête SQL pour supprimer la photo de la base de données
?>
