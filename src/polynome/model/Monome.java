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
          
    public int getDegre() {
        return this.degre;
    }
    
    public double getCoefficient() {
        return this.coefficient;
    }
    
    public void setCoefficient(double nouveauCoefficient) {
        this.coefficient = nouveauCoefficient;
    }
    
    @Override
    public String toString() {
        return coefficient + "X^" + degre;
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