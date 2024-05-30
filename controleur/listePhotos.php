<?php
/**
 * Ce fichier gère l'affichage des photos et de leurs commentaires associés.
 *
 * Il inclut les fonctionnalités de la base de données pour récupérer les informations sur les photos,
 * y compris les commentaires associés.
 *
 * Les photos sont récupérées à partir de la base de données et stockées dans la variable $listePhotos.
 * Ensuite, le fichier inclut les vues nécessaires pour afficher les photos et leurs commentaires.
 *
 * @package AlbumPhoto
 */

// Inclure les fichiers de modèle nécessaires
include_once "$racine/modele/bd.connexion.php";
include_once "$racine/modele/bd.photo.php";
include_once "$racine/modele/bd.utilisateur.php";
include_once "$racine/modele/bd.commentaire.php";

// Récupérer la liste des photos
$listePhotos = getPhotos();

// Inclure les vues pour afficher les photos et leurs commentaires
include "$racine/vue/entete.php";
include "$racine/vue/vuePhotos.php";
include "$racine/vue/pied.php";
?>
