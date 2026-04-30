/* 
 * CalculateurPolynome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
 
package polynome.calculs;

import java.util.List;
import polynome.model.Monome;
import polynome.model.Polynome;

/**
 * Classe utilitaire qui permet d'éffectuer les calculs liées aux polynomes.
 * 
 * @autor 
 */
public class CalculateurPolynome {

    /*
     * Addition de deux polynôme : on crée un polynome de résultat, on copie
     * les monomes du premier polynome (P1) pour les inserer dans polynomeFinal,
     * ensuite on parcours le deuxieme polynome (P2) en comparant les monomes de
     * polynomeFinal, si il existe deja un monome du même degre alors on additionne
     * sinon on ajoute.
     */
    public static Polynome additionnerPolynome(Polynome polynome1, Polynome polynome2) {

        Polynome polynomeFinal = new Polynome(polynome1.getListeMonome());

        /* récupère la liste des monomes */
        List<Monome> listeMonomePolynome1 = polynomeFinal.getListeMonome();
        List<Monome> listeMonomePolynome2 = polynome2.getListeMonome();

        /* première boucle pour comparer P2 à P1 */
        for (Monome monomeP2 : listeMonomePolynome2) {

            /* condition qui permet d'éviter d'ajouter plusieurs fois le même 
             * monome dans le polynome final.
             */
            boolean isDegreExistant = false;
            /* boucle sur touts les monomes de P1 pour comparer individuellement avec P2
             * si et seulement si le monome correspondant n'a pas encore été trouvé
             */
            for (int indexMonomeP1 = 0; indexMonomeP1 < listeMonomePolynome1.size() && !isDegreExistant; indexMonomeP1++) {

                Monome monomeP1 = listeMonomePolynome1.get(indexMonomeP1);
                /* condition : si le degre de P2 = degre de P1 alors on peut faire l'addition */
                if (monomeP2.getDegre() == monomeP1.getDegre() && !isDegreExistant) {
                    listeMonomePolynome1.set(indexMonomeP1, new Monome(
                                                                monomeP2.getCoefficient() + monomeP1.getCoefficient(),
                                                                monomeP2.getDegre()
                                                            ));
                    isDegreExistant = true;
                }
            }

            if (!isDegreExistant) {
                polynomeFinal.ajouterMonome(monomeP2);
            }
        }

        return polynomeFinal;
    }

    /* multiplication de deux polynome : il faut que chaque monome de P1 rencoontre
     * chaque monome de P2 (distributivité), ensuite les coefficient se multiplient 
     * et les degres s'additionent puis on les insère dans polynomeFinal en additionant
     * les monomes de même degre (en utilisant la logique de l'addition de polynome)
     */
    public static Polynome multiplierPolynomes(Polynome polynome1, Polynome polynome2) {
        Polynome polynomeFinal = new Polynome();

        /* récupère la liste des monomes */
        List<Monome> listeMonomePolynome1 = polynome1.getListeMonome();
        List<Monome> listeMonomePolynome2 = polynome2.getListeMonome();

        /* On boucle sur touts les monomes (distributivité) */
        for (Monome monomeP2 : listeMonomePolynome2) {
            for (Monome monomeP1 : listeMonomePolynome1) {
                /* multiplication des coefs et addition des degres */
                double nouveauCoefficient = monomeP1.getCoefficient() * monomeP2.getCoefficient();
                int nouveauDegre = monomeP1.getDegre() + monomeP2.getDegre();

                /* Même logique que l'ddition qui permet d'ajouter au polynome final sans avoir de doublons */
                boolean isDegreExistant = false;
                List<Monome> listeMonomesFinal = polynomeFinal.getListeMonome();

                for (int indexMonomePFinal = 0; indexMonomePFinal < listeMonomesFinal.size() && !isDegreExistant; indexMonomePFinal++) {

                    Monome monomePFinal = listeMonomesFinal.get(indexMonomePFinal);
                    if (nouveauDegre == monomePFinal.getDegre() && !isDegreExistant) {
                        listeMonomesFinal.set(indexMonomePFinal, new Monome(
                                                                    nouveauCoefficient + monomePFinal.getCoefficient(),
                                                                    nouveauDegre
                                                                ));
                        isDegreExistant = true;
                    }
                }

                if (!isDegreExistant) {
                    polynomeFinal.ajouterMonome(new Monome(nouveauCoefficient, nouveauDegre));
                }
            }
        }

        return polynomeFinal;
    }
    
}