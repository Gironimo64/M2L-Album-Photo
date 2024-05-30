<?php
/**
 * Ce fichier gère l'appelle de la procédure stocké remplaceDate().
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait l'id de l'utilisateur,
 * puis exécute une requête SQL CALL pour faire la procédure stocké
 * de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme :
 * {
 *     "id": "id"
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
$sql = $con->prepare("CALL remplaceDate(:id)");
$sql->bindValue(':id', $data['id'], PDO::PARAM_INT);
$sql->execute(); // Exécution de la requête SQL pour insérer l'utilisateur dans la base de données
?>
