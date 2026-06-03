/* 
 * Main.java
 * IUT de Rodez, no copy right/left
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
 * classe principale du projet , elle contient la méthode main qui lance le menu de l'application.
 * @autor gabriel TOLL , Alban ROZES , Erwan NAZAL
 */

class Main {
	
    public static void main(String[] args) {
    	MenuInterface menu = new MenuInterface();
    	menu.lancerMenu();
    }
}