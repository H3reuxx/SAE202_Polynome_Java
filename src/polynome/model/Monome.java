/* 
 * Monome.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
package polynome.model;

import java.util.Objects;

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

    public static String signe (double nombre) {
        if (nombre > 0) {
        	return "+";
        } else if (nombre < 0) {
        	return "-";
        } else return "0";
    }
    
    public int getDegre() {
        return this.degre;
    }
    
    public double getCoefficient() {
        return this.coefficient;
    }
    
    public void setCoefficient(double nouveauCoefficient) {
        this.coefficient = nouveauCoefficient;
    }
    public String getSigne() {
		    return signe(coefficient);
	  }
    
    
    @Override
    public String toString() {
    	//TODO Exeption pour les monomes de degre 0 et 1   	
    	if (degre == 0) {
    		return Double.toString(coefficient);
    	}
    	if (degre == 1) {
    		return Math.abs(coefficient) + "X";
    	}
        return Math.abs(coefficient) + "X^" + degre;
    }
    
    @Override
    public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Monome monome = (Monome) obj;
		return degre == monome.degre && Double.compare(monome.coefficient, coefficient) == 0;
	}
    
    @Override
    public int hashCode() {
		return Objects.hash(degre, coefficient);
	}
}