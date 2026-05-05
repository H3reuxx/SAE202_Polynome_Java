/*
 * PolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Polynome;
import polynome.model.Monome;  
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe Polynome.
 * 
 * Plan de test unitaire :
 * 	création de polynome vide
 * 	création a partir de liste de monome :
 * 		Liste vide , Liste 1 élement , liste 10 élement
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
    	
    }
    
    @Test
    void testAjouterMonome() {
    	
    }
    
    @Test
    void testGetListeMonome() {
    	
    }
    
    @Test
    void testToString() {
    	
    }
}