package com.example.albumphoto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.albumphoto.entities.InterrogationBD;
import com.example.albumphoto.entities.photo.Photos;
import com.example.albumphoto.entities.utilisateur.GestionnaireSessionUtilisateur;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Activité permettant à l'utilisateur d'ajouter une photo depuis la galerie ou l'appareil photo.
 */
public class AjoutePhoto extends AppCompatActivity {
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private GestionnaireSessionUtilisateur gestionnaireSessionUtilisateur;
    private Uri uriImage;
    private Photos nouvellePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ajoute_photo);

        // Initialisation du gestionnaire de session utilisateur
        gestionnaireSessionUtilisateur = new GestionnaireSessionUtilisateur(this);

        // Définition des observateurs de clic pour les boutons de la galerie et de l'appareil photo
        Button btnGalerie = findViewById(R.id.btnGalerie);
        btnGalerie.setOnClickListener(observateurClickBouton);

        Button btnAppareilPhoto = findViewById(R.id.btnAppareilPhoto);
        btnAppareilPhoto.setOnClickListener(observateurClickBouton);
    }

    /**
     * Observateur de clic pour les boutons de la galerie et de l'appareil photo.
     */
    private View.OnClickListener observateurClickBouton = new View.OnClickListener() {
        public void onClick(View v) {
            Utilisateur utilisateurRecupere = gestionnaireSessionUtilisateur.getSessionUtilisateur();
            EditText legende = findViewById(R.id.txtLegende);
            nouvellePhoto=new Photos(0,"",legende.getText().toString(),"",utilisateurRecupere);

            if (v.getId() == R.id.btnGalerie) {
                // Demander la permission d'accès à la galerie avant de lancer l'intent
                requestGalleryPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);

            }
            if (v.getId() == R.id.btnAppareilPhoto) {
                // Demander la permission d'accès à l'appareil photo avant de lancer l'intent
                requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA);
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // Exporter la nouvelle photo vers le serveur
                    Gson gson = new Gson();
                    InterrogationBD.exportPhoto(gson.toJson(nouvellePhoto));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Affichage d'un message de succès sur le thread principal
                            Toast.makeText(getApplicationContext(), "Photo créée", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    };

    /**
     * Laucheur du résultat pour la photo sélection de la galerie.
     */
    private final ActivityResultLauncher<Intent> galerieLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        // Récupérer l'URI de l'image sélectionnée
                        executor.execute(() -> {
                            try {
                                uriImage = data.getData();
                                InputStream inputStream = getContentResolver().openInputStream(uriImage);
                                String nomPhoto = InterrogationBD.exportImage(inputStream);
                                runOnUiThread(() -> {
                                    // Mettre à jour le lien de la nouvelle photo
                                    nouvellePhoto.setLien_photo(nomPhoto);
                                    // Afficher un message sur le thread principal
                                    Toast.makeText(getApplicationContext(), "Image envoyée", Toast.LENGTH_LONG).show();
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
    );
    private final ActivityResultLauncher<String> requestGalleryPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // La permission a été accordée, vous pouvez maintenant lancer l'intent pour la galerie
                    Intent galerieIntent = new Intent(Intent.ACTION_PICK);
                    galerieIntent.setType("image/*");
                    galerieLauncher.launch(galerieIntent);
                } else {
                    // La permission a été refusée, affichez un message à l'utilisateur ou guidez-le vers les paramètres de l'application
                    Toast.makeText(getApplicationContext(), "La permission d'accès à la galerie est nécessaire", Toast.LENGTH_SHORT).show();
                }
            });


    /**
     * Laucheur du résultat pour la capture de l'appareil photo.
     */
    private final ActivityResultLauncher<Intent> appareilPhotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getExtras() != null) {
                        executor.execute(() -> {
                            try {
                                uriImage = Uri.parse(data.getExtras().get("data").toString());
                                InputStream inputStream = getContentResolver().openInputStream(uriImage);
                                final String nomPhoto = InterrogationBD.exportImage(inputStream);
                                runOnUiThread(() -> {
                                    // Mettre à jour le lien de la nouvelle photo
                                    nouvellePhoto.setLien_photo(nomPhoto);
                                    // Afficher un message sur le thread principal
                                    Toast.makeText(getApplicationContext(), "Image envoyée", Toast.LENGTH_LONG).show();
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
    );
    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // La permission a été accordée, vous pouvez maintenant lancer l'intent pour l'appareil photo
                    Intent prendrePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    appareilPhotoLauncher.launch(prendrePhotoIntent);
                } else {
                    // La permission a été refusée, affichez un message à l'utilisateur ou guidez-le vers les paramètres de l'application
                    Toast.makeText(getApplicationContext(), "La permission d'accès à l'appareil photo est nécessaire", Toast.LENGTH_SHORT).show();
                }
            });

}
