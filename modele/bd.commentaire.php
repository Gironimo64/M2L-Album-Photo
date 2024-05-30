<?php
/**
 * Ce fichier contient des fonctions pour interagir avec la table 'commentaire' de la base de données.
 * Il inclut également le fichier 'bd.connexion.php' qui contient des fonctions pour établir la connexion à la base de données.
 *
 * Les fonctions disponibles sont :
 *
 * - addCommentaire($com, $id_photo, $id_utilisateur) : Cette fonction permet d'ajouter un nouveau commentaire à la base de données.
 *                                                     Elle prend en paramètres le commentaire, l'identifiant de la photo et l'identifiant de l'utilisateur associés au commentaire.
 *
 * - getNbrCommentaireUtilisateur($id_utilisateur) : Cette fonction récupère le nombre de commentaires associés à un utilisateur spécifié dans la base de données.
 *
 * - getLesCommentaire($id_photo) : Cette fonction récupère tous les commentaires associés à une photo spécifiée dans la base de données.
 *
 * - getLesCommentairePersonne($id_utilisateur) : Cette fonction récupère tous les commentaires associés à un utilisateur spécifié dans la base de données.
 *
 * - getCommentaire($id_com) : Cette fonction récupère les informations d'un commentaire spécifié par son identifiant dans la base de données.
 *
 * - deleteCommentaire($id_commentaire) : Cette fonction permet de supprimer un commentaire de la base de données en utilisant son identifiant.
 *
 * Le fichier utilise les fonctions définies dans 'bd.connexion.php' pour établir la connexion à la base de données.
 *
 * @package AlbumPhoto
 */

// Inclusion du fichier contenant les fonctions pour établir la connexion à la base de données
include_once "bd.connexion.php";

// Fonction pour ajouter un nouveau commentaire à la base de données
function addCommentaire($com, $id_photo, $id_utilisateur) {
    try {
        // Établissement de la connexion à la base de données
        $cnx = connexionPDOModificateur();

        // Préparation de la requête SQL pour ajouter un nouveau commentaire
        $req = $cnx->prepare("INSERT INTO commentaire (commentaire, id_photo, id_utilisateur) VALUES (:com, :id_photo, :id_utilisateur)");
        $req->bindValue(':com', $com, PDO::PARAM_STR);
        $req->bindValue(':id_photo', $id_photo, PDO::PARAM_INT);
        $req->bindValue(':id_utilisateur', $id_utilisateur, PDO::PARAM_INT);

        // Exécution de la requête SQL
        $resultat = $req->execute();
    } catch (PDOException $e) {
        // Gestion des erreurs
        print "Erreur !: " . $e->getMessage();
        die();
    }
    return $resultat;
}

// Fonction pour récupérer le nombre de commentaires associés à un utilisateur dans la base de données
function getNbrCommentaireUtilisateur($id_utilisateur) {
    try {
        // Établissement de la connexion à la base de données
        $cnx = connexionPDOLecteur();

        // Préparation de la requête SQL pour compter le nombre de commentaires associés à un utilisateur
        $req = $cnx->prepare("SELECT COUNT(*) FROM commentaire WHERE id_utilisateur = :id_utilisateur");
        $req->bindValue(':id_utilisateur', $id_utilisateur, PDO::PARAM_INT);

        // Exécution de la requête SQL
        $req->execute();

        // Récupération du résultat
        $resultat = $req->fetchColumn();
    } catch (PDOException $e) {
        // Gestion des erreurs
        print "Erreur !: " . $e->getMessage();
        die();
    }
    return $resultat;
}

// Fonction pour récupérer tous les commentaires associés à une photo dans la base de données
function getLesCommentaire($id_photo) {
    $resultat = array();

    try {
        // Établissement de la connexion à la base de données
        $cnx = connexionPDOLecteur();

        // Préparation de la requête SQL pour récupérer les commentaires associés à une photo
        $req = $cnx->prepare("SELECT * FROM commentaire WHERE id_photo = :id_photo");
        $req->bindValue(':id_photo', $id_photo, PDO::PARAM_INT);

        // Exécution de la requête SQL
        $req->execute();

        // Récupération des résultats
        while ($ligne = $req->fetch(PDO::FETCH_ASSOC)) {
            $resultat[] = $ligne;
        }
    } catch (PDOException $e) {
        // Gestion des erreurs
        print "Erreur !: " . $e->getMessage();
        die();
    }
    return $resultat;
}

// Fonction pour récupérer tous les commentaires associés à un utilisateur dans la base de données
function getLesCommentairePersonne($id_utilisateur) {
    $resultat = array();

    try {
        // Établissement de la connexion à la base de données
        $cnx = connexionPDOLecteur();

        // Préparation de la requête SQL pour récupérer les commentaires associés à un utilisateur
        $req = $cnx->prepare("SELECT * FROM commentaire WHERE id_utilisateur = :id_utilisateur");
        $req->bindValue(':id_utilisateur', $id_utilisateur, PDO::PARAM_INT);

        // Exécution de la requête SQL
        $req->execute();

        // Récupération des résultats
        while ($ligne = $req->fetch(PDO::FETCH_ASSOC)) {
            $resultat[] = $ligne;
        }
    } catch (PDOException $e) {
        // Gestion des erreurs
        print "Erreur !: " . $e->getMessage();
        die();
    }
    return $resultat;
}

// Fonction pour récupérer les informations d'un commentaire spécifié par son identifiant dans la base de données
function getCommentaire($id_com) {
    $resultat = array();

    try {
        // Établissement de la connexion à la base de données
        $cnx = connexionPDOLecteur();

        // Préparation de la requête SQL pour récupérer les informations d'un commentaire spécifié par son identifiant
        $req = $cnx->prepare("SELECT * FROM commentaire WHERE id_com = :id_com");
        $req->bindValue(':id_com', $id_com, PDO::PARAM_INT);

        // Exécution de la requête SQL
        $req->execute();

        // Récupération du résultat
        $resultat = $req->fetch(PDO::FETCH_ASSOC);
    } catch (PDOException $e) {
        // Gestion des erreurs
        print "Erreur !: " . $e->getMessage();
        die();
    }
    return $resultat;
}

// Fonction pour supprimer un commentaire de la base de données en utilisant son identifiant
function deleteCommentaire($id_commentaire) {
    // Établissement de la connexion à la base de données
    $cnx = connexionPDOLecteur();

    try {
        // Préparation de la requête SQL pour supprimer un commentaire
        $req = $cnx->prepare("DELETE FROM commentaire WHERE id_com = :id_commentaire");
        $req->bindValue(':id_commentaire', $id_commentaire, PDO::PARAM_INT);

        // Exécution de la requête SQL
        $req->execute();
    } catch (PDOException $e) {
        // Gestion des erreurs
        print "Erreur !: " . $e->getMessage();
        die();
    }
}
?>
