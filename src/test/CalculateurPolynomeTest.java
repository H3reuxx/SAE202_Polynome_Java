/*
 * CalculateurPolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;
import polynome.calculs.CalculateurPolynome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import polynome.model.Monome;
import polynome.model.Polynome;
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
 */


class CalculateurPolynomeTest {
	
	// Declaration des variables pour les tests
    private Monome m1, m2, m3, mnull;
    private Polynome p1, p2, p3, pnul;

    
    /**
     * Initialise les variables pour reutiliser dans les tests
     */
    @BeforeEach
    public void setUp() {
        m1 = new Monome(1.0 , 1);
        m2 = new Monome(2.0 , 2);
        m3 = new Monome(3.0 , 3);
        mnull = new Monome(0.0 , 0);
		
        p1 = new Polynome(List.of(m1 , m2));
        p2 = new Polynome(List.of(m3));
        p3 = new Polynome(List.of(m2 , m3));
        pnul = new Polynome(List.of(mnull));
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
    	
    	//Multiplication de polynome avec des monomes de même degre
    	Polynome resultat1 = CalculateurPolynome.multiplierPolynomes(p2, p2);
    	Polynome resultatAttendu1 = new Polynome(List.of(new Monome(9.0, 6)));
		assertEquals(resultat1, resultatAttendu1);
		
		//Multiplication de polynome avec des monomes de degre différent
		Polynome resultat2 = CalculateurPolynome.multiplierPolynomes(p1, p2);
		Polynome resultatAttendu2 = new Polynome(List.of(new Monome(3.0 , 4) , new Monome(6.0 , 5)));
		assertEquals(resultat2, resultatAttendu2);
		
		//Multiplication de polynome avec des monomes de même degre et de degre différent
		Polynome resultat3 = CalculateurPolynome.multiplierPolynomes(p1, p3);
		Polynome resultatAttendu3 = new Polynome(List.of(new Monome(6.0 , 4) , new Monome(9.0 , 5) , new Monome(2.0 , 5) , new Monome(3.0 , 6)));
		assertEquals(resultat3, resultatAttendu3);
		
		//Multiplication de polynome avec des monomes de même degre et de degre différent et des monomes nuls
		Polynome resultat4 = CalculateurPolynome.multiplierPolynomes(p1, pnul);
		Polynome resultatAttendu4 = new Polynome(List.of(new Monome(0.0 , 1) , new Monome(0.0 , 2)));
		assertEquals(resultat4, resultatAttendu4);
		
		//Multiplication de deux polynomes nul
		Polynome resultat5 = CalculateurPolynome.multiplierPolynomes(pnul, pnul);
		assertEquals(resultat5, pnul);
	}
}