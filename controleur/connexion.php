<?php
/**
 * Ce fichier gère le processus d'authentification des utilisateurs.
 *
 * Si les informations de connexion sont fournies via POST, il tente de connecter l'utilisateur en utilisant les
 * informations fournies. Sinon, il affiche le formulaire d'authentification.
 *
 * Si l'utilisateur est connecté avec succès, il inclut le contrôleur de profil pour afficher le profil de l'utilisateur.
 * Sinon, il affiche à nouveau le formulaire d'authentification.
 *
 * @package AlbumPhoto
 */

// Inclure le fichier d'authentification
include_once "$racine/modele/authentification.php";

// Vérifier si les informations de connexion sont fournies via POST
if (!isset($_POST["pseudo"]) || !isset($_POST["mdp"])) {
    
    // Afficher le formulaire d'authentification
    $titre = "authentification";
    include "$racine/vue/entete.php";
    include "$racine/vue/vueAuthentification.php";
    include "$racine/vue/pied.php";
} else {
    
    // Récupérer les informations de connexion
    $pseudo = $_POST["pseudo"];
    $mdp = $_POST["mdp"];
    
    // Tenter de connecter l'utilisateur
    // Le login de M2L est 'M2L' et les autres utilisateurs ont pour mot de passe '123'
    login($pseudo, $mdp);

    // Vérifier si l'utilisateur est connecté avec succès
    if (isLoggedOn()) { 
        // Inclure le contrôleur de profil pour afficher le profil de l'utilisateur
        include "$racine/controleur/profil.php";
    } else {
        // Afficher à nouveau le formulaire d'authentification en cas d'échec de connexion
        $titre = "authentification";
        include "$racine/vue/entete.php";
        include "$racine/vue/vueAuthentification.php";
        include "$racine/vue/pied.php";
    }
}

?>
