package test;

import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import polynome.model.Polynome;
import polynome.model.CreationPolynome;
import polynome.calculs.CalculateurRacines;
import java.util.List;
import org.junit.jupiter.api.DisplayName;



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



class CalculateurRacineTest {

    @Test
    @DisplayName("Borne invalide : borneMax < borneMin (doit lever IllegalArgumentException)")
    void testBorneInvalide() {
        // P(x) = x
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(0.0, 1.0));
        
        assertThrows(IllegalArgumentException.class, () -> {
            CalculateurRacines.suiteSturm(1.0, 5.0, p); // borneMax (1.0) < borneMin (5.0)
        });
    }

    @Test
    @DisplayName("Polynôme nul (doit renvoyer 0)")
    void testPolynomeNul() {
        // P(x) = 0
        Polynome p = new Polynome(); // Le constructeur par défaut initialise à 0.0
        
        int nbRacines = CalculateurRacines.suiteSturm(5.0, -5.0, p);
        assertEquals(0, nbRacines, "Un polynôme nul doit renvoyer 0 racine.");
    }

    @Test
    @DisplayName("Polynôme constant non nul ex: P(x) = 5 (doit renvoyer 0)")
    void testPolynomeConstantNonNul() {
        // P(x) = 5.0 (une liste avec un seul coefficient pour le degré 0)
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(5.0));
        
        int nbRacines = CalculateurRacines.suiteSturm(10.0, -10.0, p);
        assertEquals(0, nbRacines, "Un polynôme constant n'a pas de racine.");
    }

    @Test
    @DisplayName("Borne minimale ou maximale égale à 0 ou 1")
    void testBornesZeroOuUn() {
        // P(x) = x - 0.5 -> une racine en 0.5 (comprise entre 0 et 1)
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-0.5, 1.0));
        
        int nbRacines = CalculateurRacines.suiteSturm(1.0, 0.0, p);
        assertEquals(1, nbRacines, "Il doit y avoir 1 racine (0.5) entre 0 et 1.");
    }

    @Test
    @DisplayName("Bornes entièrement négatives (ex: [-5, -2])")
    void testBornesNegatives() {
        // P(x) = x + 3 -> une racine en -3, qui est dans [-5, -2]
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(3.0, 1.0));
        
        int nbRacines = CalculateurRacines.suiteSturm(-2.0, -5.0, p);
        assertEquals(1, nbRacines, "Il doit y avoir 1 racine (-3) dans l'intervalle [-5, -2].");
    }

    @Test
    @DisplayName("Racine positionnée exactement sur une borne (ex: P(x)=x^2-1 sur [0, 1])")
    void testRacineSurBorne() {
        // P(x) = x^2 - 1 -> racines en -1 et 1
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 0.0, 1.0));
        
        // La méthode de Sturm compte les racines sur l'intervalle ]a, b] 
        // 1 est sur la borne Max, elle doit donc être comptée.
        int nbRacines = CalculateurRacines.suiteSturm(1.0, 0.0, p);
        assertEquals(1, nbRacines, "La racine 1 située sur la borne supérieure doit être comptée.");
    }

    @Test
    @DisplayName("Polynôme de degré 1 (ex: 2x - 4)")
    void testDegreUn() {
        // P(x) = -4 + 2x -> racine en 2
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-4.0, 2.0));
        
        int nbRacinesDansIntervalle = CalculateurRacines.suiteSturm(5.0, 0.0, p);
        int nbRacinesHorsIntervalle = CalculateurRacines.suiteSturm(1.0, -1.0, p);
        
        assertEquals(1, nbRacinesDansIntervalle, "La racine 2 est dans [0, 5].");
        assertEquals(0, nbRacinesHorsIntervalle, "La racine 2 n'est pas dans [-1, 1].");
    }

    @Test
    @DisplayName("Polynôme n'ayant aucune racine réelle dans l'intervalle (ex: x^2 + 1)")
    void testAucuneRacineReelle() {
        // P(x) = 1 + x^2
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(1.0, 0.0, 1.0));
        
        int nbRacines = CalculateurRacines.suiteSturm(10.0, -10.0, p);
        assertEquals(0, nbRacines, "x^2 + 1 n'a aucune racine réelle.");
    }

    @Test
    @DisplayName("Polynôme avec racines distinctes (ex: (x-1)(x-2)(x-3))")
    void testRacinesDistinctes() {
        // Utilisation de la méthode de reconstruction par racines : (x-1)(x-2)(x-3)
        // Racines: 1.0, 2.0, 3.0 avec multiplicité 1 chacune, coef dominant = 1.0
        Polynome p = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(1, 1, 1),
            1.0
        );
        
        int toutesLesRacines = CalculateurRacines.suiteSturm(4.0, 0.0, p);
        int deuxRacines = CalculateurRacines.suiteSturm(2.5, 0.5, p); // contient 1 et 2
        
        assertEquals(3, toutesLesRacines, "Il y a 3 racines distinctes dans [0, 4].");
        assertEquals(2, deuxRacines, "Il y a 2 racines distinctes dans [0.5, 2.5].");
    }

    @Test
    @DisplayName("Polynôme avec racines multiples (ex: (x-2)^3 -> vérification du compte unique)")
    void testRacinesMultiples() {
        // P(x) = (x-2)^3 -> Racine: 2.0, Multiplicité: 3, Coef dominant: 1.0
        // Rappel mathématique : La suite de Sturm standard compte le nombre de racines *distinctes*.
        Polynome p = CreationPolynome.creationPolynomeRacines(
            List.of(2.0),
            List.of(3),
            1.0
        );
        
        int nbRacines = CalculateurRacines.suiteSturm(5.0, 0.0, p);
        assertEquals(1, nbRacines, "La suite de Sturm doit compter la racine triple '2' comme une seule racine distincte.");
    }

    @Test
    @DisplayName("Polynôme de haut degré (long) pour tester la performance/stabilité")
    void testHautDegre() {
        // Générons un polynôme ayant 6 racines distinctes : 1, 2, 3, 4, 5, 6
        // P(x) = (x-1)(x-2)(x-3)(x-4)(x-5)(x-6) -> Degré 6
        Polynome p = CreationPolynome.creationPolynomeRacines(
            Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0),
            Arrays.asList(1, 1, 1, 1, 1, 1),
            1.0
        );
        
        int nbRacines = CalculateurRacines.suiteSturm(7.0, 0.0, p);
        assertEquals(6, nbRacines, "Le calculateur doit trouver de manière stable les 6 racines du polynôme de degré 6.");
    }

    @Test
    @DisplayName("Coefficients entiers (sans virgule)")
    void testCoefficientsEntiers() {
        // P(x) = x^2 - 4 -> Racines en -2 et 2 (Coefficients : -4, 0, 1)
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-4.0, 0.0, 1.0));
        
        int nbRacines = CalculateurRacines.suiteSturm(3.0, -3.0, p);
        assertEquals(2, nbRacines, "Il y a 2 racines (-2 et 2) pour ce polynôme à coefficients entiers.");
    }

    @Test
    @DisplayName("Coefficients réels (doubles à virgule, ex: 0.25x^2 - 0.5x)")
    void testCoefficientsReels() {
        // P(x) = -0.5x + 0.25x^2 -> Factorisable en 0.25x(x - 2) -> Racines en 0 et 2
        // Indice 0 (constante) = 0.0, Indice 1 (x^1) = -0.5, Indice 2 (x^2) = 0.25
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(0.0, -0.5, 0.25));
        
        int nbRacines = CalculateurRacines.suiteSturm(3.0, -1.0, p);
        assertEquals(2, nbRacines, "Il y a 2 racines (0 et 2) pour ce polynôme à coefficients décimaux.");
    }
}