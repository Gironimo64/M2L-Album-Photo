<?php 
/**
 * Page de profil utilisateur affichant les informations de l'utilisateur,
 * y compris son pseudo, le nombre de photos qu'il a postées, ses photos
 * avec leurs légendes et dates de publication, ainsi que le nombre de commentaires
 * qu'il a postés et ses commentaires avec les pseudos des utilisateurs
 * auxquels ils sont associés et les dates de publication.
 */

?>
<//div class="miseEnPage">
<h1>Mon profil</h1>

Mon pseudo : <?php echo $util["pseudo"] ?> <br/> <br/>

Nombre de photo posté: <?php echo $nbrPhotos ?> <br/> <br/>

<h2>Vos Photos:</h2><br\>
<?php 
foreach ($listePhotos as $photo) {
    ?>
    <div class='miseEnPage'>
    <img src="img/<?php echo $photo['lien_photo'] ?>" alt='<?php echo $photo['lien_photo'] ?> ne peut pas être afficher  '>  
            <figcaption><h2><?php echo $photo['legende'] ?></h2> <p align='right'>Publier le <?php echo $photo['date_poster'] ?> </p> </figcaption> </br> </br>
</div>
<?php } ?>

Nombre de commentaire posté: <?php echo $nbrCommentaires ?> <br/> <br/>
<h2>Vos commentaire:</h2>
<?php
foreach ($listeCommentaire as $unCommentaire) {
    $photo=getPhotosByid_photo($unCommentaire["id_photo"]);
    $utilPhoto=getUtilisateur($photo["id_utilisateur"]);
    ?>
    
    Vous avez répondu à <?php echo "$utilPhoto[pseudo] le $unCommentaire[date_com]" ?></br> </br>
<?php } ?>

</div>
