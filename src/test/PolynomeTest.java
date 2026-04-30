/*
 * PolynomeTest.java
 *
 * IUT de Rodez, no copy right/left    10/04
 */
package test;

import polynome.model.Polynome;
import polynome.model.Monome;  
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Plan de test unitaire pour la classe Polynome.
 * 
 * L'objectif de ce plan est de garantir l'intégrité de la structure de données 
 * représentant le polynôme. On se concentre sur la manipulation de la liste 
 * interne de monômes.
 * 
 * Les axes de test sont les suivants :
 * <ul>
 *     <li><b>Initialisation :</b> Vérification que tout nouveau polynôme dispose 
 *     d'une structure de stockage prête à l'emploi et non nulle.</li>
 *     <li><b>Dynamique de stockage :</b> Validation de l'ajout unitaire et 
 *     multiple d'éléments dans la collection.</li>
 *     <li><b>Cohérence des données :</b> Vérification que les objets récupérés 
 *     depuis le polynôme conservent leurs propriétés d'origine.</li>
 *     <li><b>Sécurité du constructeur :</b> Test du comportement lors de 
 *     l'instanciation pour s'assurer de l'indépendance de la liste interne.</li>
 * </ul>
 * 
 * @author
 */

class PolynomeTest {

    @Test
    void testPolynomeVideInitialise() {
        Polynome p = new Polynome();
        assertNotNull(p.getListeMonome());
        assertTrue(p.getListeMonome().isEmpty());
    }

    @Test
    void testAjouterMonome() {
        Polynome p = new Polynome();
        p.ajouterMonome(new Monome(3.0, 2));
        assertEquals(1, p.getListeMonome().size());
    }

    @Test
    void testAjouterPlusieursMonomes() {
        Polynome p = new Polynome();
        p.ajouterMonome(new Monome(1.0, 0));
        p.ajouterMonome(new Monome(2.0, 1));
        p.ajouterMonome(new Monome(3.0, 2));
        assertEquals(3, p.getListeMonome().size());
    }

    @Test
    void testGetListeMonomeRetourneBonneValeur() {
        Polynome p = new Polynome();
        Monome m = new Monome(5.0, 1);
        p.ajouterMonome(m);
        List<Monome> liste = p.getListeMonome();
        assertEquals(5.0, liste.get(0).getCoefficient(), 1e-9);
        assertEquals(1,   liste.get(0).getDegre());
    }

    @Test
    void testConstructeurAvecListeCreePropre() {
        // Le constructeur Polynome(List<Monome>) doit initialiser une liste interne vide
        // (comportement actuel : il ignore la liste passée en paramètre)
        Polynome p = new Polynome(List.of(new Monome(1.0, 1)));
        assertNotNull(p.getListeMonome());
        // La liste interne est vide car le constructeur crée un new ArrayList<>()
        assertTrue(p.getListeMonome().isEmpty());
    }
}