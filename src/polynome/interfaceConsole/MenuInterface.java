/* 
 * MenuInterface.java
 * 
 * IUT de Rodez, no copy right/left    26/04
 */
package polynome.interfaceConsole;

import java.util.Scanner;
import polynome.model.Polynome;

/**
 * Permet d'afficher l'interface en ligne de commande et de
 * gerer les intéractions avec l'utilisateur.
 * 
 * @author 
 */
public class MenuInterface {
    private Scanner scanner;
    private boolean ok = true; 

    public MenuInterface() {
        scanner = new Scanner(System.in);
    }

    /**
     * Méthode principale qui lance et gère la boucle du menu
     */
    public void lancerMenu() {

        final String AFFICHAGEMENU = """
                === MENU POLYNÔMES ===
                1 : Création d'un polynome
                2 : Affichage d'un polynome
                3 : Sauvegarder un polynome
                4 : Charger un polynome
                5 : Afficher la courbe d'un polynome
                0 : Quitter
                """;

        while (ok) {
            System.out.println(AFFICHAGEMENU);
            System.out.print("Votre choix : ");
            
            int selection = scanner.nextInt();
            
            switch (selection) {
                //TODO Case 3 , 4 , 5
                case 1:
                    System.out.println("-> Lancement de la création...");
                    choixCreation(); //Menu pour choisir le moyen de création.
                    break;
                case 2:
                    System.out.println("-> Affichage...");
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    ok = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
            System.out.println();
        }
    }
    
    public /*Polynome*/void choixCreation(){
        final String choixCreationPolyonme = """
                1 : Creation par coefficients.
                2 : Creation par racines.
                """;

        int selectionCreation = 0;
        switch (selectionCreation){
            case 1:
                System.out.println("Veuillez indiquer vos coefficients");
                //TODO Recuperer l'entree pour le convertir en liste de coefs.
                break;
            case 2:
                System.out.println("Veuillez indiquer vos racines reeles , le coefficients de plus haut" +
                                    "degrès ainsi que ");
                //TODO Recuperer les differentes entrées. 
                break;
        }
  
    }
    public /*Polynome*/ void AffichagePolynomes(){
        //TODO faire un stack pour les polynomes crée et les afficher avec cette methode.
    }

}