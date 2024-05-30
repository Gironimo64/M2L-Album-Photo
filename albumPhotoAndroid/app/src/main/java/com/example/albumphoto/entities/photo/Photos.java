package com.example.albumphoto.entities.photo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.albumphoto.entities.commentaire.Commentaire;
import com.example.albumphoto.entities.utilisateur.Utilisateur;

import java.util.ArrayList;

/**
 * Classe représentant une photo dans l'application.
 */
public class Photos implements Parcelable {
    private final int id_photo;
    private String lien_photo;
    private String legende;
    private final String date_poster;
    private Utilisateur util;


    /**
     * Constructeur de la classe Photos.
     * @param id_photo L'identifiant de la photo.
     * @param lien_photo Le lien vers la photo.
     * @param legende La légende de la photo.
     * @param date_poster La date de publication de la photo.
     * @param util L'utilisateur associé à la photo.
     */
    public Photos(int id_photo, String lien_photo, String legende, String date_poster, Utilisateur util) {
        this.id_photo = id_photo;
        this.lien_photo = lien_photo;
        this.legende = legende;
        this.date_poster = date_poster;
        this.util = util;

    }

    protected Photos(Parcel in) {
        id_photo = in.readInt();
        lien_photo = in.readString();
        legende = in.readString();
        date_poster = in.readString();
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel in) {
            return new Photos(in);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };

    /**
     * Renvoie l'identifiant de la photo.
     * @return L'identifiant de la photo.
     */
    public int getId_photo() {
        return id_photo;
    }

    /**
     * Renvoie le lien vers la photo.
     * @return Le lien vers la photo.
     */
    public String getLien_photo() {
        return lien_photo;
    }

    /**
     * Renvoie la légende de la photo.
     * @return La légende de la photo.
     */
    public String getLegende() {
        return legende;
    }

    /**
     * Renvoie la date de publication de la photo.
     * @return La date de publication de la photo.
     */
    public String getDate_poster() {
        return date_poster;
    }

    /**
     * Renvoie l'identifiant de l'utilisateur associé à la photo.
     * @return L'identifiant de l'utilisateur.
     */
    public int getId(){
        return util.getId();
    }

    /**
     * Renvoie le pseudo de l'utilisateur associé à la photo.
     * @return Le pseudo de l'utilisateur.
     */
    public String getPseudo(){
        return util.getPseudo();
    }


    /**
     * Définit le lien vers la photo.
     * @param lien_photo Le lien vers la photo.
     */
    public void setLien_photo(String lien_photo) {
        this.lien_photo = lien_photo;
    }




    /**
     * Méthode d'interface. Inutilisée dans cette classe.
     * @return Toujours 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Écrit les données de l'objet dans un Parcel.
     * @param dest Le Parcel dans lequel écrire les données.
     * @param flags Des indicateurs optionnels pour le comportement de l'écriture.
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id_photo);
        dest.writeString(lien_photo);
        dest.writeString(legende);
        dest.writeString(date_poster);
    }
}
