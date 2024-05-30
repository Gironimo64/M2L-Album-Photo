<?php
/**
 * Ce fichier contient des fonctions pour gérer l'authentification des utilisateurs.
 * Il inclut également le fichier 'bd.utilisateur.php' qui contient des fonctions pour interagir avec la table 'utilisateur' de la base de données.
 *
 * Les fonctions disponibles sont :
 *
 * - login($pseudo, $mdp) : Cette fonction permet à un utilisateur de se connecter en vérifiant si les identifiants fournis correspondent à ceux enregistrés dans la base de données.
 *                          Si la connexion réussit, les informations de l'utilisateur sont stockées dans la session.
 *
 * - logout() : Cette fonction permet de déconnecter un utilisateur en supprimant ses informations de la session.
 *
 * - isLoggedOn() : Cette fonction vérifie si un utilisateur est connecté en vérifiant la présence de ses informations dans la session.
 *                  Elle renvoie true si l'utilisateur est connecté, sinon false.
 *
 * Le fichier utilise les fonctions définies dans 'bd.utilisateur.php' pour interagir avec la base de données.
 *
 * @package AlbumPhoto
 */

// Inclusion du fichier contenant les fonctions pour interagir avec la table 'utilisateur'
include_once "bd.utilisateur.php";

// Fonction pour connecter un utilisateur
function login($pseudo, $mdp) {
    // Démarrage de la session si elle n'est pas déjà démarrée
    if (!isset($_SESSION)) {
        session_start();
    }

    // Récupération de l'ID de l'utilisateur et de ses informations depuis la base de données
    $idUtilisateur = getIdUtilisateur($pseudo);
    $util = getUtilisateur($idUtilisateur);

    // Vérification si l'utilisateur existe dans la base de données et si le mot de passe est correct
    if ($util != false) {
        $pseudoBD = $util["pseudo"];
        $mdpBD = $util["mdp"];

        if (password_verify($mdp, $mdpBD)) {
            // Stockage des informations de l'utilisateur dans la session
            $_SESSION["pseudo"] = $pseudoBD;
            $_SESSION["mdp"] = $mdpBD;
        } 
    }
}

// Fonction pour déconnecter un utilisateur
function logout() {
    // Démarrage de la session si elle n'est pas déjà démarrée
    if (!isset($_SESSION)) {
        session_start();
    }

    // Suppression des informations de l'utilisateur de la session
    unset($_SESSION["pseudo"]);
    unset($_SESSION["mdp"]);
}

// Fonction pour vérifier si un utilisateur est connecté
function isLoggedOn() {
    // Démarrage de la session si elle n'est pas déjà démarrée
    if (!isset($_SESSION)) {
        session_start();
    }

    $ret = false;

    // Vérification si les informations de l'utilisateur sont présentes dans la session
    if (isset($_SESSION["pseudo"]) && isset($_SESSION["mdp"])) {
        $id_util = getIdUtilisateur($_SESSION["pseudo"]);
        $util = getUtilisateur($id_util);
        if ($util["pseudo"] == $_SESSION["pseudo"] && $util["mdp"] == $_SESSION["mdp"]) {
            // L'utilisateur est connecté
            $ret = true;
        }
    }
    return $ret;
}
?>
