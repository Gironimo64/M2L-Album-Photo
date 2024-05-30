<?php
/**
 * Ce fichier gère l'insertion d'un nouveau commentaire dans la base de données.
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait les informations du commentaire, de la photo
 * et de l'utilisateur associé, puis les insère dans la table 'commentaire' de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme :
 * {
 *     "commentaire": "Contenu du commentaire",
 *     "photo": {
 *         "id_photo": 123
 *         // Autres informations sur la photo si nécessaire
 *     },
 *     "util": {
 *         "id": 456
 *         // Autres informations sur l'utilisateur si nécessaire
 *     }
 * }
 *
 * Le fichier utilise PDO pour se connecter à la base de données MySQL.
 *
 * Après l'insertion du commentaire, il renvoie les mêmes données JSON reçues en réponse.
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

// Extraction des informations du commentaire, de la photo et de l'utilisateur
$commentaire = $data['commentaire'];
$photo = $data['photo'];
$utilisateur = $data['util'];

$id_photo = $photo['id_photo']; // Récupération de l'ID de la photo
$id_util = $utilisateur['id']; // Récupération de l'ID de l'utilisateur

// Préparation de la requête SQL pour insérer le nouveau commentaire
$sql = $con->prepare("INSERT INTO `commentaire`(`commentaire`, `id_photo`, `id_utilisateur`) VALUES (:com, :idPhoto, :idUtil)");
$sql->bindValue(':com', $commentaire, PDO::PARAM_STR);
$sql->bindValue(':idPhoto', $id_photo, PDO::PARAM_INT);
$sql->bindValue(':idUtil', $id_util, PDO::PARAM_INT);
$sql->execute(); // Exécution de la requête SQL

// Création d'un tableau avec les mêmes données JSON reçues pour la réponse
$array = array(
    'commentaire' => $commentaire,
    'photo' => $photo,
    'utilisateur' => $utilisateur
);

// Conversion du tableau en JSON et envoi en réponse
echo json_encode($array);
?>
