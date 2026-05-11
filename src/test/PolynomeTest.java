/*
 * PolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Polynome;
import polynome.model.Monome;  
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe Polynome.
 * 
 * Plan de test unitaire :
 * 	création de polynome vide
 * 	création a partir de liste de monome :
 * 		Liste vide , Liste 1 élement , liste 3 élement
 * 	ajouter un monome a un polynome : 
 * 		ajouter monome avec coefficient déja pris , avec un coefficient pas encore pris , coefficient 0.0 et coefficients normal.
 *  Recuperer la liste de monome :
 *  	liste vide , liste avec 1 element , liste avec 10 elements
 *  Affichage toString :
 *   	Polynome vide , polynome simple (1 monome) , polynome 10 monomes
 * 
 * @author Gabriel TOLL , Alban ROZES , Wassim NAFAA , Erwan NAZAL
 */

class PolynomeTest {

    @Test
    void testCreationVide() {
        Polynome p = new Polynome();
        assertTrue(p.getListeMonome().isEmpty());
    }
    
    @Test
    void testCreationListeMonome() {
        Monome m1 = new Monome(0.0 , 1);
        Monome m2 = new Monome(1.0 , 2);
        Monome m3 = new Monome(2.0 , 3);
        
        //Polynome vide
        ArrayList<Monome> listeMonome = new ArrayList<Monome>(List.of());
        Polynome p = new Polynome(listeMonome);
        assertEquals(listeMonome , p.getListeMonome());
        
        //Polynome avec 1 monome
        ArrayList<Monome> listeMonome1 = new ArrayList<Monome>(List.of(m1));
        Polynome p1 = new Polynome(listeMonome1);
        assertEquals(listeMonome1 , p1.getListeMonome());
        
        //Polynome avec 3 monome
        ArrayList<Monome> listeMonome3 = new ArrayList<Monome>(List.of(m1,m2,m3));
        Polynome p3 = new Polynome(listeMonome3);
        assertEquals(listeMonome3 , p3.getListeMonome());
    }
    
    @Test
    void testAjouterMonome() {
        
    }
    
    @Test
    void testToString() {
    	
    }
}