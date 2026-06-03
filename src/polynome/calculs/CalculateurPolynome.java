/* 
 * CalculateurPolynome.java
 * IUT de Rodez, no copy right/left
 */
 
package polynome.calculs;

import java.util.List;
import java.util.ArrayList;
import polynome.model.Monome;
import polynome.model.Polynome;
import polynome.model.CreationPolynome;

/**
 * Classe utilitaire qui permet d'éffectuer des calculs liées aux polynomes.
 * 
 * @autor gabriel TOLL , Alban ROZES, Erwan NAZAL
 */
public class CalculateurPolynome {

    /*
     * Addition de deux polynômes : on crée un polynome de résultat, on copie
     * les monomes du premier polynome (P1) pour les inserer dans polynomeFinal,
     * ensuite on parcours le deuxieme polynome (P2) en comparant les monomes de
     * polynomeFinal, si il existe deja un monome du même degre alors on additionne
     * sinon on ajoute.
     * 
     * @param polynome1
     * @param polynome2
     * @return polynomeFinal
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

            if (!isDegreExistant && monomeP2.getCoefficient() != 0.0) {
                polynomeFinal.ajouterMonome(monomeP2);
            }
        }

        return polynomeFinal;
    }

    /*
     * Soustraction de deux polynômes : on crée un polynome de résultat, on copie
     * les monomes du premier polynome (P1) pour les inserer dans polynomeFinal,
     * ensuite on parcours le deuxieme polynome (P2) en comparant les monomes de
     * polynomeFinal, si il existe deja un monome du même degre alors on soustrait
     * sinon on ajoute en négatif.
     * 
     * @param polynome1
     * @param polynome2
     * @return polynomeFinal
     */
    public static Polynome soustractionPolynome(Polynome polynome1, Polynome polynome2) {

        Polynome polynomeFinal = new Polynome(polynome1.getListeMonome());

        List<Monome> listeMonomeFinal = polynomeFinal.getListeMonome();
        List<Monome> listeMonomePolynome2 = polynome2.getListeMonome();

        for (Monome monomeP2 : listeMonomePolynome2) {

            boolean isDegreExistant = false;

            for (int indexMonomeP1 = 0; indexMonomeP1 < listeMonomeFinal.size() && !isDegreExistant; indexMonomeP1++) {
                
                Monome monomeP1 = listeMonomeFinal.get(indexMonomeP1);
                
                if (monomeP2.getDegre() == monomeP1.getDegre()) {

                    double nouveauCoef = monomeP1.getCoefficient() - monomeP2.getCoefficient();

                    listeMonomeFinal.set(indexMonomeP1, new Monome(nouveauCoef, monomeP1.getDegre()));

                    isDegreExistant = true; 
                }
            }

            if (!isDegreExistant) {
                double coefInverse = -monomeP2.getCoefficient();
                polynomeFinal.ajouterMonome(new Monome(coefInverse, monomeP2.getDegre()));
            }
        }

        
        
        
        
        
        return polynomeFinal;
    }

    /** multiplication de deux polynome : il faut que chaque monome de P1 rencoontre
     * chaque monome de P2 (distributivité), ensuite les coefficient se multiplient 
     * et les degres s'additionent puis on les insère dans polynomeFinal en additionant
     * les monomes de même degre (en utilisant la logique de l'addition de polynome)
     * @param polynome1
     * @param polynome2
     * @return polynomeFinal
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

    /* *
     * division euclidienne de deux polynome.
     * @param polynomeDividende : le polynome à diviser
     * @param polynomeDiviseur : le polynome par lequel on divise
     * @return une liste de deux éléments : le premier est le polynome quotient et le second est le polynome reste
     */
    public static List<Polynome> diviserPolynomes(Polynome polynomeDividende, Polynome polynomeDiviseur) {
    	
    	if (polynomeDiviseur == null) {
    		throw new IllegalArgumentException("La division par le pôlynome nul est impossible.");
    	}
    	
    	Polynome polynomeQuotient = new Polynome();
    	Polynome polynomeReste = polynomeDividende;
    	
    	Monome termeDominantDiviseur = polynomeDiviseur.getTermeDominant();
    	
    	while(!polynomeReste.estNul() && polynomeReste.getTermeDominant().getDegre() >= termeDominantDiviseur.getDegre()) {
    		Monome termeDominantReste = polynomeReste.getTermeDominant();
            
            /* "t" désigne le nouveau terme */
            double coefT = termeDominantReste.getCoefficient() / termeDominantDiviseur.getCoefficient();
            int degreT = termeDominantReste.getDegre() - termeDominantDiviseur.getDegre();
            Monome t = new Monome(coefT, degreT);
            
            /* Ajout de "t" au quotient */
            polynomeQuotient.ajouterMonome(t);
            
            /* Reste = Reste - (t * Diviseur) */
            Polynome polynomeT = new Polynome(List.of(t));
            Polynome produit = multiplierPolynomes(polynomeDiviseur, polynomeT);
            
            polynomeReste = soustractionPolynome(polynomeReste, produit);
    	}
    	
    	return List.of(polynomeQuotient, polynomeReste);
    }

    /**
     * Trie le polynôme passé en paramètre par degrés décroissants.
     */
    public static void trierInsertion(Polynome p) {
        // On récupère l'ArrayList spécifique
        List<Monome> liste = p.getListeMonome();
        int n = liste.size();
        
        for (int i = 1; i < n; i++) {
            Monome key = liste.get(i);
            int j = i - 1;
            
            // On remonte les monômes de plus petit degré vers la fin
            while (j >= 0 && liste.get(j).getDegre() < key.getDegre()) {
                liste.set(j + 1, liste.get(j));
                j--;
            }
            liste.set(j + 1, key);
        }
    }
    
    /**
     * Multiplie un polynome par un scalaire. 
     * @param polynome1
     * @param scalaire
     * @return le résultat de la multiplication du polynome1 par le scalaire
     */
    public static Polynome multiplierPolynomeAvecScalaire(Polynome polynome1, double scalaire) { 
		Monome monomeScalaire = new Monome(scalaire,0);
		Polynome polynomeScalaire = new Polynome(List.of(monomeScalaire));
		return multiplierPolynomes(polynome1,polynomeScalaire); 
	}
    
}