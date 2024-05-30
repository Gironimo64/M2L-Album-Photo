<?php
/**
 * Ce fichier gère la suppression de photos et de commentaires.
 *
 * Il inclut les fonctionnalités de la base de données pour la gestion des photos et des commentaires.
 *
 * Selon le type de soumission (suppression de photo ou de commentaire), il récupère les données appropriées
 * depuis la requête et exécute la suppression correspondante. Ensuite, il inclut les vues nécessaires
 * pour afficher les résultats.
 *
 * Si l'utilisateur confirme la suppression, il exécute la suppression et affiche à nouveau les photos ou les commentaires.
 * Sinon, il affiche le formulaire de confirmation de suppression.
 *
 * @package AlbumPhoto
 */

// Inclure les fichiers de modèle nécessaires
include_once "$racine/modele/bd.connexion.php";
include_once "$racine/modele/bd.photo.php";
include_once "$racine/modele/bd.utilisateur.php";
include_once "$racine/modele/bd.commentaire.php";

// Récupérer le type de soumission (suppression de photo ou de commentaire)
$type_submit = $_REQUEST['supprimer'];

// Initialiser les variables pour les données à supprimer
$id_photo = null;
$id_com = null;
$image = null;

// Vérifier le type de soumission
if ($type_submit == 'Supprimé la photo') {
    // Si la soumission concerne la suppression de photo, récupérer l'ID de la photo à supprimer
    $id_photo = $_REQUEST['id_photo'];
} elseif ($type_submit == 'Supprimé le  Commentaire') {
    // Si la soumission concerne la suppression de commentaire, récupérer l'ID du commentaire et l'ID de la photo associée
    $id_com = $_REQUEST['id_com'];
    $image = $_REQUEST['image'];
}

// Vérifier si l'utilisateur a confirmé la suppression
if (isset($_REQUEST['oui'])) {
    if ($type_submit == 'Supprimé la photo') {
        // Si la soumission concerne la suppression de photo et que l'utilisateur confirme,
        // supprimer la photo et inclure à nouveau la vue des photos
        deletePhoto($id_photo);
        $listePhotos = getPhotos();
        include_once "$racine/vue/entete.php";
        include_once "$racine/vue/vuePhotos.php";
        include_once "$racine/vue/pied.php";
    } else {
        // Si la soumission concerne la suppression de commentaire et que l'utilisateur confirme,
        // supprimer le commentaire et inclure à nouveau la vue des commentaires
        $com = getCommentaire($id_com);
        $id_photo = $com['id_photo'];
        deleteCommentaire($id_com);
        $listeCommentaire = getLesCommentaire($id_photo);
        include_once "$racine/vue/entete.php";
        include_once "$racine/vue/vueCommentaire.php";
        include_once "$racine/vue/pied.php";
    }
} else {
    // Si l'utilisateur n'a pas encore confirmé la suppression, afficher le formulaire de confirmation
    include_once "$racine/vue/entete.php";
    include_once "$racine/vue/vueSupprime.php";
    include_once "$racine/vue/pied.php";
}
?>
