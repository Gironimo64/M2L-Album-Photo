<?php
/**
 * Ce fichier gère le processus de déconnexion de l'utilisateur.
 *
 * Il inclut la fonction de déconnexion depuis le fichier d'authentification, puis affiche à nouveau le formulaire
 * d'authentification.
 *
 * @package AlbumPhoto
 */

// Inclure le fichier d'authentification
include_once "$racine/modele/authentification.php";

// Déconnecter l'utilisateur
logout();

// Définir le titre de la page
$titre = "authentification";

// Inclure les vues nécessaires pour afficher le formulaire d'authentification
include "$racine/vue/entete.php";
include "$racine/vue/vueAuthentification.php";
include "$racine/vue/pied.php";
?>
