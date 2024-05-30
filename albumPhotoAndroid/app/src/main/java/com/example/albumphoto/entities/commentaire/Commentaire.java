package com.example.albumphoto.entities.commentaire;

import com.example.albumphoto.entities.photo.Photos;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Représente un commentaire associé à une photo dans l'application Album Photo.
 */
public class Commentaire implements Parcelable {
    private final int id_com;
    private String commentaire;
    private final String date_com;
    private Photos photo;
    private Utilisateur util;

    /**
     * Constructeur de la classe Commentaire.
     * @param id_com L'identifiant unique du commentaire.
     * @param commentaire Le contenu du commentaire.
     * @param date_com La date de création du commentaire.
     * @param photo La photo à laquelle le commentaire est associé.
     * @param util L'utilisateur qui a posté le commentaire.
     */
    public Commentaire(int id_com, String commentaire, String date_com, Photos photo, Utilisateur util) {
        this.id_com = id_com;
        this.commentaire = commentaire;
        this.date_com = date_com;
        this.photo = photo;
        this.util = util;
    }

    // Méthodes getters et setters omises pour des raisons de concision

    // Implémentation des méthodes Parcelable pour permettre la transmission des objets Commentaire entre les composants de l'application
    // Les méthodes describeContents() et writeToParcel() sont générées automatiquement par l'IDE.

    /**
     * Interface Parcelable pour permettre la transmission des objets Commentaire entre les composants de l'application.
     */
    protected Commentaire(Parcel in) {
        id_com = in.readInt();
        commentaire = in.readString();
        date_com = in.readString();
        photo = in.readParcelable(Photos.class.getClassLoader());
        util = in.readParcelable(Utilisateur.class.getClassLoader());
    }

    public static final Creator<Commentaire> CREATOR = new Creator<Commentaire>() {
        @Override
        public Commentaire createFromParcel(Parcel in) {
            return new Commentaire(in);
        }

        @Override
        public Commentaire[] newArray(int size) {
            return new Commentaire[size];
        }
    };
    /**
     * Obtient l'identifiant unique du commentaire.
     * @return L'identifiant du commentaire.
     */
    public int getId_com() {
        return id_com;
    }

    /**
     * Obtient le contenu du commentaire.
     * @return Le contenu du commentaire.
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Obtient la date de création du commentaire.
     * @return La date de création du commentaire.
     */
    public String getDate_com() {
        return date_com;
    }

    /**
     * Obtient l'identifiant de la photo associée au commentaire.
     * @return L'identifiant de la photo.
     */
    public int getId_photo() {
        return photo.getId_photo();
    }


    /**
     * Obtient l'identifiant de l'utilisateur qui a posté le commentaire.
     * @return L'identifiant de l'utilisateur.
     */
    public int getUtilisateurId() {
        return util.getId();
    }

    /**
     * Obtient le pseudo de l'utilisateur qui a posté le commentaire.
     * @return Le pseudo de l'utilisateur.
     */
    public String getUtilisateurPseudo() {
        return util.getPseudo();
    }

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_com);
        dest.writeString(commentaire);
        dest.writeString(date_com);
        dest.writeParcelable(photo, flags);
        dest.writeParcelable(util, flags);
    }
}
