/**
 * SolveurPolynome.java
 * IUT de Rodez, no copy right/left
 */

package polynome.resolutions;

import java.util.List; 
import polynome.model.Monome;
import polynome.model.Polynome;
import polynome.calculs.CalculateurPolynome;

/**
 * Classe utilitaire qui permet de résoudre un polynome à l'aide de différentes méthodes de résolution.
 * @author Gabriel TOLL , Alban ROZES, Erwan NAZAL
 */
public class SolveurPolynome {
	
	/**
	 * Résolution d'un polynome à l'aide de la méthode de Horner.
	 * @param polynomeAResoudre
	 * @param x
	 * @return resultat (valeur du polynome pour x)
	 */
	public static double resoudreHorner(Polynome polynomeAResoudre, double x) {
		
		double resultat;
		Polynome polynome = new Polynome(polynomeAResoudre.getListeMonome());
		CalculateurPolynome.trierInsertion(polynome);
		List<Monome> listeMonome = polynome.getListeMonome();
		
		if (listeMonome.isEmpty()) {
			throw new IllegalArgumentException("Erreur : Le polynome est nul.");
		} else {
			resultat = listeMonome.get(0).getCoefficient();
		}
		
		for (int index = 1; index < listeMonome.size(); index++) {
			resultat *= x;
			resultat += listeMonome.get(index).getCoefficient();
		}
		
		return resultat;
	}
	
	
	/**
	 * Résolution d'un polynome à l'aide de la méthode directe. donc on utilise la formule : P(x) = a0*x^n + a1*x^(n-1) + ... + an-1*x + an
	 * @param polynomeAResoudre
	 * @param x
	 * @return resultat (valeur du polynome pour x)
	 */
	public static double resoudreDirect(Polynome polynomeAResoudre, double x) {
		
		List<Monome> listeMonome = polynomeAResoudre.getListeMonome();
		
		double resultat;
		double resMonome;
		
		if (listeMonome.isEmpty()) {
			throw new IllegalArgumentException("Erreur : Le polynome est nul.");
		} else {
			resultat = 0;
		}
		
		for (Monome monome : listeMonome) {
			
			resMonome = 1.0;
			for (int iteration = 0; iteration < monome.getDegre(); iteration++) {
				resMonome *= x;
			}
			resMonome *= monome.getCoefficient();
			resultat += resMonome;
		}
		
		return resultat;
	}
}
