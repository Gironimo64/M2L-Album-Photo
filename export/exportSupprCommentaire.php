<?php
/**
 * Ce fichier gère la suppression d'un commentaire de la base de données.
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait l'identifiant du commentaire à supprimer,
 * puis exécute une requête SQL DELETE pour supprimer le commentaire correspondant de la table 'commentaire'
 * de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme :
 * {
 *     "id_com": ID_du_commentaire
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

// Préparation de la requête SQL pour supprimer le commentaire avec l'identifiant spécifié
$sql = $con->prepare("DELETE FROM commentaire WHERE id_com=:idCom");
$sql->bindValue(':idCom', $data['id_com'], PDO::PARAM_INT);
$sql->execute(); // Exécution de la requête SQL pour supprimer le commentaire de la base de données
?>
