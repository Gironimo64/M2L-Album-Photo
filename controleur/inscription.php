<?php
/**
 * Ce fichier gère le processus d'inscription des utilisateurs.
 *
 * Il inclut les fonctionnalités de la base de données utilisateur pour vérifier si un pseudo est déjà utilisé
 * et ajouter un nouvel utilisateur si le pseudo n'existe pas déjà.
 *
 * Si des données de pseudo et de mot de passe sont fournies via POST, il tente d'ajouter un nouvel utilisateur.
 * Si l'ajout est réussi, il affiche une confirmation d'inscription. Sinon, il affiche un message d'erreur.
 *
 * @package AlbumPhoto
 */

// Inclure le fichier de base de données utilisateur
include_once "$racine/modele/bd.utilisateur.php";

// Initialisation des variables
$inscrit = false; // Indique si l'inscription a réussi
$msg = ""; // Message d'erreur ou de succès
$ret = false; // Résultat de l'ajout d'utilisateur

// Vérifier si les données de pseudo et de mot de passe sont fournies via POST
if (isset($_POST["pseudo"]) && isset($_POST["mdp"])) {
    
    // Récupérer le pseudo et le mot de passe fournis
    $pseudo = $_POST["pseudo"];
    $mdp = $_POST["mdp"];

    // Vérifier si le pseudo est disponible
    if (testPseudo($pseudo) == 0) {
        // Ajouter l'utilisateur s'il n'existe pas déjà
        $ret = addUtilisateur($pseudo, $mdp);
    }

    // Vérifier si l'ajout de l'utilisateur a réussi
    if ($ret) {
        // L'inscription est réussie
        $inscrit = true;
    } else {
        // L'inscription a échoué en raison d'un pseudo déjà utilisé
        $msg = "Un utilisateur porte déjà ce pseudo. Veuillez en choisir un autre.";
    }
} else {
    // Les données de pseudo et de mot de passe ne sont pas fournies via POST
    $msg = "Veuillez renseigner tous les champs.";
}

// Afficher la confirmation d'inscription ou le formulaire d'inscription en fonction du statut d'inscription
if ($inscrit) {
    // Afficher la confirmation d'inscription
    $titre = "Inscription confirmée";
    include "$racine/vue/entete.php";
    include "$racine/vue/vueConfirmationInscription.php";
    include "$racine/vue/pied.php";
} else {
    // Afficher le formulaire d'inscription avec un message d'erreur
    $titre = "Problème d'inscription";
    include "$racine/vue/entete.php";
    include "$racine/vue/vueInscription.php";
    include "$racine/vue/pied.php";
}
?>
