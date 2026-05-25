/*
 * MonomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Monome; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe Monome.
 * 
 * plan de test : 
 * 	test de Création :
 * 		- monome nul.
 * 		- coefficient entier. 
 *      - coefficient a virgule. 
 *	    - degrés 0 et dégrés entier.
 * 	test de GetDegres :
 * 		- degres positif.
 * 		- degres nul.
 * 	test de GetCoefficient :
 *		- coefficient entier. 
 *      - coefficient a virgule. 
 *      - coefficient nul.
 *  test de ToString :
 *  	- monome nul.
 *  	- coefficient entier. 
 *  	- coefficient a virgule.
 *      - dégrés normal.
 * 	test de SetCoefficient :
 *      - modification avec valeur entière. 
 *      - valeur à virgule. 
 *      - valeur nulle. 
 *      - valeur négative.
 * 
 *  * @author Gabriel TOLL , Alban ROZES , Wassim NAFAA , Erwan NAZAL 
 */

public class MonomeTest {
	
    @Test
    public void testCreationMonomeDegres0() {
       Monome m = new Monome(1.0 , 0);
       assertEquals(1.0 , m.getCoefficient() , 0.001);
       assertEquals(0 , m.getDegre());
       
       Monome m2 = new Monome(10.0 , 0);
       assertEquals(10.0 , m2.getCoefficient() , 0.001);
       assertEquals(0 , m2.getDegre());
    }
    
    @Test
    void testCreationMonomeCoefNul() {
        Monome m = new Monome(0.0 , 0);
        assertEquals(0.0 , m.getCoefficient() , 0.001);
        assertEquals(0 , m.getDegre());
        
        Monome m2 = new Monome(0.0 , 1);
        assertEquals(0.0 , m2.getCoefficient() , 0.001);
        assertEquals(1 , m2.getDegre());
    }

    @Test
    void testSetCoefficient() {
        Monome m = new Monome(1.0, 2);
        m.setCoefficient(2.5);
        assertEquals(2.5, m.getCoefficient(), 0.001);

        Monome m2 = new Monome(0.0, 2);
        m2.setCoefficient(5.0);
        assertEquals(5.0, m2.getCoefficient(), 0.001);

        Monome m3 = new Monome(-3.0, 2);
        m3.setCoefficient(-10.0);
        assertEquals(-10.0, m3.getCoefficient(), 0.001);

        Monome m4 = new Monome(1.0, 2);
        m4.setCoefficient(0.0);
        assertEquals(0.0, m4.getCoefficient(), 0.001);
    }
    

    @Test
    void testToString() {
        Monome m = new Monome(0.0 , 0);
        assertEquals(m.toString() , "0.0");
        
        Monome m2 = new Monome(10.0 , 0);
        assertEquals(m2.toString() , "10.0");
        
        Monome m3 = new Monome(1.0 , 1);
        assertEquals(m3.toString() , "1.0X");
        
        Monome m4 = new Monome(2.5 , 3);
        assertEquals(m4.toString() , "2.5X^3");
    }
}

