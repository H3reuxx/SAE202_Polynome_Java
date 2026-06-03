/* 
 * Monome.java
 * IUT de Rodez, no copy right/left 
 */
package polynome.model;

import java.util.Objects;
import java.text.DecimalFormat;

/**
 * Creation du monome avec un coefficient et un degre.
 * 
 * @autor gabriel TOLL , Alban ROZES , Erwan NAZAL
 */
public class Monome {
    private int degre;
    private double coefficient;

    public Monome(double coefficient, int degre) {
        this.coefficient = coefficient;
        this.degre = degre;
    }   
    
    /**
     * Méthode utilitaire pour déterminer le signe d'un nombre, elle retourne "+" si le nombre est positif, "-" si le nombre est négatif et "0" si le nombre est nul.
     * @param nombre
     * @return le signe du nombre sous forme de String
     */
    public static String signe (double nombre) {
        if (nombre > 0) {
        	return "+";
        } else if (nombre < 0) {
        	return "-";
        } else return "0";
    }
    /** @return le degre du monome */
    public int getDegre() {
        return this.degre;
    }
    
    /** @return le coefficient du monome */
    public double getCoefficient() {
        return this.coefficient;
    }
    
    /** @return le signe du monome sous forme de String */
    public String getSigne() {
		    return signe(coefficient);
	}
    
    /** @param nouveauCoefficient le nouveau coefficient du monome */
    public void setCoefficient(double nouveauCoefficient) {
        this.coefficient = nouveauCoefficient;
    }
    
    @Override
    public String toString() {
        /* df sert a garder seulement 3 chiffres après la virgule si nécessaire */
        DecimalFormat df = new DecimalFormat("#0.###");
        String coeffFormate = df.format(Math.abs(coefficient));
        
        if (degre == 0) {
            return coeffFormate;
        } 
        if (degre == 1) {
            return coeffFormate + "X";
        } 
        
        return coeffFormate + "X^" + degre;
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