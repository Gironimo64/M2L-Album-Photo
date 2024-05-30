<?php
/**
 * Ce fichier contient des fonctions pour interagir avec la table des photos dans la base de données.
 *
 * Les fonctions disponibles sont :
 *
 * - getNbrPhotoUtilisateur($id_utilisateur) : Cette fonction retourne le nombre de photos associées à un utilisateur spécifié.
 * - getPhotos() : Cette fonction récupère toutes les photos de la base de données.
 * - getPhotosByid_utilisateur($id_utilisateur) : Cette fonction récupère les photos associées à un utilisateur spécifié.
 * - addPhoto($lien_photo, $legende, $id_utilisateur) : Cette fonction ajoute une nouvelle photo à la base de données avec le lien de la photo, la légende et l'identifiant de l'utilisateur.
 * - deletePhoto($id_photo) : Cette fonction supprime une photo de la base de données et tous les commentaires associés à cette photo.
 *
 * Chaque fonction gère les erreurs PDO et arrête l'exécution du script en cas d'erreur.
 *
 * @package AlbumPhoto
 */

include_once "bd.connexion.php";

/**
 * Fonction pour récupérer le nombre de photos associées à un utilisateur spécifié.
 *
 * @param int $id_utilisateur L'identifiant de l'utilisateur.
 * @return int Le nombre de photos associées à l'utilisateur spécifié.
 */
function getNbrPhotoUtilisateur($id_utilisateur){
    try {
        $cnx = connexionPDOLecteur();
        $req = $cnx->prepare("SELECT COUNT(*) FROM photos WHERE id_utilisateur = :id_utilisateur");
        $req->bindValue(':id_utilisateur', $id_utilisateur, PDO::PARAM_INT);
        $req->execute();
        $resultat = $req->fetchColumn();
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour récupérer toutes les photos de la base de données.
 *
 * @return array Un tableau contenant toutes les photos de la base de données.
 */
function getPhotos() {
    try {
        $cnx = connexionPDOLecteur();
        $req = $cnx->prepare("SELECT * FROM photos");
        $req->execute();
        $resultat = $req->fetchAll(PDO::FETCH_ASSOC);
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour récupérer toutes les photos de la base de données.
 *
 * @param int $id_photo L'identifiant de la photo.
 * @return array Un tableau contenant une photo de la base de données.
 */
function getPhotosByid_photo($id_photo) {
    try {
        $cnx = connexionPDOLecteur();
        $req = $cnx->prepare("SELECT * FROM photos WHERE id_photo = :id_photo");
        $req->bindValue(':id_photo', $id_photo, PDO::PARAM_INT);
        $req->execute();
        $resultat = $req->fetch(PDO::FETCH_ASSOC);
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour récupérer les photos associées à un utilisateur spécifié.
 *
 * @param int $id_utilisateur L'identifiant de l'utilisateur.
 * @return array Un tableau contenant les photos associées à l'utilisateur spécifié.
 */
function getPhotosByid_utilisateur($id_utilisateur) {
    try {
        $cnx = connexionPDOLecteur();
        $req = $cnx->prepare("SELECT * FROM photos WHERE id_utilisateur = :id_utilisateur");
        $req->bindValue(':id_utilisateur', $id_utilisateur, PDO::PARAM_INT);
        $req->execute();
        $resultat = $req->fetchAll(PDO::FETCH_ASSOC);
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour ajouter une nouvelle photo à la base de données.
 *
 * @param string $lien_photo Le lien de la photo.
 * @param string $legende La légende de la photo.
 * @param int $id_utilisateur L'identifiant de l'utilisateur propriétaire de la photo.
 * @return bool Retourne true si l'ajout de la photo a réussi, sinon false.
 */
function addPhoto($lien_photo, $legende, $id_utilisateur) {
    try {
        $cnx = connexionPDOModificateur();
        $req = $cnx->prepare("INSERT INTO photos (lien_photo, legende, id_utilisateur) VALUES (:lien_photo, :legende, :id_utilisateur)");
        $req->bindValue(':lien_photo', $lien_photo, PDO::PARAM_STR);
        $req->bindValue(':legende', $legende, PDO::PARAM_STR);
        $req->bindValue(':id_utilisateur', $id_utilisateur, PDO::PARAM_INT);
        $resultat = $req->execute();
        return $resultat;
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}

/**
 * Fonction pour supprimer une photo de la base de données et tous les commentaires associés.
 *
 * @param int $id_photo L'identifiant de la photo à supprimer.
 * @return void
 */
function deletePhoto($id_photo) {
    try {
        $cnx = connexionPDOModificateur();
        $req = $cnx->prepare("DELETE FROM commentaire WHERE id_photo = :id_photo");
        $req->bindValue(':id_photo', $id_photo, PDO::PARAM_INT);
        $req->execute();
        
        $req2 = $cnx->prepare("DELETE FROM photos WHERE id_photo = :id_photo");
        $req2->bindValue(':id_photo', $id_photo, PDO::PARAM_INT);
        $req2->execute();
    } catch (PDOException $e) {
        print "Erreur !: " . $e->getMessage();
        die();
    }
}
?>
