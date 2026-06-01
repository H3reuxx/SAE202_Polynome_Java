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
    	
        Polynome p1 = CreationPolynome.CreationPolynomeCoefficients(List.of(-7.0, 3.0, -5.0, 2.0));
        System.out.println(p1.toString());
        
        System.out.println(SolveurPolynome.resoudreDirect(p1, 2));
    }
}