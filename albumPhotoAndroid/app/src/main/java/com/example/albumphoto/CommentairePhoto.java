package com.example.albumphoto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumphoto.entities.InterrogationBD;
import com.example.albumphoto.entities.commentaire.Commentaire;
import com.example.albumphoto.entities.commentaire.CommentaireAdapter;
import com.example.albumphoto.entities.commentaire.FusionPhotoUtil;
import com.example.albumphoto.entities.photo.Photos;
import com.example.albumphoto.entities.utilisateur.GestionnaireSessionUtilisateur;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Activité pour afficher les commentaires d'une photo.
 */
public class CommentairePhoto extends AppCompatActivity {

    // Gestionnaire de session utilisateur
    private GestionnaireSessionUtilisateur gestionnaireSessionUtilisateur;

    // Executor pour exécuter des tâches en arrière-plan
    private Executor executor = Executors.newSingleThreadExecutor();

    // Handler pour effectuer des mises à jour sur le thread principal
    private Handler handler = new Handler(Looper.getMainLooper());

    // Photo associée aux commentaires
    private Photos photoCom;

    // Liste des commentaires associés à la photo
    private ArrayList<Commentaire> listeCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentaire);

        listeCom= new ArrayList<>();

        // Initialisation du gestionnaire de session utilisateur
        gestionnaireSessionUtilisateur = new GestionnaireSessionUtilisateur(this);

        // Récupération de la photo extra de l'intent
        photoCom = getIntent().getParcelableExtra("Photo");
        Utilisateur utilisateurRecupere = gestionnaireSessionUtilisateur.getSessionUtilisateur();

        // Exécution d'une tâche en arrière-plan pour récupérer les commentaires de la base de données
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Récupération des utilisateurs de la base de données
                ArrayList<Utilisateur> resultUtilisateur = InterrogationBD.getLesUtilisateurs();

                // Parcours des utilisateurs récupérés
                for (Utilisateur utilisateur : resultUtilisateur) {
                    // Conversion de l'utilisateur en JSON
                    Gson gson = new Gson();
                    // Fusion de la photo et de l'utilisateur pour récupérer les commentaires
                    ArrayList<FusionPhotoUtil> fusionPhotUtil = new ArrayList<>();
                    fusionPhotUtil.add(new FusionPhotoUtil(photoCom, utilisateur));

                    // Récupération des commentaires de la base de données
                    ArrayList<Commentaire> resultCom = InterrogationBD.getLesCommentaire(gson.toJson(fusionPhotUtil));
                    if (!resultCom.isEmpty()) {
                        for (Commentaire com : resultCom) {
                            // Ajout des commentaires à la liste principale
                            listeCom.add(new Commentaire(com.getId_com(), com.getCommentaire(), com.getDate_com(), photoCom, utilisateur));
                        }
                    }
                }

                // Mise à jour de l'adaptateur et affichage de la liste de commentaires sur le thread principal
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!listeCom.isEmpty()){
                            // Initialisation de l'adaptateur pour la liste de commentaires
                            CommentaireAdapter adapter = new CommentaireAdapter(listeCom, utilisateurRecupere);
                            RecyclerView liste = findViewById(R.id.listeCommentaire);
                            liste.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            liste.setAdapter(adapter);
                            // Ajout d'un observateur de clic sur le bouton d'ajout de commentaire
                            Button btnAjouteCom = findViewById(R.id.btnAjouteCom);
                            btnAjouteCom.setOnClickListener(observateurClickBouton);
                            Button btnRetourAlbumPhoto= findViewById(R.id.btnRetourAlbumPhoto);
                            btnRetourAlbumPhoto.setOnClickListener(observateurClickBouton);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Aucun commentaire pour cette photo", Toast.LENGTH_LONG).show();
                            Intent lanceActivity = new Intent(getApplicationContext(), AlbumPhotos.class);
                            startActivity(lanceActivity);
                        }

                    }
                });
            }
        });
    }

    // Observateur de clic pour le bouton d'ajout de commentaire
    private View.OnClickListener observateurClickBouton = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.btnAjouteCom) {
                // Lance l'activité AjouteCommentaire lorsque le bouton est cliqué
                Intent lanceActivity = new Intent(getApplicationContext(), AjouteCommentaire.class);
                lanceActivity.putExtra("Photo", photoCom);
                startActivity(lanceActivity);
            }
            if (v.getId() == R.id.btnRetourAlbumPhoto){
                Intent lanceActivity = new Intent(getApplicationContext(), AlbumPhotos.class);
                startActivity(lanceActivity);
            }
        }
    };
}
