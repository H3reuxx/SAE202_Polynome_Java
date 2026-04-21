/* 
 * Polynome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
 
package src.polynome.model;

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

    private int racineReel;
    private List<Monome> listeMonome;
                       
    public Polynome(List<Monome> listeMonome) {
        this.listeMonome = listeMonome;
    }


    /*
    TODO : retourne le degre du monome le 
    plus haut. (dans la liste)
    */
    public int getMaxDegre() {

    }

    @Override
    public String toString() {
    
    }
}