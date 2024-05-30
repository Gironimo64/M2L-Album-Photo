<?php
/**
 * Ce fichier gère le processus d'envoi de photos.
 *
 * Il inclut les fonctionnalités de la base de données pour la gestion des photos et de l'authentification
 * pour vérifier si l'utilisateur est connecté.
 *
 * Si l'utilisateur est connecté et soumet un formulaire avec une légende de photo, il active le processus
 * d'envoi de photo en définissant la variable $activUpload à true.
 *
 * Si le processus d'envoi est activé, il inclut la vue pour gérer l'envoi de la photo.
 * Sinon, il inclut la vue pour afficher le formulaire d'envoi de photo.
 *
 * @package AlbumPhoto
 */

// Inclure les fichiers de modèle nécessaires
include_once "$racine/modele/bd.connexion.php";
include_once "$racine/modele/bd.photo.php";
include_once "$racine/modele/bd.utilisateur.php";
include_once "$racine/modele/authentification.php";

// Initialiser la variable pour activer/désactiver le processus d'envoi de photo
$activUpload = false;

// Vérifier si le formulaire a été soumis et si une légende de photo est fournie
if (isset($_POST["submit"]) && isset($_POST["legende"])) {
    
    // Vérifier si l'utilisateur est connecté
    if (isLoggedOn()) {
        // Si l'utilisateur est connecté, récupérer la légende de la photo et l'ID de l'utilisateur
        $legende = $_POST["legende"];
        $id_utilisateur = getIdUtilisateur($_SESSION["pseudo"]);
        
        // Activer le processus d'envoi de photo
        $activUpload = true;
    }  
}

// Vérifier si le processus d'envoi de photo est activé
if ($activUpload) {
    // Si le processus d'envoi est activé, inclure la vue pour gérer l'envoi de la photo
    include "$racine/vue/entete.php";
    include "$racine/vue/vueUpload.php";
    include "$racine/vue/pied.php";
} else {
    // Sinon, inclure la vue pour afficher le formulaire d'envoi de photo
    include "$racine/vue/entete.php";
    include "$racine/vue/vueform.php";
    include "$racine/vue/pied.php";
}
?>
