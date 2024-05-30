package com.example.albumphoto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.albumphoto.entities.utilisateur.GestionnaireSessionUtilisateur;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.albumphoto.entities.*;
import com.google.gson.Gson;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Activité gérant la connexion et l'inscription des utilisateurs.
 */
public class ConnexionInscription extends AppCompatActivity {

    // Executor pour exécuter des tâches en arrière-plan
    private Executor executor = Executors.newSingleThreadExecutor();

    // Handler pour effectuer des mises à jour sur le thread principal
    private Handler handler = new Handler(Looper.getMainLooper());

    // Gestionnaire de session utilisateur
    private GestionnaireSessionUtilisateur gestionnaireSessionUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.connexion_inscription);

        // Récupération des boutons de connexion et d'inscription
        Button btnConnexion = findViewById(R.id.btnConnexion);
        Button btnInscription = findViewById(R.id.btnInscription);

        // Ajout des observateurs de clic sur les boutons
        btnConnexion.setOnClickListener(observateurClickBouton);
        btnInscription.setOnClickListener(observateurClickBouton);

        // Initialisation du gestionnaire de session utilisateur
        gestionnaireSessionUtilisateur = new GestionnaireSessionUtilisateur(this);
    }

    // Observateur de clic sur les boutons de connexion et d'inscription
    View.OnClickListener observateurClickBouton = new View.OnClickListener() {
        public void onClick(View v) {
            // Gestion du clic sur le bouton de connexion
            if (v.getId() == R.id.btnConnexion) {
                EditText pseudo = findViewById(R.id.txtPseudo);
                EditText motDePasse = findViewById(R.id.txtPassword);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Récupération de la liste des utilisateurs depuis la base de données
                        ArrayList<Utilisateur> result = InterrogationBD.getLesUtilisateurs();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                int i = 0;
                                boolean trouve = false;
                                // Parcours de la liste des utilisateurs
                                while (i < result.size() && !trouve) {
                                    // Vérification des identifiants
                                    if (Objects.equals(result.get(i).getPseudo(), pseudo.getText().toString()) &&
                                            BCrypt.checkpw(motDePasse.getText().toString(), result.get(i).getMdp())) {
                                        trouve = true;
                                        // Connexion réussie
                                        Toast.makeText(getApplicationContext(), "Connexion Réussie ", Toast.LENGTH_SHORT).show();
                                        // Enregistrement de la session utilisateur
                                        Utilisateur utilisateurSession = result.get(i);
                                        gestionnaireSessionUtilisateur.enregistrerSessionUtilisateur(utilisateurSession);
                                        // Redirection vers l'activité AlbumPhotos
                                        Intent lanceActivity = new Intent(getApplicationContext(), AlbumPhotos.class);
                                        startActivity(lanceActivity);
                                    }
                                    i+=1;
                                }
                                // Gestion des cas où l'utilisateur n'est pas trouvé
                                if (!trouve) {
                                    Toast.makeText(getApplicationContext(), "Utilisateur ou Mot de Passe incorrect ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
            // Gestion du clic sur le bouton d'inscription
            if (v.getId() == R.id.btnInscription) {
                EditText pseudo = findViewById(R.id.txtPseudo);
                EditText motDePasse = findViewById(R.id.txtPassword);
                // Hachage du mot de passe avant l'inscription
                String motDePasseHache = BCrypt.hashpw(motDePasse.getText().toString(), BCrypt.gensalt());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Création d'un nouvel utilisateur
                        Utilisateur nouveauUtilisateur= new Utilisateur(0,pseudo.getText().toString(),motDePasseHache);
                        Gson gson = new Gson();
                        // Envoi de la demande d'inscription à la base de données
                        InterrogationBD.exportUtilisateur(gson.toJson(nouveauUtilisateur));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Affichage d'un toast pour confirmer la création du compte
                                Toast.makeText(getApplicationContext(), "Création du compte effectué", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }
    };
}
