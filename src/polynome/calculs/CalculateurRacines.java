package polynome.calculs;

import java.util.List; 
import java.util.ArrayList;
import java.lang.Math;
import polynome.model.Monome;
import polynome.model.Polynome;
import polynome.resolutions.SolveurPolynome;
import polynome.calculs.CalculateurPolynome;

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
        
        // Sécurité : si le polynôme initial est nul, la suite de Sturm n'est pas définie (retourne 0)
        if (Polynome.estNul(polynome)) {
            return 0; 
        }

        // Étape 1 : Initialisation et construction de la suite de Sturm
        List<Polynome> suiteSturm = new ArrayList<>();
        suiteSturm.add(polynome);            // P0 = Polynôme de base
        suiteSturm.add(polynome.deriver());  // P1 = Polynôme dérivé
        
        while (true) {
            int taille = suiteSturm.size();
            Polynome pPrecedent = suiteSturm.get(taille - 2);
            Polynome pDernier = suiteSturm.get(taille - 1);
            
            // Condition d'arrêt : si le dernier élément est un polynôme nul ou une constante (degré 0)
            if (Polynome.estNul(pDernier) || pDernier.getListeMonome().isEmpty() || pDernier.getListeMonome().get(0).getDegre() == 0) {
                break;
            }
            
            // Récupération du reste de la division euclidienne (Index 1 de la liste retournée)
            List<Polynome> division = CalculateurPolynome.diviserPolynomes(pPrecedent, pDernier);
            Polynome reste = division.get(1);
            
            // Si le reste est nul, la suite est complétée
            if (Polynome.estNul(reste) || reste.getListeMonome().isEmpty()) {
                break;
            }
            
            // Règle de Sturm : l'élément suivant est l'opposé du reste (P_i = -Reste)
            Polynome opposeReste = CalculateurPolynome.multiplierPolynomeAvecScalaire(reste, -1.0);
            suiteSturm.add(opposeReste);	
        }
        
        // Étape 2 : Évaluation des changements de signe aux bornes de l'intervalle
        int changementSigneMax = 0;
        int changementSigneMin = 0;
        
        double dernierSigneNonNulMax = 0.0;
        double dernierSigneNonNulMin = 0.0;
        
        for (Polynome p : suiteSturm) {
            // Évaluation de la valeur du polynôme courant aux bornes via la méthode de Horner (plus précise que la methode directe)
            double evaluationBorneMax = SolveurPolynome.resoudreHorner(p, borneMax);
            double evaluationBorneMin = SolveurPolynome.resoudreHorner(p, borneMin);
            
            // Comptage des changements de signe pour la borne supérieure (Max)
            if (evaluationBorneMax != 0.0) {
                double signeActuelMax = Math.signum(evaluationBorneMax);
                if (dernierSigneNonNulMax != 0.0 && signeActuelMax != dernierSigneNonNulMax) {
                    changementSigneMax++;
                }
                dernierSigneNonNulMax = signeActuelMax;
            }
            
            // Comptage des changements de signe pour la borne inférieure (Min)
            if (evaluationBorneMin != 0.0) {
                double signeActuelMin = Math.signum(evaluationBorneMin);
                if (dernierSigneNonNulMin != 0.0 && signeActuelMin != dernierSigneNonNulMin) {
                    changementSigneMin++; 
                }
                dernierSigneNonNulMin = signeActuelMin; 
            }
        }
        
        //N = V(borneMin) - V(borneMax)
        return changementSigneMin - changementSigneMax;
    }
}