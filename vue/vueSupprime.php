
<h1>Voulez vous supprimé l'élément</h1>

<form action='./?action=supprimer' method='POST'>
            <input type='submit' value='oui' name='oui' />
            <input type='hidden' value='<?php echo $id_photo ?>' name='id_photo' />
            <input type='hidden' value='<?php echo $id_com ?>' name='id_com' />
            <input type='hidden' value="<?php echo $type_submit ?>" name='supprimer' />
            <input type='hidden' value="<?php echo $image ?>" name='image' />

</form>
<form action='./?action=accueil' method='POST'>
            <input type='submit' value='non' name='non' />
</form>