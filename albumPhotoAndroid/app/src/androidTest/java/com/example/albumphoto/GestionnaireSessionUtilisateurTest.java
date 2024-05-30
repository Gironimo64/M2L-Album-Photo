package com.example.albumphoto;





import android.content.Context;
import android.content.SharedPreferences;

import com.example.albumphoto.entities.utilisateur.GestionnaireSessionUtilisateur;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;


public class GestionnaireSessionUtilisateurTest {

    @Mock
    Context mockContext;

    @Mock
    SharedPreferences mockSharedPreferences;

    @Mock
    SharedPreferences.Editor mockEditor;

    private GestionnaireSessionUtilisateur gestionnaireSessionUtilisateur;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockContext.getSharedPreferences("MesPrefs", Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        gestionnaireSessionUtilisateur = new GestionnaireSessionUtilisateur(mockContext);
    }

    @Test
    public void testEnregistrerSessionUtilisateur() {
        Utilisateur utilisateur = new Utilisateur(1, "john_doe", "password");
        gestionnaireSessionUtilisateur.enregistrerSessionUtilisateur(utilisateur);
        verify(mockEditor).putString("utilisateur", new Gson().toJson(utilisateur));
        verify(mockEditor).apply();
    }

    @Test
    public void testGetSessionUtilisateur() {
        Utilisateur utilisateur = new Utilisateur(1, "john_doe", "password");
        String utilisateurJson = new Gson().toJson(utilisateur);
        when(mockSharedPreferences.getString("utilisateur", "")).thenReturn(utilisateurJson);
        Utilisateur utilisateurRecupere = gestionnaireSessionUtilisateur.getSessionUtilisateur();
        assertEquals(utilisateur.getId(), utilisateurRecupere.getId());
        assertEquals(utilisateur.getPseudo(), utilisateurRecupere.getPseudo());
        assertEquals(utilisateur.getMdp(), utilisateurRecupere.getMdp());
    }
}

