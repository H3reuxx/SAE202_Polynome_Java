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
 * 	réation de polynome vide
 * 	acréation a partir de liste de monome :
 * 		- Liste vide 
 * 		- Liste 1 élement 
 * 		- liste 3 élement
 * 	ajouter un monome a un polynome : 
 * 		- ajouter monome avec coefficient déja pris 
 * 		- avec un coefficient pas encore pris 
 * 		- coefficient 0.0 
 * 		- coefficients normaux.
 *  Recuperer la liste de monome :
 *  	- liste vide 
 *  	- liste avec 1 element 
 *  	- liste avec 10 elements
 *  Affichage toString :
 *   	- Polynome vide 
 *   	- polynome simple (1 monome) 
 *   	- polynome 10 monomes
 * @author Gabriel TOLL , Alban ROZES , Wassim NAFAA , Erwan NAZAL
 */

class PolynomeTest {

	@Test
    void testCreationVide() {
        Polynome p = new Polynome();
        
        assertEquals(1, p.getListeMonome().size());

        Monome monomeNul = p.getListeMonome().get(0);
        
        assertEquals(0.0, monomeNul.getCoefficient(), 0.001);
        assertEquals(0, monomeNul.getDegre());
    }
    
    @Test
    void testCreationListeMonome() {
        Monome m1 = new Monome(0.0 , 1);
        Monome m2 = new Monome(1.0 , 2);
        Monome m3 = new Monome(2.0 , 3);
        
        // Polynome vide
        ArrayList<Monome> listeMonome = new ArrayList<Monome>(List.of());
        Polynome p = new Polynome(listeMonome);
        assertEquals(listeMonome , p.getListeMonome());
        
        // Polynome avec 1 monome
        ArrayList<Monome> listeMonome1 = new ArrayList<Monome>(List.of(m1));
        Polynome p1 = new Polynome(listeMonome1);
        assertEquals(listeMonome1 , p1.getListeMonome());
        
        // Polynome avec 3 monomes
        ArrayList<Monome> listeMonome3 = new ArrayList<Monome>(List.of(m1,m2,m3));
        Polynome p3 = new Polynome(listeMonome3);
        assertEquals(listeMonome3 , p3.getListeMonome());
    }
    
    @Test
    void testAjouterMonome() {
        //Préparation
        Monome mBase = new Monome(3.0, 2); // 3x^2
        Polynome p = new Polynome(new ArrayList<>(List.of(mBase)));

        //Ajouter un monome avec un coefficient normal et un degré PAS ENCORE PRIS
        Monome mNouveauDegre = new Monome(5.0, 4); // 5x^4
        p.ajouterMonome(mNouveauDegre);
        assertEquals(2, p.getListeMonome().size());
        assertTrue(p.getListeMonome().contains(mNouveauDegre));

        //Ajouter un monome avec un degré DÉJÀ PRIS (doit fusionner les coefficients)
        Monome mMemeDegre = new Monome(2.0, 2); // 2x^2 à ajouter à 3x^2 -> doit faire 5x^2
        p.ajouterMonome(mMemeDegre);
        
        //On cherche le monome de degré 2 pour vérifier son nouveau coefficient
        Monome mResultat = p.getListeMonome().stream()
                            .filter(m -> m.getDegre() == 2)
                            .findFirst().orElse(null);
        
        assertNotNull(mResultat);
        assertEquals(5.0, mResultat.getCoefficient()); // 3.0 + 2.0

        //Ajouter un monome avec un coefficient de 0.0
        int tailleAvant = p.getListeMonome().size();
        Monome mZero = new Monome(0.0, 5);
        p.ajouterMonome(mZero);
        // Selon votre choix de conception, soit il est ajouté, soit ignoré. 
        // Ici on teste s'il est ajouté à la liste :
        assertEquals(tailleAvant + 1, p.getListeMonome().size()); 
    }
    
    @Test
    void testToString() {
        //Polynome vide (Contient uniquement 0.0x^0)
        Polynome pVide = new Polynome();
        //Si Monome.toString() renvoie "0.0x^0", alors le résultat sera "0.0x^0 "
        assertFalse(pVide.toString().isEmpty());

        //Polynome simple (1 monome)
        Monome m = new Monome(4.5, 3);
        Polynome pSimple = new Polynome(new ArrayList<>(List.of(m)));
        assertEquals(m.toString() + " ", pSimple.toString());

        //Polynome à plusieurs monomes
        Monome m1 = new Monome(2.0, 1);
        Monome m2 = new Monome(-1.0, 2);
        Polynome pPlusieurs = new Polynome(new ArrayList<>(List.of(m1, m2)));
        assertEquals(m1.toString() + " " + m2.toString() + " ", pPlusieurs.toString());
    }
}