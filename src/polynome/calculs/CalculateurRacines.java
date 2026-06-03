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
}