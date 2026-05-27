package polynome.resolutions;

import java.util.List;
import polynome.model.Monome;
import polynome.model.Polynome;

public class SolveurPolynome {
	
	public static double resoudreHorner(Polynome polynomeAResoudre, double x) {
		
		double resultat;
		List<Monome> listeMonome = polynomeAResoudre.getListeMonome();
		
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
			System.out.println(resMonome);
			resMonome *= monome.getCoefficient();
			System.out.println(resMonome);
			System.out.println();
			resultat += resMonome;
		}
		
		return resultat;
	}
}
