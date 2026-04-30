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
 * Ce plan de test suit une stratégie de test "boîte noire" pour vérifier
 * que l'état interne d'un monôme est cohérent avec les paramètres fournis
 * lors de sa construction.
 * 
 * Les axes de test sont les suivants :
 * <ul>
 *     <li><b>Cas nominaux :</b> Vérification de la bonne mémorisation des 
 *     coefficients (réels) et des degrés (entiers).</li>
 *     <li><b>Cas aux limites :</b> Validation du comportement avec un degré nul 
 *     (représentant une constante) ou un coefficient nul.</li>
 *     <li><b>Cas particuliers :</b> Vérification de la gestion des signes, 
 *     notamment pour les coefficients négatifs.</li>
 *     <li><b>Représentation :</b> Conformité de l'affichage textuel pour 
 *     garantir l'interopérabilité avec les autres classes du modèle.</li>
 * </ul>
 * 
 * @author 
 */
class MonomeTest {

    @Test
    void testGetDegre() {
        Monome m = new Monome(3.0, 2);
        assertEquals(2, m.getDegre());
    }

    @Test
    void testGetCoefficient() {
        Monome m = new Monome(3.0, 2);
        assertEquals(3.0, m.getCoefficient(), 1e-9);
    }

    @Test
    void testDegreZero() {
        Monome m = new Monome(5.0, 0);
        assertEquals(0, m.getDegre());
        assertEquals(5.0, m.getCoefficient(), 1e-9);
    }

    @Test
    void testCoefficientNegatif() {
        Monome m = new Monome(-7.5, 3);
        assertEquals(-7.5, m.getCoefficient(), 1e-9);
        assertEquals(3, m.getDegre());
    }

    @Test
    void testCoefficientZero() {
        Monome m = new Monome(0.0, 4);
        assertEquals(0.0, m.getCoefficient(), 1e-9);
    }

    @Test
    void testToString() {
        Monome m = new Monome(2.0, 3);
        assertEquals("2.0X^3", m.toString());
    }
}

