/* 
 * CalculateurPolynome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
 
package polynome.calculs;

import java.util.List; 
import java.util.ArrayList;
import java.lang.Math;
import polynome.model.Monome;
import polynome.model.Polynome;
import polynome.calculs.CalculateurPolynome;

/**
 * Classe utilitaire qui permet d'éffectuer les calculs liées aux polynomes.
 * 
 * @autor 
 */
public class CalculateurRacines {

	/**
	 * Méthode qui permet de calculer le nombre de racines d'un polynome dans un intervalle [borneMin, borneMax] en utilisant la méthode de Sturm.
	 * 
	 * @param borneMax
	 * @param borneMin
	 * @param polynome
	 * @return int , nombre de racines du polynome dans l'intervalle [borneMin, borneMax]
	 */
	public static int suiteSturm (double borneMax , double borneMin , Polynome polynome) {
		
		if (polynome == null) {
            throw new IllegalArgumentException("Le polynôme ne peut pas être nul.");
        }
        
        if (borneMax < borneMin) {
            throw new IllegalArgumentException("La borne maximum doit être supérieure ou égale à la borne minimum.");
        }
        
		Polynome polynomeDerive = polynome.deriver(); //P0
		Polynome resultat = new Polynome(); //P1
		
		List<Polynome> suiteSturm = new ArrayList<Polynome>();
		suiteSturm.add(polynome);//P0 (initialement p-2)
		suiteSturm.add(polynomeDerive); //P1 (initialement p-1)
		
		while (suiteSturm.get(suiteSturm.size()-1).getListeMonome().get(0).getDegre() != 0.0) {
			resultat = null;//- (reste de la division euclidienne de P0 par P1)
			suiteSturm.add(resultat);	
		}
		
		int changementSigneMax = 0;
		int changementSigneMin = 0;
		
		double dernierSigneNonNulMax = 0.0;
		double dernierSigneNonNulMin = 0.0;
		
		for (Polynome p : suiteSturm) {
			double evaluationBorneMax = p.evaluer(borneMax);
			double evaluationBorneMin = p.evaluer(borneMin);
			
			//Calcul du nombre de changement de signe pour la borne max
			if(!(evaluationBorneMax == 0.0)){
				double signeActuelMax = Math.signum(evaluationBorneMax);
	        
	            if (dernierSigneNonNulMax != 0.0 && signeActuelMax != dernierSigneNonNulMax) {
	                changementSigneMax++;
	            }
	            dernierSigneNonNulMax = signeActuelMax;
			}
			
			//Calcul du nombre de changement de signe pour la borne min
			if(!(evaluationBorneMin == 0.0)){
				double signeActuelMin = Math.signum(evaluationBorneMin);
	        
	            if (dernierSigneNonNulMin != 0.0 && signeActuelMin != dernierSigneNonNulMin) {
	                changementSigneMax++;
	            }
	            dernierSigneNonNulMax = signeActuelMin;
			}
			int nombreRacines = changementSigneMin - changementSigneMax;
		}
	}
	
	
}