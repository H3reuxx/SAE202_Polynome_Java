/* 
 * Main.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */

package polynome;

import java.util.ArrayList;
import java.util.List;

import polynome.model.Monome;
import polynome.model.Polynome;

import static polynome.calculs.CalculateurPolynome.multiplierPolynomes;
import static polynome.model.CreationPolynome.creationPolynomeRacines;

/**
 * TODO
 * 
 * @autor 
 */

class Main {

    public static void main(String[] args) {
        List<Double> coef = List.of(1.0);
        List<Integer> mult = List.of(2);
        double coefDominant = 1.0;

        Polynome testPoly1 = creationPolynomeRacines(coef,mult,coefDominant);

        List<Monome> resultat = testPoly1.getListeMonome();

        for (int i = 0; i<resultat.size();i++){
            System.out.println(resultat.get(i));
        } 
    }
}