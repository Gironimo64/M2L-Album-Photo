<?php include_once "$racine/modele/authentification.php"; ?>
<html lang="fr">
  
  <head>
    <title>Galeries Photos</title>
    <link rel="stylesheet" type="text/css" href="vue/styles.css">
  </head>
  <body>
    <nav>
    <ul>
        <li><a href="./?action=accueil">Album</a></li>
        <?php  if (isLoggedOn()){ ?>
          <li><a href="./?action=upload">Téléverser photo</a></li>
          <li><a href="./?action=profil">Profil</a></li>
          <li><a href="./?action=deconnexion">Se déconnecter</a></li>
        <?php }
        else { ?>  
          <li><a href="./?action=inscription">S'inscrire</a></li>        
          <li><a href="./?action=connexion">Se connecter</a></li>
        <?php } ?>  
    </ul>
    </nav>
    <div class='espace'>
</body>
      
