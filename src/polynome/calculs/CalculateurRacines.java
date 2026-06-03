package polynome.calculs;

import java.util.List; 
import java.util.ArrayList;
import java.lang.Math;
import polynome.model.Polynome;
import polynome.resolutions.SolveurPolynome;

/**
 * Classe utilitaire qui permet d'effectuer les calculs liés aux polynômes.
 */
public class CalculateurRacines {

    /**
     * Calcule le nombre de racines distinctes d'un polynôme dans l'intervalle [borneMin, borneMax] 
     * en utilisant la méthode de la suite de Sturm.
     */
    public static int suiteSturm(double borneMax, double borneMin, Polynome polynome) {
        
        if (polynome == null) {
            throw new IllegalArgumentException("Le polynôme ne peut pas être nul.");
        }
        
        if (borneMax < borneMin) {
            throw new IllegalArgumentException("La borne maximum doit être supérieure ou égale à la borne minimum.");
        }
        
        //si le polynôme initial est nul
        if (polynome.estNul()) {
            return 0; 
        }

        // Construction de la suite de Sturm
        List<Polynome> suiteSturm = new ArrayList<>();
        suiteSturm.add(polynome);            // P0
        suiteSturm.add(polynome.deriver());  // P1
        
        while (true) { //P2 --> Pn
            int taille = suiteSturm.size();
            Polynome pPrecedent = suiteSturm.get(taille - 2);
            Polynome pDernier = suiteSturm.get(taille - 1);
            
            // Condition d'arrêt : si le dernier élément est mathématiquement nul
            if (pDernier.estNul()) {
                break;
            }
            
            // Si le dernier élément est une constante non nulle (degré 0), on s'arrête après l'avoir traité
            if (pDernier.getTermeDominant().getDegre() == 0) {
                break;
            }
            
            // Récupération du reste de la division euclidienne
            List<Polynome> division = CalculateurPolynome.diviserPolynomes(pPrecedent, pDernier);
            Polynome reste = division.get(1);
            
            if (reste.estNul()) {
                break;
            }
            
            // Règle de Sturm : P_i = -Reste
            Polynome opposeReste = CalculateurPolynome.multiplierPolynomeAvecScalaire(reste, -1.0);
            suiteSturm.add(opposeReste);	
        }
        
        //Changements de signes.
        int changementSigneMax = 0;
        int changementSigneMin = 0;
        
        // Utilisation d'objets Double pour savoir si un premier signe a été enregistré 
        Double dernierSigneMax = null;
        Double dernierSigneMin = null;
        
        for (Polynome p : suiteSturm) {
            // Utilisation obligatoire de resoudreDirect car resoudreHorner nécessite un polynôme complet
            double evaluationBorneMax = SolveurPolynome.resoudreDirect(p, borneMax);
            double evaluationBorneMin = SolveurPolynome.resoudreDirect(p, borneMin);
            
            // Évaluation à la borne supérieure (Max)
            if (Math.abs(evaluationBorneMax) > 1e-9) {
                double signeActuelMax = Math.signum(evaluationBorneMax);
                if (dernierSigneMax != null && signeActuelMax != dernierSigneMax) {
                    changementSigneMax++;
                }
                dernierSigneMax = signeActuelMax;
            }
            
            // Évaluation à la borne inférieure (Min)
            if (Math.abs(evaluationBorneMin) > 1e-9) {
                double signeActuelMin = Math.signum(evaluationBorneMin);
                if (dernierSigneMin != null && signeActuelMin != dernierSigneMin) {
                    changementSigneMin++; 
                }
                dernierSigneMin = signeActuelMin; 
            }
        }
        
        // Formule de Sturm : N = V(borneMin) - V(borneMax)
        return changementSigneMin - changementSigneMax;
    }
    
    public static double newton(Polynome p, double x0) {
        Polynome pDerive = p.deriver(); //
        double x = x0; //
        double epsilon = 1e-9; //
        
        int maxIterations = 100; //
        int iterations = 0; //
        
        while (iterations < maxIterations) { //
        	// À insérer dans ta boucle while de Newton :
            double fx = SolveurPolynome.resoudreHorner(p, x); //
            double fPrimeX = SolveurPolynome.resoudreHorner(pDerive, x); //
            
            if (Math.abs(fPrimeX) < epsilon) { //
                throw new ArithmeticException("La dérivée est trop proche de zéro. Choisissez un autre point de départ."); //
            }
            
            double xNext = x - fx / fPrimeX; //
            
            if (Math.abs(xNext - x) < epsilon) { //
                return xNext; //
            }
            
            x = xNext; //
            iterations++; //
        }
        throw new ArithmeticException("L'algorithme de Newton n'a pas convergé après " + maxIterations + " itérations."); //
    }
    
    
    /**q
     * Calcule une estimation de l'intervalle dans lequel se trouvent les racines d'un polynôme en utilisant le théorème de Cauchy.
     * @param p Polynome dont on veut estimer l'intervalle des racines.
     * @return Un tableau de deux éléments contenant les bornes inférieure et supérieure de l'intervalle estimé.
     */
    public static double[] calculerBornesInitiales(Polynome p) {
        // 1. Trouver le coefficient dominant (an) et son degré
        double an = Math.abs(p.getTermeDominant().getCoefficient());
        
        double maxCoeff = 0;
        
        
        // 2. Trouver le plus grand coefficient parmi tous les autres (de a0 à an-1)
        for (int i = 0; i < p.getListeMonome().size(); i++) {
            double coeffActuel = Math.abs(p.getListeMonome().get(i).getCoefficient());
            if (coeffActuel > maxCoeff) {
                maxCoeff = coeffActuel;
            }
        }
        
        //Formule de Cauchy : R = 1 + (Max(|ai|) / |an|) (avec ai le coef max et an le coef dominant)
        double r = 1.0 + (maxCoeff / an);
        return new double[]{-r, r};
    }
    
}