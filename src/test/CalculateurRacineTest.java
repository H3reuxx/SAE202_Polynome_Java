package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import polynome.model.Polynome;
import polynome.model.CreationPolynome;
import polynome.calculs.CalculateurRacines;

/**
 * classe de test pour la classe CalculateurRacines
 * plan de test Unitaires :
 * Test suite de Sturm :
 * 		-borne invalide : borneMax < borneMin
 * 		-borneMax ou borneMin = 0
 * 		-borneMax ou borneMin = 1
 * 		-Polynome nul
 * 		-Polynome de degré 1
 *		-Polynome avec des racines distinctes
 * 		-Polynome avec des racines multiples (doubles/triples)
 * 		-Polynome avec coefficients sans virgule (entiers)
 * 		-Polynome avec coefficients à virgule (doubles)
 * 		-Polynome long (haut degré)
 */
public class CalculateurRacineTest {

    @Test
    void TestSuiteSturm() {
        // --- 1. Borne invalide : borneMax < borneMin ---
        Polynome pBase = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 0.0, 1.0)); // x^2 - 1
        assertThrows(IllegalArgumentException.class, () -> {
            CalculateurRacines.suiteSturm(-10.0, 10.0, pBase);
        });

        // --- 2. borneMax ou borneMin = 0 ---
        // x^2 - 1 sur [0, 2] -> 1 racine (x=1)
        assertEquals(1, CalculateurRacines.suiteSturm(2.0, 0.0, pBase));

        // --- 3. borneMax ou borneMin = 1 ---
        // Test aux limites exactes
        assertDoesNotThrow(() -> CalculateurRacines.suiteSturm(1.0, -1.0, pBase));

        // --- 4. Polynome nul ---
        Polynome pNul = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(0.0));
        assertDoesNotThrow(() -> CalculateurRacines.suiteSturm(5.0, -5.0, pNul));

        // --- 5. Polynome de degré 1 ---
        // 2x - 4 -> racine en x=2
        Polynome pDeg1 = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-4.0, 2.0));
        assertEquals(1, CalculateurRacines.suiteSturm(5.0, 0.0, pDeg1));

        // --- 6. Polynome avec des racines distinctes ---
        // (x-1)(x-2)(x-3)
        Polynome pDistinctes = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(1.0, 2.0, 3.0), 
            Arrays.asList(1, 1, 1), 
            1.0
        );
        assertEquals(3, CalculateurRacines.suiteSturm(4.0, 0.0, pDistinctes));

        // --- 7. Polynome avec des racines multiples (doubles/triples) ---
        // (x-2)^3 -> 1 seule racine réelle distincte
        Polynome pMultiple = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(2.0), 
            Arrays.asList(3), 
            1.0
        );
        assertEquals(1, CalculateurRacines.suiteSturm(5.0, 0.0, pMultiple));

        // --- 8. Polynome avec coefficients sans virgule (entiers) ---
        Polynome pEntier = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(4.0, 0.0, -1.0)); // 4 - x^2
        assertEquals(2, CalculateurRacines.suiteSturm(3.0, -3.0, pEntier));

        // --- 9. Polynome avec coefficients à virgule (doubles) ---
        // 0.25x^2 - 0.5x - 0.75
        Polynome pVirgule = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-0.75, -0.5, 0.25));
        assertEquals(2, CalculateurRacines.suiteSturm(5.0, -2.0, pVirgule));

        // --- 10. Polynome long (haut degré) ---
        // Polynôme avec 6 racines distinctes : {1, 2, 3, 4, 5, 6}
        Polynome pLong = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0),
            Arrays.asList(1, 1, 1, 1, 1, 1),
            1.0
        );
        assertEquals(6, CalculateurRacines.suiteSturm(10.0, 0.0, pLong));
    }
}