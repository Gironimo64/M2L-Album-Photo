<?php
/**
 * Ce fichier gère l'affichage des commentaires pour une photo spécifique et l'ajout de nouveaux commentaires.
 *
 * Ce script inclut la gestion des commentaires depuis la base de données et l'authentification de l'utilisateur.
 *
 * @package AlbumPhoto
 */

// Inclure les fichiers nécessaires
include_once "$racine/modele/bd.commentaire.php";
include_once "$racine/modele/authentification.php";

// Récupérer les paramètres de la requête
$id_photo = $_REQUEST["id_photo"];
$image = $_REQUEST['image'];

// Vérifier si l'utilisateur est connecté
if (isLoggedOn()) {    
    
    // Vérifier si un nouveau commentaire est envoyé
    if (isset($_REQUEST["envoyerCom"])) {
        $com = $_REQUEST["com"];
        
        // Récupérer l'ID de l'utilisateur actuel
        $id_utilisateur = getIdUtilisateur($_SESSION["pseudo"]);
        
        // Ajouter un nouveau commentaire
        $unCommentaire = addCommentaire($com, $id_photo, $id_utilisateur);
    }
}

// Récupérer la liste des commentaires pour la photo spécifiée
$listeCommentaire = getLesCommentaire($id_photo);

// Inclure les vues nécessaires
include "$racine/vue/entete.php";
include "$racine/vue/vueCommentaire.php";
include "$racine/vue/pied.php";
