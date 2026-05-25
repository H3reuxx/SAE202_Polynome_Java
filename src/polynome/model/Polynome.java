/* 
* Polynome.java
* 
* IUT de Rodez, no copy right/left    10/04
*/

package polynome.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    
    //Méthode permettant d'ajouter un monome à un polynome.
    public void ajouterMonome(Monome m) {
        boolean degreTrouve = false;
        
        for (Monome monome : this.listeMonome) {
            if (monome.getDegre() == m.getDegre()) {
                double nouveauCoeff = monome.getCoefficient() + m.getCoefficient();
                monome.setCoefficient(nouveauCoeff);
                degreTrouve = true;
                break; //
            }
        }
        if (!degreTrouve) {
            this.listeMonome.add(m);
        }
    }
    
    /**
     * Calcule la dérivée d'un polynome en dérivant chaque monome individuellement.
     * @return le polynome dérivé de ce polynome
     */
    public Polynome deriver() {
        Polynome derivee = new Polynome();
        
        for (Monome m : this.listeMonome) {
            if (m.getDegre() > 0) {
                double nouveauCoef = m.getCoefficient() * m.getDegre();
                int nouveauDegre = m.getDegre() - 1;
                derivee.ajouterMonome(new Monome(nouveauCoef, nouveauDegre));
            }
        }
        return derivee;
    }
    
    /**
     * Calcule la primitive d'un polynome en intégrant chaque monome individuellement.
     * @param constanteC
     * @return le polynome primitif de ce polynome avec la constante d'integration C
     */
    public Polynome integrer(double constanteC) {
        Polynome primitive = new Polynome();
        
        for (Monome m : this.listeMonome) {
            double nouveauCoef = m.getCoefficient() / (m.getDegre() + 1);
            int nouveauDegre = m.getDegre() + 1;
            primitive.ajouterMonome(new Monome(nouveauCoef, nouveauDegre));
        }
        if (constanteC != 0) {
            primitive.ajouterMonome(new Monome(constanteC, 0));
        }
        return primitive;    
    }
    
    
    public List<Monome> getListeMonome() {
        return this.listeMonome;
    }
    
    @Override
    public String toString() {
    	String affichage = "";
    	
    	for(int indice = this.listeMonome.size()-1 ; indice >= 0; indice-- ) {
    		if (indice == 0) {
    			affichage += this.listeMonome.get(indice).toString();
    			return affichage;
    		}
			affichage += listeMonome.get(indice).toString() + " " + this.listeMonome.get(indice-1).getSigne() + " ";
		}
    	return affichage;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Polynome other = (Polynome) obj;
        return Objects.equals(this.listeMonome, other.listeMonome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listeMonome);
    }
}
