<?php
/**
 * Ce fichier contient la fonction principale de contrôleur qui détermine l'action à exécuter.
 *
 * La fonction `controleurPrincipal` prend en paramètre une action et renvoie le fichier correspondant à cette action.
 * Si l'action spécifiée n'est pas définie, il renvoie le fichier "notFound.php".
 *
 * @param string $action L'action à exécuter.
 * @return string Le fichier correspondant à l'action spécifiée.
 */
function controleurPrincipal($action){
    // Tableau associatif contenant les actions et les fichiers correspondants
    $lesActions = array();
    $lesActions["defaut"] = "listePhotos.php";
    $lesActions["notFound"] = "notFound.php";
    $lesActions["accueil"] = "listePhotos.php";
    $lesActions["upload"] = "upload.php";
    $lesActions["commentaire"] = "commentaire.php";
    $lesActions["inscription"] = "inscription.php";
    $lesActions["connexion"] = "connexion.php";
    $lesActions["deconnexion"] = "deconnexion.php";
    $lesActions["profil"] = "profil.php";
    $lesActions["supprimer"] = "supprimer.php";

    // Vérifier si l'action spécifiée est définie dans le tableau des actions
    if (array_key_exists($action, $lesActions)) {
        // Renvoyer le fichier correspondant à l'action spécifiée
        return $lesActions[$action];
    } else {
        // Si l'action n'est pas définie, renvoyer le fichier "notFound.php"
        return $lesActions["notFound"];
    }
}
?>
