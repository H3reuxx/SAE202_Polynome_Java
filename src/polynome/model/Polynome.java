/* 
* Polynome.java
* 
* IUT de Rodez, no copy right/left    10/04
*/

package polynome.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Création d'un objet polynome représenté par une liste de monome.
 * @author Gabriel TOLL , Alban ROZES , Wassim NAFAA , Erwan NAZAL
 * @version 1.0
 */

public class Polynome {
    
    private List<Monome> listeMonome;
    
    /**
     * Constructeur du polynome
     * @param listeMonome La liste des monomes du polynome
     */
    public Polynome(List<Monome> listeMonome) {
        this.listeMonome = new ArrayList<Monome>(listeMonome); 
    }
    
    /**
     * initialisation du polynome nul.
     */
    public Polynome(){
        Monome monomeNul = new Monome(0.0 , 0);
        this.listeMonome = new ArrayList<Monome>(List.of(monomeNul));  
    }
    
    //Methode permettant d'ajouter un monome a un polynome.
    public void ajouterMonome(Monome m) {
        for (Monome monome : this.listeMonome) {
            if (monome.getDegre() == m.getDegre()) {
                double nouveauCoeff = monome.getCoefficient() + m.getCoefficient();
                monome.setCoefficient(nouveauCoeff);
            }
            return;
        }
    }

    public List<Monome> getListeMonome() {
        return this.listeMonome;
    }

  
    /*
    TODO : retourne le degre du monome le 
    plus haut. (dans la liste)
    */
    /*
    public int getMaxDegre() {
	*/
    
    @Override
    public String toString() {
    	String affichage = "";
    	for (Monome monome : getListeMonome()) {
    		affichage += monome.toString() + " "; 
    	}
    	
    	return affichage;
    }

}
