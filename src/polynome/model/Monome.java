/* 
 * Monome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
package polynome.model;

/**
 * Creation du monome
 * 
 * @autor 
 */
public class Monome {
    private int degre;
    private double coefficient;

    public Monome(double coefficient, int degre) {
        this.coefficient = coefficient;
        this.degre = degre;
    }   
          
    public int getDegre() {
        return this.degre;
    }
    
    public double getCoefficient() {
        return this.coefficient;
    }
    
    public static String signe (double nombre) {
        if (nombre > 0) return "+";
        if (nombre < 0) return "-";
        return "0";
    }
    
  
    @Override
    public String toString() {
    	//TODO Exeption pour les monomes de degre 0 et 1
    	
    	String signe = signe(coefficient);
    	
    	if (degre == 0) {
    		return Double.toString(coefficient);
    	}
        return signe + " " + Math.abs(coefficient) + "X^" + degre;
    }
}