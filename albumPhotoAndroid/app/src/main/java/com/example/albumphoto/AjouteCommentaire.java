package com.example.albumphoto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.albumphoto.entities.InterrogationBD;
import com.example.albumphoto.entities.commentaire.Commentaire;
import com.example.albumphoto.entities.photo.Photos;
import com.example.albumphoto.entities.utilisateur.GestionnaireSessionUtilisateur;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Activité permettant à l'utilisateur d'ajouter un commentaire à une photo.
 */
public class AjouteCommentaire extends AppCompatActivity {
    // Executor pour exécuter des tâches en arrière-plan
    private Executor executor = Executors.newSingleThreadExecutor();
    // Handler pour effectuer des mises à jour sur le thread principal
    private Handler handler = new Handler(Looper.getMainLooper());
    // Gestionnaire de session utilisateur
    private GestionnaireSessionUtilisateur gestionnaireSessionUtilisateur;
    private Commentaire nouveauCommentaire;
    private Photos photoCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ajoute_commentaire);

        // Initialisation du gestionnaire de session utilisateur
        gestionnaireSessionUtilisateur = new GestionnaireSessionUtilisateur(this);

        // Récupération de la photo concernée par le commentaire
        photoCom = getIntent().getParcelableExtra("Photo");

        // Définition de l'observateur pour le bouton de publication de commentaire
        Button btnPublierCom = findViewById(R.id.btnPublierCom);
        btnPublierCom.setOnClickListener(observateurClickBouton);
    }

    /**
     * Observateur de clic pour le bouton de publication de commentaire.
     */
    private View.OnClickListener observateurClickBouton = new View.OnClickListener() {
        public void onClick(View v) {
            Utilisateur utilisateurRecupere = gestionnaireSessionUtilisateur.getSessionUtilisateur();


            if (v.getId() == R.id.btnPublierCom) {
                EditText com = findViewById(R.id.textCom);

                // Exécution de la tâche sur un thread séparé
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Création du nouveau commentaire
                        nouveauCommentaire = new Commentaire(0, com.getText().toString(), "", photoCom, utilisateurRecupere);

                        // Conversion du commentaire en format JSON
                        Gson gson = new Gson();

                        // Exportation du commentaire vers le serveur
                        InterrogationBD.exportCom(gson.toJson(nouveauCommentaire));

                        // Affichage d'un message sur le thread principal
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Création du commentaire effectuée", Toast.LENGTH_LONG).show();
                                Intent lanceActivity = new Intent(getApplicationContext(), CommentairePhoto.class);
                                lanceActivity.putExtra("Photo", photoCom);
                                startActivity(lanceActivity);
                            }
                        });
                    }
                });
            }
        }
    };
}
