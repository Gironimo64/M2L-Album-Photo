package com.example.albumphoto.entities.commentaire;

import com.example.albumphoto.entities.photo.Photos;
import com.example.albumphoto.entities.utilisateur.Utilisateur;

public class FusionPhotoUtil {
    private Photos photo;
    private Utilisateur utilisateur;

    public FusionPhotoUtil(Photos photo, Utilisateur utilisateur) {
        this.photo = photo;
        this.utilisateur = utilisateur;
    }

    public int getIdPhotoFusion(){return photo.getId_photo();}


    public int getIdUtilisateurFusion() {
        return utilisateur.getId();
    }
}

