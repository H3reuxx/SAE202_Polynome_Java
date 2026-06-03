/*
 * MonomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Monome; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

/**
 * Plan de test unitaire pour la classe Monome.
 * plan de test : 
 * 	test de Création :
 * 		- monome totalement nul (0.0X^0).
 * 		- coefficient nul avec degré positif (0.0X^5).
 * 		- coefficient entier. 
 *		- coefficient a virgule. 
 * 		- degrés 0 et dégrés entier.
 * 	test de GetDegre :
 * 		- degres positif.
 * 		- degres nul.
 * 	test de GetCoefficient :
 *		- coefficient entier. 
 * 		- coefficient a virgule. 
 * 		- coefficient nul.
 * 	test de SetCoefficient :
 * 		- modification avec valeur entière. 
 * 		- valeur à virgule. 
 * 		- valeur nulle. 
 * 		- valeur négative.
 * 	test de ToString :
 * 		- monome nul (0.0).
 * 		- coefficient entier. 
 * 		- coefficient a virgule.
 * 		- degrés 0, degré 1 (X) et degrés supérieur (X^3).
 * 		- gestion de la valeur absolue sur coefficient négatif.
 * 	test de Signe (Méthodes statique et dynamique) :
 * 		- valeur positive (+).
 * 		- valeur négative (-).
 * 		- valeur nulle (0).
 * 	test de Equals :
 * 		- égalité nominale et réflexive.
 *		- inégalité sur le coefficient ou le degré.
 * 		- comparaison avec un objet nul (null) ou un type d'objet différent.
 * * * @author Gabriel TOLL , Alban ROZES , Wassim NAFAA , Erwan NAZAL 
 */

public class MonomeTest {
    
    @Test
    @DisplayName("Création : Degrés 0 et coefficients entiers / réels")
    public void testCreationMonomeDegres0() {
       Monome m = new Monome(1.0 , 0);
       assertEquals(1.0 , m.getCoefficient() , 0.001);
       assertEquals(0 , m.getDegre());
       
       Monome m2 = new Monome(10.5 , 0); // Coefficient à virgule
       assertEquals(10.5 , m2.getCoefficient() , 0.001);
       assertEquals(0 , m2.getDegre());
    }
    
    @Test
    @DisplayName("Création : Monômes nuls et degrés normaux")
    void testCreationMonomeCoefNul() {
        Monome m = new Monome(0.0 , 0); // Monôme totalement nul
        assertEquals(0.0 , m.getCoefficient() , 0.001);
        assertEquals(0 , m.getDegre());
        
        Monome m2 = new Monome(0.0 , 5); // Coefficient nul, degré > 0
        assertEquals(0.0 , m2.getCoefficient() , 0.001);
        assertEquals(5 , m2.getDegre());
    }

    @Test
    @DisplayName("Getter : Vérification des degrés (nul et positif)")
    void testGetDegre() {
        Monome mNul = new Monome(2.0, 0);
        assertEquals(0, mNul.getDegre(), "Le degré doit être égal à 0");
        
        Monome mPositif = new Monome(2.0, 4);
        assertEquals(4, mPositif.getDegre(), "Le degré doit être égal à 4");
    }
    
    @Test
    @DisplayName("Getter : Vérification des coefficients (entier, virgule, nul)")
    void testGetCoefficient() {
        Monome mEntier = new Monome(5.0, 1);
        assertEquals(5.0, mEntier.getCoefficient(), 0.001);
        
        Monome mVirgule = new Monome(3.1415, 2);
        assertEquals(3.1415, mVirgule.getCoefficient(), 0.001);
        
        Monome mNul = new Monome(0.0, 3);
        assertEquals(0.0, mNul.getCoefficient(), 0.001);
    }

    @Test
    @DisplayName("Setter : Modification (entier, virgule, nul, négatif)")
    void testSetCoefficient() {
        Monome m = new Monome(1.0, 2);
        m.setCoefficient(2.5); // Valeur à virgule
        assertEquals(2.5, m.getCoefficient(), 0.001);

        Monome m2 = new Monome(0.0, 2);
        m2.setCoefficient(5.0); // Valeur entière
        assertEquals(5.0, m2.getCoefficient(), 0.001);

        Monome m3 = new Monome(-3.0, 2);
        m3.setCoefficient(-10.0); // Valeur négative
        assertEquals(-10.0, m3.getCoefficient(), 0.001);

        Monome m4 = new Monome(1.0, 2);
        m4.setCoefficient(0.0); // Valeur nulle
        assertEquals(0.0, m4.getCoefficient(), 0.001);
    }

    @Test
    @DisplayName("ToString : Conversion textuelle (degrés 0, 1 et supérieur)")
    void testToString() {
        Monome m = new Monome(0.0 , 0);
        assertEquals("0.0", m.toString());
        
        Monome m2 = new Monome(10.0 , 0);
        assertEquals("10.0", m2.toString());
        
        Monome m3 = new Monome(1.0 , 1);
        assertEquals("1.0X", m3.toString());
        
        Monome m4 = new Monome(2.5 , 3);
        assertEquals("2.5X^3", m4.toString());
        
        Monome mNegatif = new Monome(-4.0, 2);
        assertEquals("4.0X^2", mNegatif.toString());
    }

    @Test
    @DisplayName("Bonus : Test des méthodes de signes (statique et dynamique)")
    void testSignes() {
        // Test de la méthode signe(double)
        assertEquals("+", Monome.signe(5.5));
        assertEquals("-", Monome.signe(-3.2));
        assertEquals("0", Monome.signe(0.0));
        
        // Test de la méthode d'instance getSigne()
        Monome mPos = new Monome(4.0, 1);
        Monome mNeg = new Monome(-2.5, 2);
        Monome mNul = new Monome(0.0, 0);
        
        assertEquals("+", mPos.getSigne());
        assertEquals("-", mNeg.getSigne());
        assertEquals("0", mNul.getSigne());
    }

    @Test
    @DisplayName("Bonus : Test de la méthode equals()")
    void testEquals() {
        Monome m1 = new Monome(2.0, 3);
        Monome m2 = new Monome(2.0, 3);
        Monome m3 = new Monome(4.0, 3); // Coefficient différent
        Monome m4 = new Monome(2.0, 5); // Degré différent
        
        assertTrue(m1.equals(m2));
        assertTrue(m1.equals(m1));
        
        // Cas d'inégalité
        assertFalse(m1.equals(m3));
        assertFalse(m1.equals(m4));
        assertFalse(m1.equals(null)); // Avec null
        assertFalse(m1.equals("Goo Goo Ga Ga")); // Avec un autre type d'objet
    }
}
