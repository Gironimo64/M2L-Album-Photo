<?php
/**
 * Ce fichier gère l'insertion d'une nouvelle photo dans la base de données.
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait les informations sur la photo
 * (lien de la photo, légende et ID de l'utilisateur), puis les insère dans la table 'photos' de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme :
 * {
 *     "lien_photo": "URL_de_la_photo",
 *     "legende": "Légende_de_la_photo",
 *     "id": ID_de_l_utilisateur
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

// Préparation de la requête SQL pour insérer la nouvelle photo
$sql = $con->prepare("INSERT INTO `photos`(`lien_photo`, `legende`, `id_utilisateur`) VALUES (:lienPhoto, :legende, :idUtil)");
$sql->bindValue(':lienPhoto', $data['lien_photo'], PDO::PARAM_STR);
$sql->bindValue(':legende', $data['legende'], PDO::PARAM_STR);
$sql->bindValue(':idUtil', $data['id'], PDO::PARAM_INT);
$sql->execute(); // Exécution de la requête SQL pour insérer la photo dans la base de données
?>
