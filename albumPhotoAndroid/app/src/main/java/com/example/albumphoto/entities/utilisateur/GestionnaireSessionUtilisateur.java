package com.example.albumphoto.entities.utilisateur;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;

/**
 * Gestionnaire de session utilisateur pour enregistrer et récupérer les données de session utilisateur.
 */
public class GestionnaireSessionUtilisateur {
    private SharedPreferences preferencesUtilisateur;
    private static final String NOM_PREFS = "MesPrefs";
    private static final String CLE_UTILISATEUR = "utilisateur";

    /**
     * Constructeur de la classe GestionnaireSessionUtilisateur.
     * @param context Contexte de l'application.
     */
    public GestionnaireSessionUtilisateur(Context context) {
        preferencesUtilisateur = context.getSharedPreferences(NOM_PREFS, Context.MODE_PRIVATE);
    }

    /**
     * Enregistre les données de session de l'utilisateur dans les préférences partagées.
     * @param utilisateur Utilisateur dont les données de session doivent être enregistrées.
     */
    public void enregistrerSessionUtilisateur(Utilisateur utilisateur) {
        SharedPreferences.Editor editeur = preferencesUtilisateur.edit();
        Gson gson = new Gson();
        String json = gson.toJson(utilisateur);
        editeur.putString(CLE_UTILISATEUR, json);
        editeur.apply();
    }

    /**
     * Récupère les données de session de l'utilisateur à partir des préférences partagées.
     * @return L'utilisateur dont les données de session sont enregistrées, ou null s'il n'y en a pas.
     */
    public Utilisateur getSessionUtilisateur() {
        String json = preferencesUtilisateur.getString(CLE_UTILISATEUR, "");
        Gson gson = new Gson();
        return gson.fromJson(json, Utilisateur.class);
    }
}
