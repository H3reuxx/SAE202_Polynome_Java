/*
 * CreationPolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Polynome;
import polynome.model.Monome;
import polynome.model.CreationPolynome;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe CalculateurPolynome.
 * 
 * Plan de test : 
 *      test de creation de polynome par coefficients
 *              - Liste de 1 element 
 *              - liste de 10 elements 
 *              - liste vide
 *              - liste avec des coefficients négatif et positif 
 *              - seulement négatif
 *              - seulement positif.
 *      test de creation de polynome par ses racines
 *              - a (coefficient dominant) positif , liste de racines positive et ordre de multiplicité positive 
 *              - a positif , liste de racines positive et négative et ordre de multiplicité positive
 *              - a negatif , liste de racines positive et ordre de multiplicité positive
 *              - a negatif , liste de racines positive et négative et ordre de multiplicité positive 
 *              - a positif liste de racine et de multiplicité vide 
 *              - a positif liste de racine et de multiplicité avec un seul élement.
 * @author Gabriel TOLL , Alban Rozes , Wassim Nafaa , Erwan Nazal
 */

class CreationPolynomeTest {
	
	private ArrayList<Double> coefficients1 , coefficients10 , coefficientsNegatifs , coefficientsPositifs , coefficientsNull;
	
	Double aPos = 10.0;
	Double aNeg = -10.0;
	
	ArrayList<Double> racinesPos , racinesNeg , racinesNull , racines;
	ArrayList<Integer> multiplicites , multiplicitesNeg , multiplicitesNull;
	
	/**
     * Initialise les variables pour reutiliser dans les tests
     */
    @BeforeEach
    void setUp() {
    	coefficients1 = new ArrayList<>(List.of(1.0));
    	coefficients10 = new ArrayList<>(List.of(1.0 , 2.0 , 3.0 , 4.0 , 5.0 , 6.0 , 7.0 , 8.0 , 9.0 , 10.0));
    	coefficientsNegatifs = new ArrayList<>(List.of(-1.0 , -2.0 , -3.0 ));
    	coefficientsPositifs = new ArrayList<>(List.of(1.0 , 2.0 , 3.0 ));
    	coefficientsNull = new ArrayList<>();
    	
    	racinesPos = new ArrayList<>(List.of(1.0 , 2.0));
    	racinesNeg = new ArrayList<>(List.of(-2.0 , -3.0));
    	racines = new ArrayList<>(List.of(1.0 , -2.0));
    	racinesNull = new ArrayList<>();
    	
    	multiplicites = new ArrayList<>(List.of(2 , 1));
    	multiplicitesNull = new ArrayList<>();
    	
	}
    
     
    @Test
    void testCreationParCoefficientsSimple() {
    	//Test de creation de polynome a partir d'une liste de coefficients avec un seul element
    	Polynome p1 = CreationPolynome.CreationPolynomeCoefficients(coefficients1);
    	Polynome p1Attendu = new Polynome(List.of(new Monome(1.0 , 0)));
    	assertEquals(p1, p1Attendu);
    	
    	//Test de creation de polynome a partir d'une liste de coefficients avec 10 elements
    	Polynome p2 = CreationPolynome.CreationPolynomeCoefficients(coefficients10);
    	Polynome p2Attendu = new Polynome(List.of(new Monome(1.0 , 0) , new Monome(2.0 , 1) , new Monome(3.0 , 2) , new Monome(4.0 , 3) , new Monome(5.0 , 4) , new Monome(6.0 , 5) , new Monome(7.0 , 6) , new Monome(8.0 , 7) , new Monome(9.0 , 8) , new Monome(10.0 , 9)));
    	assertEquals(p2 , p2Attendu);
    	
    	//Test de creation de polynome a partir d'une liste de coefficients vide
    	Polynome p3 = CreationPolynome.CreationPolynomeCoefficients(coefficientsNull);
    	assertEquals(p3, new Polynome());
    	
    	//Test de creation de polynome a partir d'une liste de coefficients avec des coefficients négatifs et positifs
    	Polynome p4 = CreationPolynome.CreationPolynomeCoefficients(coefficientsNegatifs);
    	Polynome p4Attendu = new Polynome(List.of(new Monome(-1.0 , 0) , new Monome(-2.0 , 1) , new Monome(-3.0 , 2)));
    	assertEquals(p4, p4Attendu);
    	
    	//Test de creation de polynome a partir d'une liste de coefficients avec seulement des coefficients négatifs
    	Polynome p5 = CreationPolynome.CreationPolynomeCoefficients(coefficientsNegatifs);
    	Polynome p5Attendu = new Polynome(List.of(new Monome(-1.0 , 0) , new Monome(-2.0 , 1) , new Monome(-3.0 , 2)));
    	assertEquals(p5, p5Attendu);
    	
    	//Test de creation de polynome a partir d'une liste de coefficients avec seulement des coefficients positifs
    	Polynome p6 = CreationPolynome.CreationPolynomeCoefficients(coefficientsPositifs);
    	Polynome p6Attendu = new Polynome(List.of(new Monome(1.0 , 0) , new Monome(2.0 , 1) , new Monome(3.0 , 2)));
    	assertEquals(p6, p6Attendu);    	
    }
    
    @Test
    void testCreationParRacines() {
    	//Test de creation de polynome a partir d'une liste de racines positives et d'ordre de multiplicité positifs et a positif
    	Polynome p1 = CreationPolynome.creationPolynomeRacines(racinesPos, multiplicites, aPos);
    	Polynome p1Attendu = new Polynome(List.of(new Monome(-20.0, 0), new Monome(50.0, 1), new Monome(-40.0, 2), new Monome(10.0, 3)));
    	assertEquals(p1, p1Attendu);
    	
    	// Test de creation de polynome a partir d'une liste de racines positives et négatives, de multiplicités positives et a positif
    	Polynome p2 = CreationPolynome.creationPolynomeRacines(racines, multiplicites, aPos);
    	Polynome p2Attendu = new Polynome(List.of(new Monome(20.0, 0), new Monome(-30.0, 1), new Monome(0.0, 2), new Monome(10.0, 3))); // Ajout du 0.0, 2
    	assertEquals(p2Attendu, p2);
    	
    	//Test de creation de polynome a partir d'une liste de racines positives et d'ordre de multiplicité positifs et a negatif
    	Polynome p3 = CreationPolynome.creationPolynomeRacines(racinesPos, multiplicites, aNeg);
    	Polynome p3Attendu = new Polynome(List.of(new Monome(20.0, 0), new Monome(-50.0, 1), new Monome(40.0, 2), new Monome(-10.0, 3)));
    	assertEquals(p3, p3Attendu);
    	
    	//Test de creation de polynome a partir d'une liste de racines positives et négatives et d'ordre de multiplicité positifs et a negatif
    	Polynome p4 = CreationPolynome.creationPolynomeRacines(racines, multiplicites, aNeg);
    	Polynome p4Attendu = new Polynome(List.of(new Monome(-20.0, 0), new Monome(30.0, 1), new Monome(0.0, 2), new Monome(-10.0, 3))); // Ajout du 0.0, 2
    	assertEquals(p4, p4Attendu);	
    	
    	//Test de creation de polynome a partir d'une liste de racines et de multiplicité vide et a positif
    	Polynome p5 = CreationPolynome.creationPolynomeRacines(racinesNull, multiplicitesNull, aPos);
    	Polynome p5Attendu = new Polynome(List.of(new Monome(10.0, 0)));
    	System.out.print(p5.toString() + p5Attendu.toString());
    	assertEquals(p5, p5Attendu);
    	
    	//Test de creation de polynome a partir d'une liste de racines et de multiplicité avec un seul element et a positif
    	Polynome p6 = CreationPolynome.creationPolynomeRacines(List.of(2.0), List.of(3), aPos);
    	Polynome p6Attendu = new Polynome(List.of(new Monome(80.0, 0), new Monome(-120.0, 1), new Monome(60.0, 2), new Monome(-10.0, 3)));
    	assertEquals(p6, p6Attendu);
    	
    }
    
   
}