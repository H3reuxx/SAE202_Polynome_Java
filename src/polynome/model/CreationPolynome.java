/* 
 * CreationPolynome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
 
package polynome.model;

import static polynome.calculs.CalculateurPolynome.multiplierPolynomes;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * 
 * @autor 
 */

public class CreationPolynome {
    /**
     * Cree un objet Polynome a partir d'une liste de coefficients , cependant
     * ne fonctionne que si la liste de coefficient est triée de façon a ce
     * que l'indice du coefficient soit la puissance du monome associé. 
     * @param coefficients
     * @return polynomeFinal
     */
    public static Polynome CreationPolynomeCoefficients(List<Double> coefficients) {

        Polynome polynomeFinal = new Polynome();

        for(int degre = 0 ; degre<coefficients.size() ; degre++){
            polynomeFinal.ajouterMonome(new Monome(coefficients.get(degre), degre));
        }
        return polynomeFinal;
    }


    /**
     * Creation d'un polynome a partire de ses racines , leurs ordre de multiplicites et le coef dominant 
     * via la formule de reconstruction , "a*((x-r1)^m1)*((x-r2)^m2)*...*((x-rn)^mn)"
     * ne fonctionne que si , le polynome n'admet que des racines reeles.
     * @param racines
     * @param multiplicites
     * @param coefDominant
     * @return polynomeFinal 
     */
    public static Polynome creationPolynomeRacines(List<Double> racines, List<Integer> multiplicites, double coefDominant) {
    	Polynome polynomeFinal = new Polynome();
    	
    	if (racines.size() != multiplicites.size()) {
    		return polynomeFinal;
    	}
 
    	if (racines == null || multiplicites == null) { 
    		polynomeFinal.ajouterMonome(new Monome(coefDominant , 0));
            return polynomeFinal;
        }
    	
    	
        /*
         * Ajout du premier monome dans le polynome final, celui ci
         * est un polynome simple qui represente 'a', le coefficient dominant.
         */
        polynomeFinal.ajouterMonome(new Monome(coefDominant , 0));
        
        for (int index=0;index<racines.size();index++){
            double r = racines.get(index);
            int m = multiplicites.get(index); 

            //ensuite on crée le polynome (x-r)
            // C'est une liste de deux monômes : [1.0 * x^1] et [-r * x^0]
            Polynome facteur = new Polynome();
            facteur.ajouterMonome(new Monome(1.0 , 1));
            facteur.ajouterMonome(new Monome(-r , 0));

            /* On multiplie m fois le polynomeFinal par le facteur , (a*(x -r) ^ m ) , c'est ici le ^m,
             * cela permet de faire d'élever notre polynome à la puissance souhaiter (m) en passant par
             * la multiplication successive.
             */
            for (int j=0 ; j < m ; j++){
                polynomeFinal = multiplierPolynomes(polynomeFinal, facteur);
            }

        }
        return polynomeFinal;
    }
}
