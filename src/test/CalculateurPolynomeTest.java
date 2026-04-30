/*
 * CalculateurPolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;
import polynome.calculs.CalculateurPolynome;
import org.junit.jupiter.api.Test;
import polynome.model.Monome;
import polynome.model.Polynome;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe CalculateurPolynome.
 * 
 * Ce plan de test vérifie la justesse des opérations algébriques sur les polynômes.
 * L'accent est mis sur la manipulation correcte des degrés et la réduction des 
 * coefficients lors des calculs.
 * 
 * Les axes de test sont les suivants :
 * <ul>
 *     <li><b>Algèbre d'addition :</b> Vérification du regroupement des monômes 
 *     de même degré et de la conservation des degrés distincts.</li>
 *     <li><b>Algèbre de multiplication :</b> Validation de la distribution des 
 *     produits et de la règle de somme des degrés ($x^a \cdot x^b = x^{a+b}$).</li>
 *     <li><b>Éléments neutres et nuls :</b> Test des opérations avec des polynômes 
 *     vides (représentant le zéro mathématique).</li>
 *     <li><b>Signes et annulations :</b> Vérification que l'addition de monômes 
 *     opposés produit bien un coefficient nul.</li>
 * </ul>
 * 
 * @author
 */


class CalculateurPolynomeTest {

    // ------------------------------------------------------------------ utilitaire
    /** Cherche le coefficient du monome de degre donné, -1 si absent. */
    private double coefPourDegre(Polynome p, int degre) {
        return p.getListeMonome().stream()
                .filter(m -> m.getDegre() == degre)
                .mapToDouble(Monome::getCoefficient)
                .findFirst()
                .orElse(Double.NaN);
    }

    // ================================================================ addition
    @Test
    void testAdditionMemesDegres() {
        // P1 = 2x^1  ,  P2 = 3x^1  =>  résultat = 5x^1
        Polynome p1 = new Polynome(); p1.ajouterMonome(new Monome(2.0, 1));
        Polynome p2 = new Polynome(); p2.ajouterMonome(new Monome(3.0, 1));

        Polynome result = CalculateurPolynome.additionnerPolynome(p1, p2);

        assertEquals(5.0, coefPourDegre(result, 1), 1e-9);
        assertEquals(1, result.getListeMonome().size());
    }

    @Test
    void testAdditionDegresDifferents() {
        // P1 = 2x^0  ,  P2 = 3x^1  =>  résultat = 2x^0 + 3x^1
        Polynome p1 = new Polynome(); p1.ajouterMonome(new Monome(2.0, 0));
        Polynome p2 = new Polynome(); p2.ajouterMonome(new Monome(3.0, 1));

        Polynome result = CalculateurPolynome.additionnerPolynome(p1, p2);

        assertEquals(2, result.getListeMonome().size());
        assertEquals(2.0, coefPourDegre(result, 0), 1e-9);
        assertEquals(3.0, coefPourDegre(result, 1), 1e-9);
    }

    @Test
    void testAdditionPolynomesComplets() {
        // P1 = 1 + 2x  ,  P2 = 3 + 4x  =>  4 + 6x
        Polynome p1 = new Polynome();
        p1.ajouterMonome(new Monome(1.0, 0));
        p1.ajouterMonome(new Monome(2.0, 1));

        Polynome p2 = new Polynome();
        p2.ajouterMonome(new Monome(3.0, 0));
        p2.ajouterMonome(new Monome(4.0, 1));

        Polynome result = CalculateurPolynome.additionnerPolynome(p1, p2);

        assertEquals(4.0, coefPourDegre(result, 0), 1e-9);
        assertEquals(6.0, coefPourDegre(result, 1), 1e-9);
    }

    @Test
    void testAdditionAvecCoefficientNegatif() {
        // P1 = 5x^2  ,  P2 = -5x^2  =>  0x^2
        Polynome p1 = new Polynome(); p1.ajouterMonome(new Monome( 5.0, 2));
        Polynome p2 = new Polynome(); p2.ajouterMonome(new Monome(-5.0, 2));

        Polynome result = CalculateurPolynome.additionnerPolynome(p1, p2);

        assertEquals(0.0, coefPourDegre(result, 2), 1e-9);
    }

    @Test
    void testAdditionP2Vide() {
        // P1 = 3x^1  ,  P2 = vide  =>  3x^1
        Polynome p1 = new Polynome(); p1.ajouterMonome(new Monome(3.0, 1));
        Polynome p2 = new Polynome();

        Polynome result = CalculateurPolynome.additionnerPolynome(p1, p2);

        assertEquals(1, result.getListeMonome().size());
        assertEquals(3.0, coefPourDegre(result, 1), 1e-9);
    }

    // ================================================================ multiplication
    @Test
    void testMultiplicationSimple() {
        // (2x) * (3x) = 6x^2
        Polynome p1 = new Polynome(); p1.ajouterMonome(new Monome(2.0, 1));
        Polynome p2 = new Polynome(); p2.ajouterMonome(new Monome(3.0, 1));

        Polynome result = CalculateurPolynome.multiplierPolynomes(p1, p2);

        assertEquals(6.0, coefPourDegre(result, 2), 1e-9);
        assertEquals(1, result.getListeMonome().size());
    }

    @Test
    void testMultiplicationParConstante() {
        // (2 + x) * 3 = 6 + 3x
        Polynome p1 = new Polynome();
        p1.ajouterMonome(new Monome(2.0, 0));
        p1.ajouterMonome(new Monome(1.0, 1));

        Polynome p2 = new Polynome(); p2.ajouterMonome(new Monome(3.0, 0));

        Polynome result = CalculateurPolynome.multiplierPolynomes(p1, p2);

        assertEquals(6.0, coefPourDegre(result, 0), 1e-9);
        assertEquals(3.0, coefPourDegre(result, 1), 1e-9);
    }

    @Test
    void testMultiplicationBinomes() {
        // (x + 1) * (x - 1) = x^2 - 1
        Polynome p1 = new Polynome();
        p1.ajouterMonome(new Monome( 1.0, 1));
        p1.ajouterMonome(new Monome( 1.0, 0));

        Polynome p2 = new Polynome();
        p2.ajouterMonome(new Monome( 1.0, 1));
        p2.ajouterMonome(new Monome(-1.0, 0));

        Polynome result = CalculateurPolynome.multiplierPolynomes(p1, p2);

        assertEquals(-1.0, coefPourDegre(result, 0), 1e-9);
        assertEquals( 0.0, coefPourDegre(result, 1), 1e-9);
        assertEquals( 1.0, coefPourDegre(result, 2), 1e-9);
    }

    @Test
    void testMultiplicationParPolynomeVide() {
        // (2 + x) * vide = vide (liste vide)
        Polynome p1 = new Polynome();
        p1.ajouterMonome(new Monome(2.0, 0));
        p1.ajouterMonome(new Monome(1.0, 1));

        Polynome p2 = new Polynome();

        Polynome result = CalculateurPolynome.multiplierPolynomes(p1, p2);

        assertTrue(result.getListeMonome().isEmpty());
    }

    @Test
    void testMultiplicationDegresCorrects() {
        // (x^2) * (x^3) = x^5
        Polynome p1 = new Polynome(); p1.ajouterMonome(new Monome(1.0, 2));
        Polynome p2 = new Polynome(); p2.ajouterMonome(new Monome(1.0, 3));

        Polynome result = CalculateurPolynome.multiplierPolynomes(p1, p2);

        assertEquals(1.0, coefPourDegre(result, 5), 1e-9);
    }
}