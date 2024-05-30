<?php

/**
 * Bibliothèque de fonctions pour interagir avec la table "utilisateur" de la base de données.
 *
 * Ce fichier contient des fonctions pour effectuer différentes opérations sur les utilisateurs,
 * telles que récupérer l'identifiant d'un utilisateur, obtenir les informations d'un utilisateur,
 * vérifier l'existence d'un pseudo, et ajouter un nouvel utilisateur.
 */

include_once "bd.connexion.php";

/**
 * Fonction pour récupérer l'identifiant de l'utilisateur en fonction de son pseudo.
 *
 * @param string $pseudo Le pseudo de l'utilisateur.
 * @return int|null L'identifiant de l'utilisateur s'il est trouvé, sinon null.
 */
function getIdUtilisateur($pseudo) {
    try {
        $cnx = connexionPDOLecteur();
        $req = $cnx->prepare("SELECT id FROM utilisateur WHERE pseudo=:pseudo");
        $req->bindValue(':pseudo', $pseudo, PDO::PARAM_STR);
        $req->execute();
        $resultat = $req->fetchColumn();
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour récupérer les informations d'un utilisateur en fonction de son identifiant.
 *
 * @param int $id L'identifiant de l'utilisateur.
 * @return array|null Les informations de l'utilisateur s'il est trouvé, sinon null.
 */
function getUtilisateur($id){
    try {
        $cnx = connexionPDOLecteur();
        $req = $cnx->prepare("SELECT * FROM utilisateur WHERE id=:id_utilisateur");
        $req->bindValue(':id_utilisateur', $id, PDO::PARAM_INT);
        $req->execute();
        $resultat = $req->fetch(PDO::FETCH_ASSOC);
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour vérifier si un pseudo existe déjà dans la base de données.
 *
 * @param string $pseudo Le pseudo à vérifier.
 * @return int Le nombre de pseudos correspondants dans la base de données.
 */
function testPseudo($pseudo){
    try {
        $cnx = connexionPDOLecteur();
        $req = $cnx->prepare("SELECT COUNT(*) FROM utilisateur WHERE pseudo=:pseudo");
        $req->bindValue(':pseudo', $pseudo, PDO::PARAM_STR);
        $req->execute();
        $resultat = $req->fetchColumn();
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour ajouter un nouvel utilisateur dans la base de données.
 *
 * @param string $pseudo Le pseudo du nouvel utilisateur.
 * @param string $mdp Le mot de passe du nouvel utilisateur.
 * @return bool Retourne true si l'ajout de l'utilisateur a réussi, sinon false.
 */
function addUtilisateur($pseudo , $mdp) {
    try {
        $cnx = connexionPDOModificateur();
        $req = $cnx->prepare("INSERT INTO utilisateur (pseudo, mdp) VALUES (:pseudo, :mdp)");
        $req->bindValue(':pseudo', $pseudo, PDO::PARAM_STR);
        $req->bindValue(':mdp', password_hash($mdp, PASSWORD_BCRYPT, ["cost" => 10]), PDO::PARAM_STR);
        $resultat = $req->execute();
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

?>
