/* 
 * MenuInterface.java
 * 
 * IUT de Rodez, no copy right/left    26/04
 */
package polynome.interfaceConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import polynome.model.Polynome;

import polynome.model.CreationPolynome;

/**
 * Permet d'afficher l'interface en ligne de commande et de
 * gerer les intéractions avec l'utilisateur.
 * 
 * @author 
 */
public class MenuInterface {
    private static final String ERREUR_ENTREE_INVALIDE = 
                                "Erreur : l'entrée saisie est invalide, veuillez réessayer.";
    private static final String ERREUR_TAILLE_INVALIDE = 
            "Erreur : la taille des listes de multiplicité et racines doivent être de même taille.";

    private Scanner scanner;
    private boolean ok;
    private Polynome polynome;
    
    private ArrayList<Polynome> listePolynomeEnregistre = new ArrayList<>();

    public MenuInterface() {
        scanner = new Scanner(System.in);
        ok = true;
        polynome = new Polynome();
    }

    /**
     * Méthode principale qui lance et gère la boucle du menu
     */
    public void lancerMenu() {

        final String AFFICHAGEMENU = """
                ==== MENU POLYNÔMES ====
                1 : Création d'un polynome
                2 : Affichage d'un polynome
                3 : Calculs liée au polynome
                0 : Quitter
                """;

        while (ok) {
            System.out.println(AFFICHAGEMENU);
            System.out.print("Votre choix : ");
            
            int selection;
            String saisie = scanner.nextLine();
            
            try {
            	selection = Integer.parseInt(saisie);
            } catch (IllegalArgumentException erreur) {
            	selection = -1;
            }
            
            switch (selection) {
                //TODO Case 3 , 4 , 5
                case 1:
                    System.out.println("-> Lancement de la création...");
                    choixCreation(); //Menu pour choisir le moyen de création.
                    break;
                case 2:
                    System.out.println("-> Affichage...");
                    AffichagePolynomes();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    ok = false;
                    break;
                default:
                    System.out.println(ERREUR_ENTREE_INVALIDE);
            }
            System.out.println();
        }
    }
    
    /**
     * Méthode utilitaire pour saisir une liste de nombres en boucle.
     */
    private ArrayList<Double> saisirListe(String message) {
    	ArrayList<Double> liste = new ArrayList<>();
        System.out.println(message + " (ou 'exit' pour terminer)");
        
        while (true) {
            System.out.print("=> ");
            if (scanner.hasNextDouble()) {
                liste.add(scanner.nextDouble());
            } else {
                String entree = scanner.next();
                if (entree.equalsIgnoreCase("exit")) { 
                	break;
                }
                System.out.println(ERREUR_ENTREE_INVALIDE);
            }
        }
        return liste;
    }
    
    public void choixCreation(){
        final String choixCreationPolyonme = """
                1 : Creation par coefficients.
                2 : Creation par racines.
                0 : Retour.
                """;
        
        boolean checkRetour = true;
        while (checkRetour) {
            System.out.println(choixCreationPolyonme);
            System.out.print("Votre choix : ");
            
        	int selectionCreation;
        	String saisie = scanner.nextLine();
            
            try {
            	selectionCreation = Integer.parseInt(saisie);
            } catch (IllegalArgumentException erreur) {
            	selectionCreation = -1;
            }
        	
	        switch (selectionCreation) {
	            case 1: 
	                ArrayList<Double> listeCoefficients = saisirListe("Veuillez indiquer vos coefficients dans l'ordre des degrès croissant.");
	                polynome = CreationPolynome.CreationPolynomeCoefficients(listeCoefficients);
	                listePolynomeEnregistre.add(polynome);
	                System.out.println(polynome.toString());
	                break;
	            case 2:
	                System.out.println("Veuillez indiquer votre coefficient dominant.");
	                System.out.print("=> ");
	                
	                boolean erreur_exit = false;
	                double coefDominant = 0;
	                if (scanner.hasNextDouble()) {
	                	coefDominant = scanner.nextInt();
	                } else {
	                	System.out.println(ERREUR_ENTREE_INVALIDE);
	                	scanner.next();
	                	erreur_exit = true;
	                }
	                
	                if (erreur_exit) break;
	                
	                ArrayList<Double> listeRacines = saisirListe("Veuillez indiquer la liste des multiplicitées.");  
	                
	                System.out.println("Veuillez indiquer la liste des racines.");
	                System.out.println("Pour quitter/finir la liste saisir 'exit' ");
	            	
	            	boolean isFini = false;
	            	
	                ArrayList<Integer> listeMultiplicite = new ArrayList<Integer>();  
	                while(!isFini) {
	                	System.out.print("=> ");
	                    if (scanner.hasNextInt()) {
	                    	listeMultiplicite.add(scanner.nextInt());
	                    } else if (scanner.next().trim().equalsIgnoreCase("exit")) {
	                    	isFini = true;
	                    	checkRetour = false;
	                    } else {
	                        System.out.println(ERREUR_ENTREE_INVALIDE);
	                    }
	                }
	                
	                if (listeMultiplicite.size() == listeRacines.size()) {
	                	polynome = CreationPolynome.creationPolynomeRacines(listeRacines, listeMultiplicite, coefDominant);
	                	listePolynomeEnregistre.add(polynome);
	                	System.out.print(polynome.toString());
	                } else {
	                	System.out.print(ERREUR_TAILLE_INVALIDE);
	                }
	                break;
	            case 0:
	                System.out.println();
	                checkRetour = false;
	                break;
	            default:
	                System.out.println(ERREUR_ENTREE_INVALIDE);
	        }
        }
        System.out.println();
    }

    public void AffichagePolynomes(){
        if(listePolynomeEnregistre.size() == 0 ) { System.out.print("Aucun polynome na été crée.");}
        else {
            for (Polynome polynome : listePolynomeEnregistre) {
                System.out.println(polynome.toString());
            }
        }
    }

    public void CalculsPolynomes(){
        //TODO
    }
}