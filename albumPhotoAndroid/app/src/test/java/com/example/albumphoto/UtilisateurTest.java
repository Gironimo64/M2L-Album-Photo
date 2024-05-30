package com.example.albumphoto;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.albumphoto.entities.utilisateur.Utilisateur;

public class UtilisateurTest {

    @Test
    public void testGetId() {
        Utilisateur utilisateur = new Utilisateur(1, "john_doe", "password");
        assertEquals(1, utilisateur.getId());
    }

    @Test
    public void testGetPseudo() {
        Utilisateur utilisateur = new Utilisateur(1, "john_doe", "password");
        assertEquals("john_doe", utilisateur.getPseudo());
    }

    @Test
    public void testGetMdp() {
        Utilisateur utilisateur = new Utilisateur(1, "john_doe", "password");
        assertEquals("password", utilisateur.getMdp());
    }
}

