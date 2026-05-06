/*
 * CreationPolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Polynome;
import polynome.model.Monome;
import polynome.model.CreationPolynome;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe CalculateurPolynome.
 * 
 * Plan de test : 
 *      test de creation de polynome par coefficients
 *              Liste de 1 element , liste de 10 elements , liste vide , liste 
 *              avec coefficients négatif et positif , seulement négatif
 *              seulement positif.
 *      test de creation de polynome par ses racines
 *              avec a positif , a negatif , ordre de multiplicité de meme 
 *              taille et de taille diférente de la liste des racines , liste de
 *              racine et de multiplicité vide et avec un seul élement.
 *
 * @author
 */

class CreationPolynomeTest {

    // utilitaire
	private double coefPourDegre(Polynome p, int degre) {
	    for (Monome m : p.getListeMonome()) {
	        if (m.getDegre() == degre) {
	            return m.getCoefficient();
	        }
	    }
	    return 0.0;
	}

    
    @Test
    void testCreationParCoefficientsSimple() {
        
    }

   
}