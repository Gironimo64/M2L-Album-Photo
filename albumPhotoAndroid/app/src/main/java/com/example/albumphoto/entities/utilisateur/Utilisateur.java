package com.example.albumphoto.entities.utilisateur;

import com.example.albumphoto.entities.commentaire.Commentaire;
import com.example.albumphoto.entities.photo.Photos;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe représentant un utilisateur de l'application.
 */
public class Utilisateur implements Parcelable {
    private final int id;
    private final String pseudo;
    private final String mdp;

    /**
     * Constructeur de la classe Utilisateur.
     * @param id Identifiant de l'utilisateur.
     * @param pseudo Pseudo de l'utilisateur.
     * @param mdp Mot de passe de l'utilisateur.
     */
    public Utilisateur(int id, String pseudo, String mdp) {
        this.id = id;
        this.pseudo = pseudo;
        this.mdp=mdp;
    }

    protected Utilisateur(Parcel in) {
        id = in.readInt();
        pseudo = in.readString();
        mdp = in.readString();
    }

    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };

    /**
     * Retourne l'identifiant de l'utilisateur.
     * @return L'identifiant de l'utilisateur.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le pseudo de l'utilisateur.
     * @return Le pseudo de l'utilisateur.
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     * @return Le mot de passe de l'utilisateur.
     */
    public String getMdp() {
        return mdp;
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
        dest.writeInt(id);
        dest.writeString(pseudo);
        dest.writeString(mdp);
    }
}
