/* 
 * Monome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
package src.polynome.model;

/**
 * Creation du monome
 * 
 * @autor 
 */
public class Monome {
    private int degre;
    private double coefficient;

    public int getDegre() {
        return this.degre;
    }
    
    public double getCoefficient() {
        return this.coefficient;
    }

    @Override
    public String toString() {
        return coefficient + "X^" + degre;
    }
}
