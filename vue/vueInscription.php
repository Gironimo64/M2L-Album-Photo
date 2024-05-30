<h1>Inscription</h1>
<span id="alerte"><?= $msg ?></span>
<form action="./?action=inscription" method="POST">

    <input type="text" name="pseudo" placeholder="Pseudo" /><br />
    <input type="password" name="mdp" placeholder="Mot de passe"  /><br />
    

    <input type="submit" value="S'inscrire" />

</form>
