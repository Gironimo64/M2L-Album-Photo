package com.example.albumphoto.entities;



import android.util.Log;

import com.example.albumphoto.entities.commentaire.Commentaire;
import com.example.albumphoto.entities.photo.Photos;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import okhttp3.*;

/**
 * Cette classe fournit des méthodes pour interagir avec la base de données distante.
 */
public class InterrogationBD {
    private static OkHttpClient client = new OkHttpClient();

    //à changer en fonction de votre chemin de projet dans localhoste en gardant 10.0.2.2
    private static String urlCommune="http://10.0.2.2/projet/ap-slam/Album%20Photo/";

    /**
     * Récupère la liste des utilisateurs depuis la base de données MySQL.
     * @return La liste des utilisateurs.
     */
    public static ArrayList<Utilisateur> getLesUtilisateurs(){
        try {
            String adrSrv=urlCommune+"import/importUtilisateur.php";
            // Préparation de la requête.
            Request request = new Request.Builder().url(adrSrv).build();
            // Exécution de la requête.
            Response response = client.newCall(request).execute();
            // Récupération du résultat de la requête.
            Gson gson = new Gson();
            return gson.fromJson(response.body().string(), new TypeToken<ArrayList<Utilisateur>>() {}.getType());
        } catch (MalformedURLException e) {
            Log.i("monTAG", "URL incorrecte " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.i("monTAG", "Problème I/O " + e.getMessage());
            return null;
        }
    }

    /**
     * Exporte les informations d'un nouveau utilisateur vers la base de données MySQL.
     * @param utilisateur Les informations de l'utilisateur à exporter.
     */
    public static void exportUtilisateur(String utilisateur){
        try{
            String adrSrv=urlCommune+"export/exportUtilisateur.php";
            // Préparation de la requête.
            MediaType JSON= MediaType.parse("application/json; charset=utf-8");
            RequestBody body=RequestBody.create(JSON,utilisateur);
            Request request = new Request.Builder().url(adrSrv).post(body).build();
            // Exécution de la requête.
            client.newCall(request).execute();
        } catch (MalformedURLException e) {
            Log.i("monTAG", "URL incorrecte " + e.getMessage());
        } catch (IOException e) {
            Log.i("monTAG", "Problème I/O " + e.getMessage());
        }
    }

    /**
     * Récupère la liste des photos depuis la base de données MySQL grâce à un objet utilisateur.
     * @return La liste des photos.
     */
    public static ArrayList<Photos> getLesPhotos(String utilisateur){
        try {
            String adrSrv=urlCommune+"import/importPhoto.php";
            // Préparation de la requête.
            MediaType JSON= MediaType.parse("application/json; charset=utf-8");
            RequestBody body=RequestBody.create(JSON,utilisateur);

            Request request = new Request.Builder().url(adrSrv).post(body).build();
            // Exécution de la requête.
            Response response = client.newCall(request).execute();

            Gson gson = new Gson();
            String responseText= response.body().string();

            if (responseText.startsWith("[")) {
                //Si la réponse est bon
                return gson.fromJson(responseText, new TypeToken<ArrayList<Photos>>() {}.getType());
            } else {
                return new ArrayList<Photos>();
            }
        } catch (MalformedURLException e) {
            Log.i("monTAG", "URL incorrecte " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.i("monTAG", "Problème I/O " + e.getMessage());
            return null;
        }
    }

    /**
     * Exporte les informations de la photo vers la base de données distante.
     * @param photo Les informations de la photo à exporter.
     */
    public static void exportPhoto(String photo){
        try{
            String adrSrv=urlCommune+"export/exportPhoto.php";
            MediaType JSON= MediaType.parse("application/json; charset=utf-8");
            RequestBody body=RequestBody.create(JSON,photo);
            Request request = new Request.Builder().url(adrSrv).post(body).build();
            client.newCall(request).execute();

        } catch (MalformedURLException e) {
            Log.i("monTAG", "URL incorrecte " + e.getMessage());
        } catch (IOException e) {
            Log.i("monTAG", "Problème I/O " + e.getMessage());
        }
    }
    /**
     * Exporte une image vers le serveur distant.
     *
     * @param inputStream Le flux d'entrée contenant les données de l'image à exporter.
     * @return Le nom de fichier de l'image exportée.
     */
    public static String exportImage(InputStream inputStream) {
        String fileName = null;
        try {
            // Adresse du service sur le serveur distant pour l'importation des images
            String adrSrv = urlCommune + "img/";

            // Génération d'un nom de fichier unique pour l'image à exporter
            fileName = "image_" + System.currentTimeMillis() + ".jpg";

            // Création d'un flux de sortie pour écrire les données de l'image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            // Lecture des données de l'image à partir du flux d'entrée et écriture dans le flux de sortie
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Conversion des données de l'image en tableau de bytes
            byte[] imageBytes = outputStream.toByteArray();

            // Création du corps de la requête multipart pour l'envoi de l'image
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", fileName, RequestBody.create(MediaType.parse("image/jpeg"), imageBytes))
                    .build();

            // Création de la requête POST vers le service d'exportation de l'image
            Request request = new Request.Builder().url(adrSrv).post(requestBody).build();

            // Exécution de la requête HTTP pour exporter l'image vers le serveur
            client.newCall(request).execute();

            // Fermeture des flux
            inputStream.close();
            outputStream.close();
        } catch (MalformedURLException e) {
            // Gestion des erreurs liées à l'URL incorrecte
            Log.i("monTAG", "URL incorrecte " + e.getMessage());
        } catch (IOException e) {
            // Gestion des erreurs liées aux problèmes d'entrée/sortie
            Log.i("monTAG", "Problème I/O " + e.getMessage());
        }
        return fileName;
    }

    /**
     * Exporte une demande de suppression de photo vers le serveur distant.
     *
     * @param photo Les données de la photo à supprimer, généralement au format JSON.
     */
    public static void exportSupprPhoto(String photo) {
        try {
            // Adresse du service sur le serveur distant pour l'exportation de la demande de suppression de photo
            String adrSrv = urlCommune + "export/exportSupprPhoto.php";

            // Création du type de contenu JSON
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            // Création du corps de la requête contenant les données de la demande de suppression de photo
            RequestBody body = RequestBody.create(JSON, photo);

            // Création de la requête POST vers le service d'exportation de la demande de suppression de photo
            Request request = new Request.Builder().url(adrSrv).post(body).build();

            // Exécution de la requête HTTP pour exporter la demande de suppression de photo vers le serveur
            client.newCall(request).execute();

        } catch (MalformedURLException e) {
            // Gestion des erreurs liées à l'URL incorrecte
            Log.i("monTAG", "URL incorrecte " + e.getMessage());
        } catch (IOException e) {
            // Gestion des erreurs liées aux problèmes d'entrée/sortie
            Log.i("monTAG", "Problème I/O " + e.getMessage());
        }
    }

    /**
     * Récupère la liste des commentaires à partir du serveur distant.
     *
     * @param fusionPhotUtil La photo et l'utilisateur nécessaires à la requête de récupération des commentaires, envoyer au format JSON.
     * @return La liste des commentaires récupérés depuis le serveur.
     */
    public static ArrayList<Commentaire> getLesCommentaire(String fusionPhotUtil) {
        try {
            // Adresse du service sur le serveur distant pour l'importation des commentaires
            String adrSrv = urlCommune + "import/importCommentaire.php";

            // Création du type de contenu JSON
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            // Création du corps de la requête contenant les informations nécessaires
            RequestBody body = RequestBody.create(JSON, fusionPhotUtil);

            // Création de la requête POST vers le service d'importation des commentaires
            Request request = new Request.Builder().url(adrSrv).post(body).build();

            // Exécution de la requête HTTP pour récupérer les commentaires depuis le serveur
            Response response = client.newCall(request).execute();

            // Récupération du texte de la réponse
            String responseText = response.body().string();

            Gson gson = new Gson();

            // Vérification si la réponse est au format JSON valide
            if (responseText.startsWith("[")) {
                // Si la réponse est au format JSON valide, retourne la liste des commentaires désérialisée
                return gson.fromJson(responseText, new TypeToken<ArrayList<Commentaire>>() {}.getType());

            } else {
                // Si la réponse n'est pas au format JSON valide, retourne une liste vide
                return new ArrayList<>();
            }

        } catch (MalformedURLException e) {
            // Gestion des erreurs liées à l'URL incorrecte
            Log.i("monTAG", "URL incorrecte " + e.getMessage());
            return null;
        } catch (IOException e) {
            // Gestion des erreurs liées aux problèmes d'entrée/sortie
            Log.i("monTAG", "Problème I/O " + e.getMessage());
            return null;
        }
    }




    /**
     * Exporte un commentaire vers le serveur distant.
     *
     * @param commentaire Le commentaire à exporter, généralement au format JSON.
     */
    public static void exportCom(String commentaire) {
        try {
            // Adresse du service sur le serveur distant pour l'exportation des commentaires
            String adrSrv = urlCommune + "export/exportCommentaire.php";

            // Création du type de contenu JSON
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            // Création du corps de la requête contenant le commentaire à exporter
            RequestBody body = RequestBody.create(JSON, commentaire);

            // Création de la requête POST vers le service d'exportation des commentaires
            Request request = new Request.Builder().url(adrSrv).post(body).build();

            // Exécution de la requête HTTP pour exporter le commentaire vers le serveur
            client.newCall(request).execute();
        } catch (MalformedURLException e) {
            // Gestion des erreurs liées à l'URL incorrecte
            Log.i("monTAG", "URL incorrecte " + e.getMessage());

        } catch (IOException e) {
            // Gestion des erreurs liées aux problèmes d'entrée/sortie
            Log.i("monTAG", "Problème I/O " + e.getMessage());

        }
    }


    /**
     * Exporte la suppression d'un commentaire vers le serveur distant.
     *
     * @param fusionPhotoUtil Les infots du commentaire à supprimer, généralement au format JSON.
     */
    public static void exportSupprCom(String fusionPhotoUtil) {
        try {
            // Adresse du service sur le serveur distant pour l'exportation de la suppression de commentaire
            String adrSrv = urlCommune + "export/exportSupprCommentaire.php";

            // Création du type de contenu JSON
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            // Création du corps de la requête contenant le commentaire à supprimer
            RequestBody body = RequestBody.create(JSON, fusionPhotoUtil);

            // Création de la requête POST vers le service d'exportation de suppression de commentaire
            Request request = new Request.Builder().url(adrSrv).post(body).build();

            // Exécution de la requête HTTP pour exporter la suppression du commentaire vers le serveur
            client.newCall(request).execute();


        } catch (MalformedURLException e) {
            // Gestion des erreurs liées à l'URL incorrecte
            Log.i("monTAG", "URL incorrecte " + e.getMessage());

        } catch (IOException e) {
            // Gestion des erreurs liées aux problèmes d'entrée/sortie
            Log.i("monTAG", "Problème I/O " + e.getMessage());

        }
    }


    /**
     * Renvoie l'URL commune utilisée pour les requêtes vers le serveur distant.
     *
     * @return L'URL commune pour les requêtes vers le serveur.
     */
    public static String getUrlCommune() {
        return urlCommune;
    }
}
