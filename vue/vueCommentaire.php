<div class="miseEnPage">
<img src='img/<?php echo $image ?>' alt='<?php echo $image ?> ne peut pas être afficher  '>

    <h2>Commentaire </h2>
    
    <?php
    if ($listeCommentaire){
        foreach ($listeCommentaire as $commentaire){
            $util_commentaire=getUtilisateur($commentaire['id_utilisateur']);
            $util_commentaire=$util_commentaire['pseudo'];
            echo "<div class='miseEnPage'><h3><i> $util_commentaire </i></h3> $commentaire[commentaire] </br>
            <p align='right'>$commentaire[date_com] </p></div>";
            if (isLoggedOn()){
                $id_utilisateur=getIdUtilisateur($_SESSION["pseudo"]);
                if ($commentaire['id_utilisateur']==$id_utilisateur){
                    ?>
                    <form action='./?action=supprimer' method='POST'>
                    <input type='submit' value='Supprimé le  Commentaire' name='supprimer' />
                    <input type='hidden' value='<?php echo $commentaire['id_com'] ?>' name='id_com' />
                    <input type='hidden' value="<?php echo $image ?>" name='image' />
                    </form>
                <?php } 
            }
        }
    }
    
    else {
        echo "Pas de commentaire pous cette photo";
    }
    ?>
</div>

<?php 
if (isLoggedOn()){
?>
<div class="miseEnPage">
    <h2>Laissé un commentaire </h2>

    <form action='./?action=commentaire' method='POST'>
    <input type='text' name='com' placeholder='Votre commentaire' /><br />
    <input type='submit' value='Envoyer' name='envoyerCom' />
    <input type='hidden' value='<?php echo $id_photo ?>' name='id_photo' />
    <input type='hidden' value="<?php echo $image ?>" name='image' />
    </form>
<?php  } ?>
</div>