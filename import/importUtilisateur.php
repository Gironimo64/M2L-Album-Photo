<?php
/**
 * Ce fichier récupère toutes les entrées de la table 'utilisateur' dans la base de données.
 *
 * Il exécute une requête SQL SELECT pour sélectionner toutes les lignes de la table 'utilisateur'
 * de la base de données, puis renvoie les résultats au format JSON.
 *
 * Le fichier utilise PDO pour se connecter à la base de données MySQL.
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

// Exécution de la requête SQL pour récupérer toutes les entrées de la table 'utilisateur'
$sql = "SELECT * FROM utilisateur";
$result = $con->query($sql);

// Construction du tableau contenant les résultats
$tab = array();
$i = 0;
while ($row = $result->fetch(PDO::FETCH_OBJ)) {
    $tab[$i] = $row;
    $i++;
}

// Conversion du tableau en format JSON et envoi en réponse
echo json_encode($tab);
?>
