<?php
/**
 * Ce fichier récupère les photos associées à un utilisateur dans la base de données.
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait l'identifiant de l'utilisateur
 * à partir de ces données, puis exécute une requête SQL SELECT pour sélectionner les photos correspondantes
 * de la table 'photos' de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme :
 * {
 *     "id": ID_de_l_utilisateur
 * }
 *
 * Le fichier utilise PDO pour se connecter à la base de données MySQL.
 * Il renvoie les informations des photos sélectionnées sous forme de tableau JSON.
 *
 * @package AlbumPhotoMobil
 */

// Informations de connexion à la base de données
$host = "localhost";
$username = "lecteur";
$password = "P@ssw0rd";
$db_name = "albumphoto";

// Connexion à la base de données via PDO
$con = new PDO('mysql:host='.$host.';dbname='.$db_name, $username, $password);

// Récupération des données JSON reçues
$data = json_decode(file_get_contents("php://input"), true);

// Préparation de la requête SQL pour sélectionner les photos associées à l'utilisateur spécifié
$sql = $con->prepare("SELECT id_photo, lien_photo, legende, date_poster FROM photos WHERE id_utilisateur=:idUtil ORDER BY date_poster DESC");
$sql->bindValue(':idUtil', $data['id'], PDO::PARAM_INT);
$sql->execute(); // Exécution de la requête SQL pour récupérer les photos

// Récupération des résultats et construction du tableau de photos
$tab = array();
$i = 0;
while ($row = $sql->fetch(PDO::FETCH_OBJ)) {
    $tab[$i] = $row;
    $i++;
}

// Conversion du tableau en format JSON et envoi en réponse
echo json_encode($tab);
?>
