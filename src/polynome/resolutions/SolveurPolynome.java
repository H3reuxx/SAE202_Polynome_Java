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

}
