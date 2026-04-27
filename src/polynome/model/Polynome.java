/* 
* Polynome.java
* 
* IUT de Rodez, no copy right/left    10/04
*/

package polynome.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Création de la fiche d'indentité du polynome 
 * 
 * @autor 
 */

public class Polynome {

    /** 
     * prise des informations du polynome 
     * Son degré
     * Ses Coefficients
     * 
     * param args utilisé 
     */ 

    private List<Monome> listeMonome;
    
    //Créatiobn d'un polynome a partire d'une liste de monome.
    public Polynome(List<Monome> listeMonome) {
        this.listeMonome = new ArrayList<>(); 
    }
    //Overload de la class polynome pour permettre l'inisialisation du polynome vide.
    public Polynome(){
        this.listeMonome = new ArrayList<>();
    }
    //Methode permettant d'ajouter un monome a un polynome.
    public void ajouterMonome(Monome m) {
        this.listeMonome.add(m);
        //TODO , Ajouter des cas d'erreur , par exemple ,
        //TODO on ne peut pas avoir deux monome du meme degres , on doit donc mettre des verifications
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

    }

    @Override
    public String toString() {
    
    }
    */
}