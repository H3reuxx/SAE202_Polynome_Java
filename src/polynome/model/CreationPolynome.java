/* 
 * CreationPolynome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
 
package polynome.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @autor 
 */

class CreationPolynome {
    /**
     * Cree un objet Polynome a partire d'une liste de coefficients , cependant
     * ne fonctionne que si la liste de coefficient est triée de façon a ce
     * que l'indice du coefficient soit la puissance du monome associé. 
     * @param coefficients
     * @return resultat
     */
    public Polynome CreationPolynomeCoefficients(List<Double> coefficients) {

        Polynome resultat = new Polynome();

        for(int i = 0 ; i<coefficients.size() ; i++){
            resultat.ajouterMonome(new Monome(coefficients.get(i),i));
        }
        return resultat;
    }


    /**
     * Creation d'un polynome a partire de ses racines , leurs ordre de multiplicites et le coef dominant 
     * via la formule de reconstruction , "a*((x-r1)^m1)*((x-r2)^m2)*...*((x-rn)^mn)"
     * ne fonctionne que si , le polynome n'admet que des racines reeles.
     * @param racines
     * @param multiplicites
     * @param coefDominant
     * @return resultat 
     */

    public Polynome creationPolynomeRacines(List<Double> racines,List<Integer> multiplicites,double coefDominant) {
        ArrayList<Double> listeRacines = new ArrayList<>(racines);
        ArrayList<Integer> listeMult = new ArrayList<>(multiplicites);


        if (racines == null || multiplicites == null || racines.size() != multiplicites.size() || racines.isEmpty()) {
            return null; // Ou renvoyer un Polynome vide selon ton choix
        }

        Polynome resultat = new Polynome();
        resultat.ajouterMonome(new Monome(coefDominant , 0)); //Ajout du premier monome
        //de facon a crée la base du polynome
        
        for (int index=0;index<listeRacines.size();index++){
            double r = racines.get(index);
            double m = multiplicites.get(index);

            //ensuite on crée le polynome (x-r)
            // C'est une liste de deux monômes : [1.0 * x^1] et [-r * x^0]
            Polynome facteur = new Polynome();
            facteur.ajouterMonome(new Monome(1.0 , 1));
            facteur.ajouterMonome(new Monome(-r , 0));

            //On multiplie m fois le resultat par le facteur , (a*(x -r) ^ m ) , c'est ici le ^m
            for (int j=0 ; j < m ; j++){
                resultat = multiplierPolynomes(resultat, facteur); //Pas encore réaliser.
            }

        }
        return resultat;
    }
}