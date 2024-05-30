<?php
/**
 * Génère une mise en page pour afficher une liste de photos paginée.
 *
 * @param array $listePhotos La liste complète des photos à afficher.
 */
function afficherListePhotosPaginee($listePhotos) {
    // Nombre de photos par page
    $photosParPage = 4;

    // Récupérer le numéro de la page à afficher à partir de la requête GET
    $page_actuelle = isset($_GET['page']) ? intval($_GET['page']) : 1;

    // Calculer le nombre total de photos
    $totalPhotos = count($listePhotos);

    // Calculer le nombre total de pages nécessaires
    $nombrePages = ceil($totalPhotos / $photosParPage);

    // Déterminer l'index de départ à partir duquel récupérer les photos à afficher sur la page actuelle
    $indexDebut = ($page_actuelle - 1) * $photosParPage;

    // Extraire les photos à afficher sur la page actuelle
    $listePhotosPage = array_slice($listePhotos, $indexDebut, $photosParPage);
    
    ?>
     <script>
        // Transférer la valeur de la variable PHP à JavaScript
        var phpVariable = "<?php echo $totalPhotos; ?>";
        // Afficher la valeur dans la console du navigateur
        console.log(phpVariable);
    </script>

    <?php
    // Vérifier s'il y a des photos à afficher
    if ($listePhotos) {
        ?>
        <div class="page">
        <?php    
        // Parcourir les photos extraites et afficher chaque photo avec ses détails
        foreach ($listePhotosPage as $photo) {
            // Récupérer l'utilisateur associé à la photo
            $util_photo = getUtilisateur($photo['id_utilisateur']);
            $util_photo = $util_photo["pseudo"];
            ?>
            <div class="miseEnPage">
                
                <img src='img/<?php echo $photo['lien_photo'] ?>' alt='<?php echo $photo['lien_photo'] ?> ne peut pas être afficher'>
                <figcaption><h2><i><?php echo $util_photo ?>:</i> <?php echo $photo['legende'] ?></h2> <p align='right'>Publier le <?php echo $photo['date_poster'] ?></p></figcaption> </br> </br>
                <?php 
                $id_photo = $photo['id_photo'];
                // Afficher le formulaire de suppression de la photo si l'utilisateur est connecté et est le propriétaire de la photo
                if (isLoggedOn()) {
                    $id_utilisateur = getIdUtilisateur($_SESSION["pseudo"]);
                    if ($photo['id_utilisateur'] == $id_utilisateur) {
                        ?>
                        <form action='./?action=supprimer' method='POST'>
                            <input type='submit' value='Supprimé la photo' name='supprimer' />
                            <input type='hidden' value='<?php echo $id_photo ?>' name='id_photo' />
                        </form>
                    <?php 
                    }
                }
                ?>
                <form action='./?action=commentaire' method='POST'>
                    <input type='submit' value='Zone commentaire' name='zoneCom' />
                    <input type='hidden' value='<?php echo $id_photo ?>' name='id_photo' />
                    <input type='hidden' value="<?php echo $photo['lien_photo'] ?>" name='image' />
                </form>
            </div>
        <?php            
        }
        ?>
        <!-- Afficher les liens de pagination pour accéder aux différentes pages -->
        <div class="miseEnPage">
            <?php
            for ($i = 1; $i <= $nombrePages; $i++) {
                echo "<a href='?page=$i'>$i</a> ";
            }
            ?>
        </div>
    <?php    
    } 
    else {
        // Afficher un message si l'utilisateur n'a pas de photo
        ?>
        <h2>Vous n'avez pas de photo.</h2>
        </div>
    <?php 
    }
}
afficherListePhotosPaginee($listePhotos);

?>
