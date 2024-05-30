package com.example.albumphoto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.albumphoto.entities.InterrogationBD;
import com.example.albumphoto.entities.photo.Photos;
import com.example.albumphoto.entities.photo.PhtotosAdapter;
import com.example.albumphoto.entities.utilisateur.GestionnaireSessionUtilisateur;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;

/**
 * Activité principale de l'application Album Photo.
 * Cette activité affiche la liste des photos de l'utilisateur courant.
 */
public class AlbumPhotos extends AppCompatActivity {

    // Executor pour exécuter des tâches en arrière-plan
    private Executor executor = Executors.newSingleThreadExecutor();

    // Handler pour effectuer des mises à jour sur le thread principal
    private Handler handler = new Handler(Looper.getMainLooper());

    // Gestionnaire de session utilisateur
    private GestionnaireSessionUtilisateur gestionnaireSessionUtilisateur;

    // Liste des photos récupérées de la base de données
    private ArrayList<Photos> listePhoto;

    // Résultat de la requête pour récupérer les utilisateurs de la base de données
    private ArrayList<Utilisateur> resultUtilisateur;

    // Résultat de la requête pour récupérer les photos de la base de données
    private ArrayList<Photos> resultPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.album_photo);

        // Initialisez la liste de photos
        listePhoto = new ArrayList<>();

        // Initialisez les résultats des requêtes
        resultUtilisateur= new ArrayList<>();
        resultPhoto= new ArrayList<>();

        // Initialisez le gestionnaire de session utilisateur
        gestionnaireSessionUtilisateur = new GestionnaireSessionUtilisateur(this);

        // Récupérez l'utilisateur courant de la session
        Utilisateur utilisateurRecupere = gestionnaireSessionUtilisateur.getSessionUtilisateur();

        // Exécutez une tâche en arrière-plan pour récupérer les données de la base de données
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Récupérez les utilisateurs de la base de données
                resultUtilisateur = InterrogationBD.getLesUtilisateurs();

                // Parcourez les utilisateurs récupérés
                for (Utilisateur utilisateur : resultUtilisateur) {
                    // Convertissez l'utilisateur en JSON
                    Gson gson = new Gson();
                    // Récupérez les photos de l'utilisateur de la base de données
                    resultPhoto = InterrogationBD.getLesPhotos(gson.toJson(utilisateur));

                    // Parcourez les photos récupérées
                    for (Photos photo : resultPhoto) {
                        // Ajoutez les photos de l'utilisateur actuel à la liste de photos principale
                        listePhoto.add(new Photos(photo.getId_photo(), photo.getLien_photo(), photo.getLegende(), photo.getDate_poster(), utilisateur));
                    }
                }

                // Mise à jour de l'adaptateur et affichage de la liste de photos sur le thread principal
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Initialisation de l'adaptateur pour la liste de photos
                        PhtotosAdapter adapter = new PhtotosAdapter(listePhoto, utilisateurRecupere);
                        RecyclerView liste = findViewById(R.id.listePhoto);
                        liste.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        liste.setAdapter(adapter);

                        // Ajoute un setOnClickListener sur le bouton d'ajout de photo
                        Button btnAjoutPhoto = findViewById(R.id.btnAjoutPhoto);
                        btnAjoutPhoto.setOnClickListener(observateurClickBouton);

                        //Ajoute un setOnClickListener sur le bonton déconnexion
                        Button btnRetourAcueil= findViewById(R.id.btnRetourAcueil);
                        btnRetourAcueil.setOnClickListener(observateurClickBouton);

                    }
                });
            }
        });
    }

    // Observateur de clic pour le bouton d'ajout de photo
    private View.OnClickListener observateurClickBouton = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.btnAjoutPhoto) {
                // Lancez l'activité AjoutePhoto lorsque le bouton est cliqué
                Intent lanceActivity = new Intent(getApplicationContext(), AjoutePhoto.class);
                startActivity(lanceActivity);
            }
            if (v.getId() == R.id.btnRetourAcueil){
                // Lancez l'activité ConnexionInscription lorsque le bouton est cliqué
                Intent lanceActivity = new Intent(getApplicationContext(), ConnexionInscription.class);
                startActivity(lanceActivity);
            }
        }
    };
}
