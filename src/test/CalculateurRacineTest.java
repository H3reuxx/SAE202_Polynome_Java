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
 * 	- Borne invalide : borneMax < borneMin (doit lever IllegalArgumentException)
 * 	- Polynôme nul (doit renvoyer 0 ou être rejeté)
 * 	- Polynôme constant non nul ex: P(x) = 5 (doit renvoyer 0)
 * 	- Borne minimale ou maximale égale à 0 ou 1
 * 	- Bornes entièrement négatives (ex: [-5, -2])
 * 	- Racine positionnée exactement sur une borne (ex: P(x)=x^2-1 sur [0, 1])
 * 	- Polynôme de degré 1 (ex: 2x - 4)
 * 	- Polynôme n'ayant aucune racine réelle dans l'intervalle (ex: x^2 + 1)
 * 	- Polynôme avec racines distinctes (ex: (x-1)(x-2)(x-3))
 * 	- Polynôme avec racines multiples (ex: (x-2)^3 -> vérification du compte unique)
 * 	- Polynôme de haut degré (long) pour tester la performance/stabilité
 * 	- Coefficients entiers (sans virgule)
 * 	- Coefficients réels (doubles à virgule, ex: 0.25x^2 - 0.5x)
 * * Plan de tests Unitaires pour la méthode de Newton :
 * 	- 0 racine réelle globale : comportement aveugle (doit lever ArithmeticException pour non-convergence après 100 itérations)
 * 	- Présence de plusieurs racines : convergence locale vers la plus proche selon x0 (ex: x^2 - 4)
 * 	- Polynôme de degré 1 (ex: 2x - 4, convergence vers la racine x = 2.0)
 * 	- Polynôme de degré supérieur (ex: x^2 - 2, convergence vers x = sqrt(2))
 * 	- x0 positionné exactement sur la racine réelle (0 itération requise)
 * 	- Point de départ éloigné.
 * 	- x0 positionné sur un extremum local où f'(x) = 0 (doit lever ArithmeticException)
 * 	- dérivée f'(x) extrêmement proche de zéro, sous le seuil epsilon (doit lever ArithmeticException)
 * 	- cas de cycles d'oscillations infinis entre deux valeurs (doit lever ArithmeticException après 100 itérations)
 * * @author Gabriel TOLL , Alban ROZES
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
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(0.0, -0.5, 0.25));
        
        int nbRacines = CalculateurRacines.suiteSturm(3.0, -1.0, p);
        assertEquals(2, nbRacines, "Il y a 2 racines (0 et 2) pour ce polynôme à coefficients décimaux.");
    }
    
    // TESTS POUR LA MÉTHODE DE NEWTON -------------------------------
    
    @Test
    @DisplayName("Newton : 0 racine réelle (Doit lever ArithmeticException pour non-convergence)")
    void testNewtonZeroRacineSturm() {
        // P(X) = X^2 + 4 (N'a aucune racine réelle) -> liste: [4.0, 0.0, 1.0]
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(4.0, 0.0, 1.0));
        
        // On s'attend à ce que Newton ne trouve rien et lève une ArithmeticException après 100 itérations
        assertThrows(ArithmeticException.class, () -> {
            CalculateurRacines.newton(p, 1.0);
        }, "Le polynôme n'a pas de racine réelle, l'algorithme ne doit pas converger.");
    }

    @Test
    @DisplayName("Newton : Choix de la racine la plus proche parmi plusieurs")
    void testNewtonPlusieursRacinesStandard() {
        // P(X) = X^2 - 4 (Racines : -2.0 et 2.0) -> liste: [-4.0, 0.0, 1.0]
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-4.0, 0.0, 1.0));
        
        // Test 1 : En partant de 3.0, on doit converger vers 2.0
        double racinePositive = CalculateurRacines.newton(p, 3.0);
        assertEquals(2.0, racinePositive, 1e-7, "Newton doit converger vers la racine 2.0.");
        
        // Test 2 : En partant de -3.0, on doit converger vers -2.0
        double racineNegative = CalculateurRacines.newton(p, -3.0);
        assertEquals(-2.0, racineNegative, 1e-7, "Newton doit converger vers la racine -2.0.");
    }
    
    @Test
    @DisplayName("Newton : Polynôme de degré 1 (2x - 4 = 0 -> x = 2.0)")
    void testNewtonDegreUn() {
        // P(X) = 2X - 4 -> liste: [-4.0, 2.0]. Racine exacte = 2.0
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-4.0, 2.0));
        
        // On choisit un point de départ proche (x0 = 2.5) pour être sûr de rester 
        // dans l'intervalle calculé par calculerBornesInitiales
        double racine = CalculateurRacines.newton(p, 2.5); 
        assertEquals(2.0, racine, 1e-7, "La racine calculée pour 2x - 4 doit être 2.0");
    }

    @Test
    @DisplayName("Newton , Polynome de degre supérieur avec racine simple (x^2 - 2 = 0)")
    void testNewtonDegreSupérieur() {
        // P(X) = X^2 - 2 (Racines: -sqrt(2) et +sqrt(2)) -> liste: [-2.0, 0.0, 1.0]
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-2.0, 0.0, 1.0));
        
        // Si tes bornes initiales automatiques isolent la racine positive :
        double racine = CalculateurRacines.newton(p, 2.0); 
        assertEquals(Math.sqrt(2.0), racine, 1e-7, "Newton doit renvoyer la racine carrée de 2.");
    }

    @Test
    @DisplayName("Point de départ idéal x0 positionné sur la racine exacte")
    void testNewtonDejaSurLaRacine() {
        // P(X) = X - 3 -> liste: [-3.0, 1.0] (Racine évidente = 3.0)
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-3.0, 1.0));
        
        double racine = CalculateurRacines.newton(p, 3.0); // x0 est déjà égal à 3.0
        assertEquals(3.0, racine, 1e-9, "L'algorithme doit s'arrêter immédiatement en 0 itération.");
    }

    @Test
    @DisplayName("Newton : Sécurité f'(x) = 0 au point de départ (Doit lever ArithmeticException)")
    void testNewtonDeriveeNulle() {
        // P(X) = X^3 - 3X^2 + 3X - 1 -> Unique racine en X = 1.0
        // Sa dérivée est P'(X) = 3X^2 - 6X + 3. En x0 = 1.0, f'(1) = 0.0.
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 3.0, -3.0, 1.0));
        
        assertThrows(ArithmeticException.class, () -> {
            CalculateurRacines.newton(p, 1.0);
        }, "La tangente est horizontale, l'algorithme doit lever une ArithmeticException.");
    }

    @Test
    @DisplayName("sous le seuil epsilon")
    void testNewtonDeriveeProcheZero() {
        // P(x) = x^3 - 3x^2 + 3x - 1 -> Une seule racine réelle globale en x = 1.0
        // Liste des coefficients : a0 = -1.0, a1 = 3.0, a2 = -3.0, a3 = 1.0
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(-1.0, 3.0, -3.0, 1.0));
        
        assertThrows(ArithmeticException.class, () -> {
            // En démarrant en x0 = 1.0, f'(1.0) sera strictement égal à 0.0, ce qui est inférieur à ton epsilon (1e-9)
            CalculateurRacines.newton(p, 1.0);
        }, "La dérivée est trop petite, l'exception de sécurité doit se déclencher.");
    }

    @Test
    @DisplayName("Newton : Non-convergence / Oscillation infinie (Doit lever ArithmeticException après 100 itérations)")
    void testNewtonNonConvergenceOscillation() {
        // P(X) = X^3 - 2X + 2 -> liste: [2.0, -2.0, 0.0, 1.0]
        // Si x0 = 0.0 -> x1 = 1.0 -> x2 = 0.0. L'algorithme oscille indéfiniment entre 0 et 1.
        Polynome p = CreationPolynome.CreationPolynomeCoefficients(Arrays.asList(2.0, -2.0, 0.0, 1.0));
        
        assertThrows(ArithmeticException.class, () -> {
            // On commence l'oscillation à 0.0
            CalculateurRacines.newton(p, 0.0);
        }, "L'algorithme boucle à l'infini, le compteur d'itérations max doit bloquer et lever une exception.");
    }
}