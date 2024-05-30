<?php
/**
 * Ce fichier gère l'affichage du profil de l'utilisateur connecté.
 *
 * Il inclut les fonctionnalités de l'authentification et de la base de données pour récupérer les informations
 * sur l'utilisateur connecté, y compris ses photos et ses commentaires associés.
 *
 * Si l'utilisateur est connecté, il récupère les informations sur l'utilisateur, ses photos, ses commentaires,
 * ainsi que le nombre de photos et de commentaires qu'il a publiés. Ensuite, il inclut la vue pour afficher
 * les informations du profil.
 *
 * Si l'utilisateur n'est pas connecté, il affiche uniquement l'entête et le pied de page du profil.
 *
 * @package AlbumPhoto
 */

// Inclure les fichiers de modèle nécessaires
include_once "$racine/modele/authentification.php";
include_once "$racine/modele/bd.utilisateur.php";
include_once "$racine/modele/bd.photo.php";
include_once "$racine/modele/bd.commentaire.php";

// Vérifier si l'utilisateur est connecté
if (isLoggedOn()){
    // Récupérer l'ID de l'utilisateur
    $id_utilisateur = getIdUtilisateur($_SESSION["pseudo"]);
    
    // Récupérer les informations sur l'utilisateur, ses photos, ses commentaires et le nombre de photos/commentaires
    $listePhotos = getPhotosByid_utilisateur($id_utilisateur);
    $listeCommentaire = getLesCommentairePersonne($id_utilisateur);
    $util = getUtilisateur($id_utilisateur);
    $nbrPhotos = getNbrPhotoUtilisateur($id_utilisateur);
    $nbrCommentaires = getNbrCommentaireUtilisateur($id_utilisateur);

    // Définir le titre de la page
    $titre = "Mon profil";
    
    // Inclure les vues pour afficher le profil
    include "$racine/vue/entete.php";
    include "$racine/vue/vueProfil.php";
    include "$racine/vue/pied.php";
} else {
    // Si l'utilisateur n'est pas connecté, afficher uniquement l'entête et le pied de page du profil
    $titre = "Mon profil";
    include "$racine/vue/entete.html.php";
    include "$racine/vue/pied.html.php";
}
?>
