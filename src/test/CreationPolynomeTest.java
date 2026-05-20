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
 *              - a positif , liste de racines positive et négative et ordre de multiplicité positive et négative
 *              - a negatif , liste de racines positive et ordre de multiplicité positive
 *              - a negatif , liste de racines positive et négative et ordre de multiplicité positive et négative
 *              - a positif liste de racine et de multiplicité vide 
 *              - a positif liste de racine et de multiplicité avec un seul élement.
 * @author Gabriel TOLL , Alban Rozes , Wassim Nafaa , Erwan Nazal
 */

class CreationPolynomeTest {
	
	private ArrayList<Double> coefficients1 , coefficients10 , coefficientsNegatifs , coefficientsPositifs , coefficientsNuls;
	
	Double aPos = 10.0;
	Double aNeg = -10.0;
	
	ArrayList<Double> racinesPos , racinesNeg , racinesNull;
	ArrayList<Integer> multiplicitesPos , multiplicitesNeg , multiplicitesNull;
	
	/**
     * Initialise les variables pour reutiliser dans les tests
     */
    @BeforeEach
    void setUp() {
    	ArrayList<Double> coefficients1 = new ArrayList<>(List.of(1.0));
    	ArrayList<Double> coefficients10 = new ArrayList<>(List.of(1.0 , 2.0 , 3.0 , 4.0 , 5.0 , 6.0 , 7.0 , 8.0 , 9.0 , 10.0));
    	ArrayList<Double> coefficientsNegatifs = new ArrayList<>(List.of(-1.0 , -2.0 , -3.0 ));
    	ArrayList<Double> coefficientsPositifs = new ArrayList<>(List.of(1.0 , 2.0 , 3.0 ));
    	ArrayList<Double> coefficientsNull = new ArrayList<>();
    	
    	ArrayList<Double> racinesPos = new ArrayList<>(List.of(1.0 , 2.0 , 3.0));
    	ArrayList<Double> racinesNeg = new ArrayList<>(List.of(-1.0 , -2.0 , -3.0));
    	ArrayList<Double> racinesNull = new ArrayList<>();
    	
    	ArrayList<Integer> multiplicitesPos = new ArrayList<>(List.of(2 , 1 , 5));
    	ArrayList<Integer> multiplicitesNeg = new ArrayList<>(List.of(-2 , -1 , -32));
    	ArrayList<Integer> multiplicitesNull = new ArrayList<>();
    	
	}
    
     
    @Test
    void testCreationParCoefficientsSimple() {
    	//Test de creation de polynome a partir d'une liste de coefficients avec un seul element
    	Polynome p1 = CreationPolynome.CreationPolynomeCoefficients(coefficients1);
    	Polynome p1Attendu = new Polynome(List.of(new Monome(1.0 , 0)));
    	assertEquals(p1, p1Attendu);
    	
    	//Test de creation de polynome a partir d'une liste de coefficients avec 10 elements
    	Polynome p2 = CreationPolynome.CreationPolynomeCoefficients(coefficients10);
    	assertEquals(p2, coefficients10);
    	
    	//Test de creation de polynome a partir d'une liste de coefficients vide
    	Polynome p3 = CreationPolynome.CreationPolynomeCoefficients(coefficientsNuls);
    	assertEquals(p3, new Polynome());
    	
    	//Test de creation de polynome a partir d'une liste de coefficients avec des coefficients négatifs et positifs
    	Polynome p4 = CreationPolynome.CreationPolynomeCoefficients(coefficientsNegatifs);
    	Polynome p4Attendu = new Polynome(List.of(new Monome(-1.0 , 0) , new Monome(-2.0 , 1) , new Monome(-3.0 , 2)));
    	assertEquals(p4, p4Attendu);
    }

   
}