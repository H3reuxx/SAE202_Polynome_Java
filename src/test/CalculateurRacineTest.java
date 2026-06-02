package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import polynome.model.Polynome;
import polynome.model.CreationPolynome;
import polynome.calculs.CalculateurRacines;

/**
 * Plan de tests Unitaires pour CalculateurRacines (Suite de Sturm) :
 * - Borne invalide : borneMax < borneMin (doit lever IllegalArgumentException)
 * - Polynôme nul (doit renvoyer 0 ou être rejeté)
 * - Polynôme constant non nul ex: P(x) = 5 (doit renvoyer 0)
 * - Borne minimale ou maximale égale à 0 ou 1
 * - Bornes entièrement négatives (ex: [-5, -2])
 * - Racine positionnée exactement sur une borne (ex: P(x)=x^2-1 sur [0, 1])
 * - Polynôme de degré 1 (ex: 2x - 4)
 * - Polynôme n'ayant aucune racine réelle dans l'intervalle (ex: x^2 + 1)
 * - Polynôme avec racines distinctes (ex: (x-1)(x-2)(x-3))
 * - Polynôme avec racines multiples (ex: (x-2)^3 -> vérification du compte unique)
 * - Polynôme de haut degré (long) pour tester la performance/stabilité
 * - Coefficients entiers (sans virgule)
 * - Coefficients réels (doubles à virgule, ex: 0.25x^2 - 0.5x)
 */
public class CalculateurRacineTest {

    @Test
    void testBorneInvalideMaxInferieurMin() {
        // x^2 - 1
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 0.0, 1.0));
        
        // L'exception doit être levée car borneMax (-10) < borneMin (10)
        assertThrows(IllegalArgumentException.class, () -> {
            CalculateurRacines.suiteSturm(-10.0, 10.0, p);
        });
    }

    @Test
    void testPolynomeNul() {
        // P(x) = 0
        Polynome pNul = CreationPolynome.CreationPolynomeCoefficients(Collections.singletonList(0.0));
        
        // Le polynôme nul ne possède pas de racine dénombrable, on attend 0
        assertEquals(0, CalculateurRacines.suiteSturm(5.0, -5.0, pNul));
    }

    @Test
    void testPolynomeConstantNonNul() {
        // P(x) = 5.0 (Une ligne horizontale qui ne croise jamais l'axe X)
        Polynome pConstant = CreationPolynome.CreationPolynomeCoefficients(Collections.singletonList(5.0));
        
        assertEquals(0, CalculateurRacines.suiteSturm(10.0, -10.0, pConstant));
    }

    @Test
    void testBorneEgaleAZero() {
        // x^2 - 1 -> racines en -1 et 1
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 0.0, 1.0));
        
        // Sur [0, 2], seule la racine 1 est présente -> On attend 1 racine
        assertEquals(1, CalculateurRacines.suiteSturm(2.0, 0.0, p));
    }

    @Test
    void testBornesEntierementNegatives() {
        // x^2 - 1 -> racines en -1 et 1
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 0.0, 1.0));
        
        // Sur l'intervalle [-5, -0.5], seule la racine -1 est présente -> On attend 1 racine
        assertEquals(1, CalculateurRacines.suiteSturm(-0.5, -5.0, p));
    }

    @Test
    void testRacineExactementSurLaBorne() {
        // x^2 - 1 -> racines en -1 et 1
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 0.0, 1.0));
        
        // Sur [0, 1], la racine est pile sur la borne Max. 
        // Selon l'implémentation de Sturm, elle est comptée.
        assertEquals(1, CalculateurRacines.suiteSturm(1.0, 0.0, p));
    }

    @Test
    void testPolynomeDegreUn() {
        // P(x) = 2x - 4 -> unique racine en x = 2
        Polynome pDeg1 = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-4.0, 2.0));
        
        // Entre 0 et 5, il y a bien la racine 2
        assertEquals(1, CalculateurRacines.suiteSturm(5.0, 0.0, pDeg1));
    }

    @Test
    void testPolynomeSansAucuneRacineReelle() {
        // P(x) = x^2 + 1 -> Ne traverse jamais l'axe des abscisses
        Polynome pSansRacine = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(1.0, 0.0, 1.0));
        
        assertEquals(0, CalculateurRacines.suiteSturm(10.0, -10.0, pSansRacine));
    }

    @Test
    void testPolynomeRacinesDistinctes() {
        // P(x) = (x-1)(x-2)(x-3) -> 3 racines distinctes : 1, 2, et 3
        Polynome pDistinctes = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(1.0, 2.0, 3.0), 
            Arrays.asList(1, 1, 1), 
            1.0
        );
        
        // L'intervalle [0, 4] englobe 1, 2 et 3 -> On attend 3 racines
        assertEquals(3, CalculateurRacines.suiteSturm(4.0, 0.0, pDistinctes));
    }

    @Test
    void testPolynomeRacinesMultiples() {
        // P(x) = (x-2)^3 -> x=2 est une racine triple.
        // Sturm ne compte que les racines *distinctes* (sans la multiplicité).
        Polynome pMultiple = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(2.0), 
            Arrays.asList(3), 
            1.0
        );
        
        // On attend 1 seule racine distincte dans l'intervalle [0, 5]
        assertEquals(1, CalculateurRacines.suiteSturm(5.0, 0.0, pMultiple));
    }

    @Test
    void testPolynomeHautDegre() {
        // Polynôme avec 6 racines distinctes : {1, 2, 3, 4, 5, 6}
        Polynome pLong = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0),
            Arrays.asList(1, 1, 1, 1, 1, 1),
            1.0
        );
        
        assertEquals(6, CalculateurRacines.suiteSturm(10.0, 0.0, pLong));
    }

    @Test
    void testCoefficientsEntiers() {
        // P(x) = 4 - x^2 -> racines en -2 et 2
        Polynome pEntier = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(4.0, 0.0, -1.0));
        
        assertEquals(2, CalculateurRacines.suiteSturm(3.0, -3.0, pEntier));
    }

    @Test
    void testCoefficientsAFloatsEtVirgules() {
        // P(x) = 0.25x^2 - 0.5x - 0.75 -> racines en -1.0 et 3.0
        Polynome pVirgule = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-0.75, -0.5, 0.25));
        
        assertEquals(2, CalculateurRacines.suiteSturm(5.0, -2.0, pVirgule));
    }
}