/*
 * CalculateurPolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;
import polynome.calculs.CalculateurPolynome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import polynome.model.CreationPolynome;
import polynome.model.Monome;
import polynome.model.Polynome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe CalculateurPolynome.
 * 
 * Plan de test : 
 *  test calcul de l'addition de polynome :
 *  	- addition de polynome avec des monomes de même degre
 *  	- addition de polynome avec des monomes de degre différent
 *  	- addition de polynome avec des monomes de même degre et de degre différent
 *  	- addition de polynome avec des monomes de même degre et de degre différent et des monomes nuls
 *  	- addition de deux polynome nul
 *  test de calcul de la multiplication de polynome :
 *  	- multiplication de polynome avec des monomes de même degre
 *  	- multiplication de polynome avec des monomes de degre différent
 *  	- multiplication de polynome avec des monomes de même degre et de degre différent
 *  	- multiplication de polynome avec des monomes de même degre et de degre différent et des monomes nuls
 *  	- multiplication de deux polynome nul
 *  tes multiplication par un scalaire : 
 *  	- multiplication de polynome avec des monomes de même degre
 *  TriInsertion :
 *   	- polynome vide
 *   	- polynome simple (1 monome)
 *   	- polynome 10 monomes
 *   test de calcul de la multiplication de polynome avec un scalaire :
 *      - mulitiplication de polynome avec un scalaire positif 
 *      - multiplication de polynome avec un scalaire négatif 
 *      - multiplication de polynome avec un scalaire nul
 *      - multiplication de polynome avec un scalaire de type double
 */


class CalculateurPolynomeTest {
	
	// Declaration des variables pour les tests
    private Monome m1, m2, m3, mnull , m4, m5, m6, m7;
    private Polynome p1, p2, p3, pnul , pscalaire1, pscalaire2, pscalaire3, pscalaire4;

    
    /**
     * Initialise les variables pour reutiliser dans les tests
     */
    @BeforeEach
    public void setUp() {
        m1 = new Monome(1.0 , 1);
        m2 = new Monome(2.0 , 2);
        m3 = new Monome(3.0 , 3);
        mnull = new Monome(0.0 , 0);
        //Monome pour les tests mulitplier avec un scalaire 
        m4 = new Monome(2.0, 0);
        m5 = new Monome(-5.0, 0);
        m6 = new Monome(0.0, 0);
        m7 = new Monome(3.5, 0);
        
        p1 = new Polynome(List.of(m1 , m2));
        p2 = new Polynome(List.of(m3));
        p3 = new Polynome(List.of(m2 , m3));
        pnul = new Polynome(List.of(mnull));
        //Polynome pour les tests multiplier avec un scalaire
        pscalaire1 = new Polynome(List.of(m4));
        pscalaire2 = new Polynome(List.of(m5));
        pscalaire3 = new Polynome(List.of(m6));
        pscalaire4 = new Polynome(List.of(m7));
        
    }

	
    @Test
    public void testAdditionDePolynome() {
		
		//Test de l'addition de polynome avec des monomes de même degre
		Polynome resultat1 = CalculateurPolynome.additionnerPolynome(p1, p1);
		Polynome resultatAttendue1 = new Polynome(List.of(new Monome(2.0 , 1) , new Monome(4.0 , 2)));
		assertEquals(resultat1, resultatAttendue1);
		
		//Test de l'addition de polynome avec des monomes de degre différent
		Polynome resultat2 = CalculateurPolynome.additionnerPolynome(p1, p2); 
		Polynome resultatAttendue2 = new Polynome(List.of(new Monome(1.0 , 1) , new Monome(2.0 , 2) , new Monome(3.0 , 3)));
		assertEquals(resultat2, resultatAttendue2);
		
		//Test de l'addition de polynome avec des monomes de même degre et de degre différent
		Polynome resultat3 = CalculateurPolynome.additionnerPolynome(p1, p3);
		Polynome resultatAttendue3 = new Polynome(List.of(new Monome(1.0 , 1) , new Monome(4.0 , 2) , new Monome(3.0 , 3)));
		assertEquals(resultat3, resultatAttendue3);
		
		
		//Test de l'addition de polynome avec des monomes de même degre et de degre différent et des monomes nuls
		Polynome resultat4 = CalculateurPolynome.additionnerPolynome(p1, pnul);
		Polynome resultatAttendue4 = new Polynome(List.of(new Monome(1.0 , 1) , new Monome(2.0 , 2)));
		System.out.println(resultat4);
		System.out.println(resultatAttendue4);
		assertEquals(resultat4, resultatAttendue4);
		
		//Test de l'addition de polynome nul avec un polynome nul
		Polynome resultat5 = CalculateurPolynome.additionnerPolynome(pnul, pnul);
		assertEquals(resultat5, pnul);
    }
    
@Test 
public void testMultiplicationDePolynome() {
	
	
        Polynome p1 = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(0.0, 2.0, 3.0));
        
        Polynome p2 = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(0.0, 0.0, 0.0, 3.0));
        
        Polynome p3 = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(0.0, 0.0, 1.0, 1.0));
        
        Polynome pnul = new Polynome(); 

        //Multiplication de polynômes avec des monômes de même degré (p2 * p2)
        //(3X^3) * (3X^3) = 9X^6
        Polynome resultat1 = CalculateurPolynome.multiplierPolynomes(p2, p2);
        System.out.println(resultat1);
        assertEquals("9.0X^6", resultat1.toString());
        
        //Multiplication de polynômes avec des monômes de degrés différents (p1 * p2)
        //(3X^2 + 2X^1) * (3X^3) = 9X^5 + 6X^4
        Polynome resultat2 = CalculateurPolynome.multiplierPolynomes(p1, p2);
        assertEquals("9.0X^5 + 6.0X^4", resultat2.toString());
        
        //Multiplication de polynômes avec plusieurs degrés (p1 * p3)
        //(3X^2 + 2X^1) * (1X^3 + 1X^2) = 3X^5 + 3X^4 + 2X^4 + 2X^3 = 3X^5 + 5X^4 + 2X^3
        Polynome resultat3 = CalculateurPolynome.multiplierPolynomes(p1, p3);
        assertEquals("3.0X^5 + 5.0X^4 + 2.0X^3", resultat3.toString());
        
        //Multiplication de polynôme avec un polynôme nul (p1 * pnul)
        //Tout polynôme multiplié par 0 doit renvoyer 0
        Polynome resultat4 = CalculateurPolynome.multiplierPolynomes(p1, pnul);
        assertEquals("0", resultat4.toString());
        
        //Multiplication de deux polynômes nuls (pnul * pnul)
        Polynome resultat5 = CalculateurPolynome.multiplierPolynomes(pnul, pnul);
        assertEquals("0", resultat5.toString());
	}
    
    @Test
    void testTriInsertion() {
		//Polynome vide
		Polynome pVide = new Polynome();
		CalculateurPolynome.trierInsertion(pVide);
		assertEquals(1, pVide.getListeMonome().size());
		assertEquals(0.0, pVide.getListeMonome().get(0).getCoefficient(), 0.001);
		assertEquals(0, pVide.getListeMonome().get(0).getDegre());

		//Polynome simple (1 monome)
		Monome m = new Monome(3.0, 2); 
		Polynome pSimple = new Polynome(new ArrayList<>(List.of(m)));
		CalculateurPolynome.trierInsertion(pSimple);
		assertEquals(1, pSimple.getListeMonome().size());
		assertEquals(m, pSimple.getListeMonome().get(0));

		//Polynome à plusieurs monomes (non triés)
		Monome m1 = new Monome(4.0, 3); 
		Monome m2 = new Monome(-2.0, 1); 
		Monome m3 = new Monome(5.0, 2); 
		Polynome pPlusieurs = new Polynome(new ArrayList<>(List.of(m2, m3, m1)));
		CalculateurPolynome.trierInsertion(pPlusieurs);
		
		List<Monome> listeTriee = pPlusieurs.getListeMonome();
		
		assertEquals(3, listeTriee.size());
		assertEquals(m1, listeTriee.get(0)); // Degré 3
		assertEquals(m3, listeTriee.get(1)); // Degré 2
		assertEquals(m2, listeTriee.get(2)); // Degré 1
	}
    
    @Test
    public void testMultiplierPolynomeAvecScalaire() { 
    	//Test pour un scalaire positif
    	Polynome resultat1 = CalculateurPolynome.multiplierPolynomes(p1, pscalaire1); 
    	Polynome resultatAttendu1 = new Polynome(List.of(new Monome(2.0,1), new Monome(4.0,2)));
    	assertEquals(resultat1,resultatAttendu1);
    	
    	//Test pour un scalaire négatif
    	Polynome resultat2 = CalculateurPolynome.multiplierPolynomes(p1, pscalaire2);
    	Polynome resultatAttendu2 = new Polynome(List.of(new Monome(-5.0,1), new Monome(-10.0,2)));
    	assertEquals(resultat2,resultatAttendu2);
    	
    	//Test pour un scalaire nul
    	Polynome resultat3 = CalculateurPolynome.multiplierPolynomes(p1, pscalaire3);
    	Polynome resultatAttendu3 = new Polynome(List.of(new Monome(0.0,1), new Monome(0.0,2)));
    	assertEquals(resultat3,resultatAttendu3);
    	
    	//Test pour un scalaire de type double 
    	Polynome resultat4 = CalculateurPolynome.multiplierPolynomes(p1, pscalaire4);
    	Polynome resultatAttendu4 = new Polynome(List.of(new Monome(3.5,1), new Monome(7.0,2)));
    	assertEquals(resultat4,resultatAttendu4);
    }
    
}