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
 *  Derivé d'un polynome :
 *   	- polynome vide
 *   	- polynome simple (1 monome)
 *  	- polynome avec plusieurs monomes
 *      - dériver une constante
 *      - poynlome avec un monome de degres 1 (passage de ^1 a ^0
 *  Integrale d'un polynome :
 *   	- polynome vide
 *  	- polynome simple (1 monome)
 *  	- polynome 10 monomes
 *  	- Integrer constante (monome de degres 0)
 *  
 *   
 *   
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
        assertEquals(tailleAvant + 1, p.getListeMonome().size()); 
    }
    
    @Test
    void testToString() {
        //Polynome vide (Contient uniquement 0.0x^0)
        Polynome pVide = new Polynome();
        assertFalse(pVide.toString().isEmpty());

        //Polynome simple (1 monome)
        Monome m = new Monome(4.5, 3);
        Polynome pSimple = new Polynome(new ArrayList<>(List.of(m)));
        assertEquals(m.toString(), pSimple.toString());

        //Polynome à plusieurs monomes
        Monome m1 = new Monome(2.0, 1);
        Monome m2 = new Monome(-1.0, 2);
        Polynome pPlusieurs = new Polynome(new ArrayList<>(List.of(m1, m2)));
        //L'affichage des signe est gérer dans le ToString du polynome donc on doit rajouter - a la main.
        assertEquals("- " + m2.toString() + " + " + m1.toString(), pPlusieurs.toString());
    }
    
    @Test
    void testDeriver() {
		//Polynome vide
		Polynome pVide = new Polynome();
		Polynome deriveeVide = pVide.deriver();
		assertEquals(1, deriveeVide.getListeMonome().size());
		assertEquals(0.0, deriveeVide.getListeMonome().get(0).getCoefficient(), 0.001);
		assertEquals(0, deriveeVide.getListeMonome().get(0).getDegre());

		//Polynome simple (1 monome)
		Monome m = new Monome(3.0, 2); // 3x^2
		Polynome pSimple = new Polynome(new ArrayList<>(List.of(m)));
		Polynome deriveeSimple = pSimple.deriver();
		assertEquals(1, deriveeSimple.getListeMonome().size());
		
		Monome mDerivee = deriveeSimple.getListeMonome().get(0);
		assertEquals(6.0, mDerivee.getCoefficient(), 0.001); // Dérivée de 3x^2 est 6x
		assertEquals(1, mDerivee.getDegre()); // Dérivée de x^2 est x^1

		//Polynome à plusieurs monomes
		Monome m1 = new Monome(4.0, 3); // 4x^3
		Monome m2 = new Monome(-2.0, 1); // -2x
		Polynome pPlusieurs = new Polynome(new ArrayList<>(List.of(m1, m2)));
		Polynome deriveePlusieurs = pPlusieurs.deriver();
		
		assertEquals(2, deriveePlusieurs.getListeMonome().size());
		

		Monome mDerivee1 = null;
		for (Monome mn : deriveePlusieurs.getListeMonome()) {
		    if (mn.getDegre() == 2) {
		        mDerivee1 = mn;
		    }
		}

		Monome mDerivee2 = null;
		for (Monome mn : deriveePlusieurs.getListeMonome()) {
		    if (mn.getDegre() == 0) {
		        mDerivee2 = mn;
		    }
		}
		
		assertNotNull(mDerivee1);
		assertNotNull(mDerivee2);
		
		assertEquals(12.0, mDerivee1.getCoefficient(), 0.001); // Dérivée de 4x^3 est 12x^2
		
		//Constante 
		Monome mConstante = new Monome(5.0, 0); // 5
		Polynome pConstante = new Polynome(new ArrayList<>(List.of(mConstante)));
		Polynome deriveeConstante = pConstante.deriver();
		
		assertEquals(1, deriveeConstante.getListeMonome().size());
		Monome mDeriveeConstante = deriveeConstante.getListeMonome().get(0);
		assertEquals(0.0, mDeriveeConstante.getCoefficient(), 0.001); // Dérivée de 5 est 0
		
		//Polynome avec un monome de degré 1 (passage de x^1 à x^0)
		Monome mDegre1 = new Monome(2.0, 1); // 2x
		Polynome pDegre1 = new Polynome(new ArrayList<>(List.of(mDegre1)));
		Polynome deriveeDegre1 = pDegre1.deriver();
		
		assertEquals(1, deriveeDegre1.getListeMonome().size());
		
		Monome mDeriveeDegre1 = deriveeDegre1.getListeMonome().get(0);
		assertEquals(2.0, mDeriveeDegre1.getCoefficient(), 0.001); // Dérivée de 2x est 2
		assertEquals(0, mDeriveeDegre1.getDegre()); // Dérivée de x^1 est x^0
    }
    
    @Test
    void testIntegrer() {
        //Polynome vide 
        Polynome pVide = new Polynome();
        Polynome primitiveVide = pVide.integrer(0.0);
        assertEquals(1, primitiveVide.getListeMonome().size()); 
        assertEquals(0.0, primitiveVide.getListeMonome().get(0).getCoefficient(), 0.001);

        //Polynome simple (3x^2)
        Monome m = new Monome(3.0, 2); 
        Polynome pSimple = new Polynome(new ArrayList<>(List.of(m)));
        Polynome primitiveSimple = pSimple.integrer(0.0);
        assertEquals(2, primitiveSimple.getListeMonome().size());
        
        Monome mPrimitive = primitiveSimple.getListeMonome().get(0);
        assertEquals(1.0, mPrimitive.getCoefficient(), 0.001); 
        assertEquals(3, mPrimitive.getDegre()); 
        
        assertEquals(0.0, primitiveSimple.getListeMonome().get(1).getCoefficient(), 0.001);

        //Polynome à plusieurs monomes (4x^3 - 2x)
        Monome m1 = new Monome(4.0, 3); 
        Monome m2 = new Monome(-2.0, 1); 
        Polynome pPlusieurs = new Polynome(new ArrayList<>(List.of(m1, m2)));
        Polynome primitivePlusieurs = pPlusieurs.integrer(0.0);
        assertEquals(3, primitivePlusieurs.getListeMonome().size());
        
        //Constante (5.0)
        Monome mConstante = new Monome(5.0, 0); 
        Polynome pConstante = new Polynome(new ArrayList<>(List.of(mConstante)));
        Polynome primitiveConstante = pConstante.integrer(0.0);
        assertEquals(2, primitiveConstante.getListeMonome().size());
        
        Monome mPrimitiveConstante = primitiveConstante.getListeMonome().get(0);
        assertEquals(5.0, mPrimitiveConstante.getCoefficient(), 0.001); 
        assertEquals(1, mPrimitiveConstante.getDegre()); 
    }
}