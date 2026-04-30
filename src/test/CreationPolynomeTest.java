/*
 * CreationPolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Polynome;
import polynome.model.Monome;
import polynome.model.CreationPolynome;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe CreationPolynome.
 * 
 * Ce plan de test vérifie les différentes méthodes de "fabrique" permettant de 
 * générer des polynômes à partir de données sources variées. 
 * 
 * Les axes de test sont les suivants :
 * <ul>
 *     <li><b>Génération par coefficients :</b> Vérification de la conversion 
 *     d'une liste de réels en une suite de monômes dont les degrés correspondent 
 *     aux indices de la liste.</li>
 *     <li><b>Génération par racines :</b> Validation de la reconstruction 
 *     algébrique d'un polynôme à partir de ses racines et de leurs multiplicités 
 *     respectives (forme factorisée).</li>
 *     <li><b>Fiabilité et robustesse :</b> Test des cas d'erreur tels que les 
 *     listes nulles, vides ou de tailles incohérentes pour s'assurer que le 
 *     système ne produit pas de résultats erronés.</li>
 *     <li><b>Cas particuliers :</b> Prise en compte du coefficient dominant 
 *     et des coefficients nuls au sein d'une séquence.</li>
 * </ul>
 * 
 * @author 
 */

class CreationPolynomeTest {

    // utilitaire
    private double coefPourDegre(Polynome p, int degre) {
        return p.getListeMonome().stream()
                .filter(m -> m.getDegre() == degre)
                .mapToDouble(Monome::getCoefficient)
                .findFirst()
                .orElse(Double.NaN);
    }

    // ============================================ CreationPolynomeCoefficients
    @Test
    void testCreationParCoefficientsSimple() {
        // coefficients = [1.0, 2.0, 3.0]  =>  1 + 2x + 3x^2
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(List.of(1.0, 2.0, 3.0));

        assertEquals(3, p.getListeMonome().size());
        assertEquals(1.0, coefPourDegre(p, 0), 1e-9);
        assertEquals(2.0, coefPourDegre(p, 1), 1e-9);
        assertEquals(3.0, coefPourDegre(p, 2), 1e-9);
    }

    @Test
    void testCreationParCoefficientsUnSeul() {
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(List.of(7.0));
        assertEquals(1, p.getListeMonome().size());
        assertEquals(7.0, coefPourDegre(p, 0), 1e-9);
    }

    @Test
    void testCreationParCoefficientsListeVide() {
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(List.of());
        assertTrue(p.getListeMonome().isEmpty());
    }

    @Test
    void testCreationParCoefficientsAvecZero() {
        // [2.0, 0.0, 5.0]  =>  2 + 0x + 5x^2
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(List.of(2.0, 0.0, 5.0));
        assertEquals(0.0, coefPourDegre(p, 1), 1e-9);
        assertEquals(5.0, coefPourDegre(p, 2), 1e-9);
    }

    // ============================================ creationPolynomeRacines
    @Test
    void testCreationRacineUniqueSimple() {
        // a=1, racine=2, multiplicité=1  =>  (x-2) = -2 + x
        Polynome p = CreationPolynome.creationPolynomeRacines(List.of(2.0), List.of(1), 1.0);

        assertEquals(-2.0, coefPourDegre(p, 0), 1e-9);
        assertEquals( 1.0, coefPourDegre(p, 1), 1e-9);
    }

    @Test
    void testCreationRacineAvecCoefDominant() {
        // a=3, racine=1, multiplicité=1  =>  3*(x-1) = -3 + 3x
        Polynome p = CreationPolynome.creationPolynomeRacines(List.of(1.0), List.of(1), 3.0);

        assertEquals(-3.0, coefPourDegre(p, 0), 1e-9);
        assertEquals( 3.0, coefPourDegre(p, 1), 1e-9);
    }

    @Test
    void testCreationDeuxRacines() {
        // a=1, racines={1, -1}, mult={1,1}  =>  (x-1)(x+1) = x^2 - 1
        Polynome p = CreationPolynome.creationPolynomeRacines(List.of(1.0, -1.0), List.of(1, 1), 1.0);

        assertEquals(-1.0, coefPourDegre(p, 0), 1e-9);
        assertEquals( 0.0, coefPourDegre(p, 1), 1e-9);
        assertEquals( 1.0, coefPourDegre(p, 2), 1e-9);
    }

    @Test
    void testCreationRacineAvecMultipliciteDeux() {
        // a=1, racine=3, multiplicité=2  =>  (x-3)^2 = 9 - 6x + x^2
        Polynome p = CreationPolynome.creationPolynomeRacines(List.of(3.0), List.of(2), 1.0);

        assertEquals( 9.0, coefPourDegre(p, 0), 1e-9);
        assertEquals(-6.0, coefPourDegre(p, 1), 1e-9);
        assertEquals( 1.0, coefPourDegre(p, 2), 1e-9);
    }

    @Test
    void testCreationRacinesNull() {
        assertNull(CreationPolynome.creationPolynomeRacines(null, List.of(1), 1.0));
    }

    @Test
    void testCreationMultiplicitesNull() {
        assertNull(CreationPolynome.creationPolynomeRacines(List.of(1.0), null, 1.0));
    }

    @Test
    void testCreationTaillesDifferentes() {
        // racines et multiplicités de tailles différentes => null
        assertNull(CreationPolynome.creationPolynomeRacines(List.of(1.0, 2.0), List.of(1), 1.0));
    }

    @Test
    void testCreationListesVides() {
        assertNull(CreationPolynome.creationPolynomeRacines(List.of(), List.of(), 1.0));
    }
}