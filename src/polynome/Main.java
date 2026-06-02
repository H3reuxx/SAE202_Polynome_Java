/* 
 * Main.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */

package polynome;

import java.util.ArrayList;
import java.util.List;

import polynome.interfaceConsole.MenuInterface;
import static polynome.resolutions.SolveurPolynome.resoudreDirect;
import static polynome.model.CreationPolynome.CreationPolynomeCoefficients;
import polynome.calculs.CalculateurPolynome;
import polynome.model.CreationPolynome;
import polynome.resolutions.SolveurPolynome;
import polynome.model.Polynome;

/**
 * TODO
 * 
 * @autor 
 */

class Main {

    public static void main(String[] args) {
    	MenuInterface menu = new MenuInterface();
    	System.out.println("=== TEST DE LA DIVISION EUCLIDIENNE ===\n");

        // ---------------------------------------------------------
        // TEST 1 : Avec reste
        // A(X) = 2X^3 - 2X^2 - 4 (coeff : -4, 0, -2, 2)
        // B(X) = X - 3           (coeff : -3, 1)
        // ---------------------------------------------------------
        Polynome dividende1 = CreationPolynome.CreationPolynomeCoefficients(List.of(-4.0, 0.0, -2.0, 2.0));
        Polynome diviseur1 = CreationPolynome.CreationPolynomeCoefficients(List.of(-3.0, 1.0));
        
        System.out.println("--- Test 1 ---");
        System.out.println("Dividende A(X) : " + dividende1);
        System.out.println("Diviseur  B(X) : " + diviseur1);
        
        List<Polynome> resultat1 = CalculateurPolynome.diviserPolynomes(dividende1, diviseur1);
        
        System.out.println("Quotient  Q(X) : " + resultat1.get(0));
        System.out.println("Reste     R(X) : " + resultat1.get(1));
        System.out.println("Attendu        : Q(X) = 2.0X^2 + 4.0X + 12.0 | R(X) = 32.0\n");


        // ---------------------------------------------------------
        // TEST 2 : Division exacte (Reste nul)
        // A(X) = X^2 - 4  (coeff : -4, 0, 1)
        // B(X) = X - 2    (coeff : -2, 1)
        // ---------------------------------------------------------
        Polynome dividende2 = CreationPolynome.CreationPolynomeCoefficients(List.of(-4.0, 0.0, 1.0));
        Polynome diviseur2 = CreationPolynome.CreationPolynomeCoefficients(List.of(-2.0, 1.0));

        System.out.println("--- Test 2 ---");
        System.out.println("Dividende A(X) : " + dividende2);
        System.out.println("Diviseur  B(X) : " + diviseur2);
        
        List<Polynome> resultat2 = CalculateurPolynome.diviserPolynomes(dividende2, diviseur2);
        
        System.out.println("Quotient  Q(X) : " + resultat2.get(0));
        System.out.println("Reste     R(X) : " + resultat2.get(1));
        System.out.println("Attendu        : Q(X) = 1.0X + 2.0 | R(X) = 0");
    }
}