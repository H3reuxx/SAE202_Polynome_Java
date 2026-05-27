/* 
* Polynome.java
* 
* IUT de Rodez, no copy right/left    10/04
*/

package polynome.model;

import java.util.Arrays;
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
        ArrayList<Monome> derivee = new ArrayList<Monome>();
        
        //Polynome avec seulement une constante renvoie un polynome nul
        if (this.listeMonome.size() == 1 && this.listeMonome.get(0).getDegre() == 0) {
			return new Polynome();
		}
        
        for (Monome m : this.listeMonome) {
            if (m.getDegre() > 0) {
                double nouveauCoef = m.getCoefficient() * m.getDegre();
                int nouveauDegre = m.getDegre() - 1;
                derivee.add(new Monome(nouveauCoef, nouveauDegre));
            }
        }
        Polynome deriveePolynome = new Polynome(derivee);
        return deriveePolynome;
    }
    
    /**
     * Calcule la primitive d'un polynome en intégrant chaque monome individuellement.
     * @param constanteC
     * @return le polynome primitif de ce polynome avec la constante d'integration C
     */
    public Polynome integrer(double constanteC) {
        ArrayList<Monome> primitive = new ArrayList<Monome>(); 
        
        for (Monome m : this.listeMonome) {
            // On ignore le monôme nul 0x^0 lors de l'intégration pour éviter les fantômes
            if (m.getCoefficient() != 0 || m.getDegre() != 0) {
                primitive.add(new Monome(m.getCoefficient() / (m.getDegre() + 1), m.getDegre() + 1));
            }
        }
        // On ajoute la constante (elle devient le nouveau terme de degré 0)
        primitive.add(new Monome(constanteC, 0));
        Polynome primitivePolynome = new Polynome(primitive);
        return primitivePolynome;
    }
    
    public void TriInsertion(Polynome polynome) {
    	this.listeMonome = new ArrayList<Monome>(polynome.getListeMonome());
    	for (int i = 1; i < this.listeMonome.size(); i++) {
			Monome key = this.listeMonome.get(i);
			int j = i - 1;
			while (j >= 0 && this.listeMonome.get(j).getDegre() < key.getDegre()) {
				this.listeMonome.set(j + 1, this.listeMonome.get(j));
				j--;
			}
			this.listeMonome.set(j + 1, key);
		}
    }
    	
    public List<Monome> getListeMonome() {
        return this.listeMonome;
    }
    
    @Override
    public String toString() {
        // On trie d'abord le polynôme par puissances décroissantes
        TriInsertion(this);
        
        // Si le polynôme est vide ou ne contient que le monôme nul 0.0X^0
        if (this.listeMonome.isEmpty() || (this.listeMonome.size() == 1 && this.listeMonome.get(0).getCoefficient() == 0)) {
            return "0";
        }
        
        String affichage = "";
        for (int i = 0; i < this.listeMonome.size(); i++) {
            Monome monomeActuel = this.listeMonome.get(i);
            if (i == 0) {
                affichage += monomeActuel.toString();
            } else {
                if (monomeActuel.getCoefficient() >= 0) {
                    affichage += " + " + monomeActuel.toString();
                } else {
                    affichage += " " + monomeActuel.toString(); 
                }
            }
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
