<?php
/**
 * Ce fichier récupère les commentaires associés à une photo et à un utilisateur dans la base de données.
 *
 * Il reçoit des données JSON via une requête HTTP POST, extrait l'identifiant de la photo et de l'utilisateur
 * à partir de ces données, puis exécute une requête SQL SELECT pour sélectionner les commentaires correspondants
 * de la table 'commentaire' de la base de données.
 *
 * Les données JSON reçues doivent être sous la forme d'un tableau contenant des objets avec les informations sur la photo
 * et l'utilisateur :
 * [
 *     {
 *         "photo": {
 *             "id_photo": ID_de_la_photo
 *             // Autres informations sur la photo si nécessaire
 *         },
 *         "utilisateur": {
 *             "id": ID_de_l_utilisateur
 *             // Autres informations sur l'utilisateur si nécessaire
 *         }
 *     }
 * ]
 *
 * Le fichier utilise PDO pour se connecter à la base de données MySQL.
 * Il renvoie les commentaires sélectionnés sous forme de tableau JSON.
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

// Extraction de l'identifiant de la photo et de l'utilisateur à partir des données JSON
$id_photo = $data[0]['photo']['id_photo'];
$id_utilisateur = $data[0]['utilisateur']['id'];

// Préparation de la requête SQL pour sélectionner les commentaires associés à la photo et à l'utilisateur spécifiés
$sql = $con->prepare("SELECT id_com, commentaire, date_com FROM commentaire WHERE id_photo=:idPhoto AND id_utilisateur=:idUtil ORDER BY date_com DESC");
$sql->bindValue(':idPhoto', $id_photo, PDO::PARAM_INT);
$sql->bindValue(':idUtil', $id_utilisateur, PDO::PARAM_INT);
$sql->execute(); // Exécution de la requête SQL pour récupérer les commentaires

// Récupération des résultats et construction du tableau de commentaires
$tab = array();
$i = 0;
while ($row = $sql->fetch(PDO::FETCH_ASSOC)) {
    $tab[$i] = $row;
    $i++;
}

// Conversion du tableau en format JSON et envoi en réponse
echo json_encode($tab);
?>
