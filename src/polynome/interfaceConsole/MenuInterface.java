/* * MenuInterface.java
 * * IUT de Rodez, no copy right/left    2026
 */
package polynome.interfaceConsole;

import java.util.ArrayList;
import java.util.Scanner;
import polynome.model.Polynome;
import polynome.model.CreationPolynome;

/**
 * Permet d'afficher l'interface en ligne de commande et de
 * gerer les intéractions avec l'utilisateur.
 */
public class MenuInterface {
	/** Message d'erreur pour prévenir d'une entrée invalide */
    private static final String ERREUR_ENTREE_INVALIDE = 
                                "Erreur : l'entrée saisie est invalide, veuillez réessayer.";
    /** Message d'erreur pour prévenir d'une liste de taille invalide  */
    private static final String ERREUR_TAILLE_INVALIDE = 
            "Erreur : la taille des listes de multiplicité et racines doivent être de même taille.";

    private Scanner scanner;
    private Polynome polynome;
    private boolean ok;
    
    private ArrayList<Polynome> listePolynomeEnregistre = new ArrayList<>();

    public MenuInterface() {
        scanner = new Scanner(System.in);
        ok = true;
        polynome = new Polynome();
    }
    
    /**
     * Lance l'interface utilisateur en console.
     * Cette méthode affiche un menu de selection qui propose les
     * actions liée au polynome et s'occupe de la gestion des entrées 
     * utilisateur et potentiels cas d'erreurs.
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
            
            String saisie = scanner.nextLine();
            int selection;
            
            try {
                selection = Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                selection = -1;
            }
            
            switch (selection) {
                case 1:
                    System.out.println("-> Lancement de la création...");
                    choixCreation();
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
     * Permet la création d'une liste donnée par l'utilisateur.
     * S'occupe de la gestion des entrée et des potentiels cas d'erreurs
     * @param message message destiné à l'utilisateur
     * @return liste
     */
    private ArrayList<Double> saisirListe(String message) {
        ArrayList<Double> liste = new ArrayList<>();
        System.out.println(message + " (ou 'exit' pour terminer)");
        
        while (true) {
            System.out.print("=> ");
            String entree = scanner.nextLine().trim();
            
            if (entree.equalsIgnoreCase("exit")) { 
                break;
            }
            
            try {
                double valeur = Double.parseDouble(entree);
                liste.add(valeur);
            } catch (NumberFormatException e) {
                System.out.println(ERREUR_ENTREE_INVALIDE);
            }
        }
        return liste;
    }
    
    /**
     * Sous interface de la partie "Création d'un polynome".
     * S'occupe de l'affichage en console qui permet la création des polynomes
     * en plus de gerer les entrées utilisateur et potentiels cas d'erreurs.
     */
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
            
            String saisie = scanner.nextLine();
            int selectionCreation;
            
            try {
                selectionCreation = Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
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
                    
                    double coefDominant;
                    try {
                        coefDominant = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(ERREUR_ENTREE_INVALIDE);
                        break;
                    }
                    
                    ArrayList<Double> listeRacines = saisirListe("Veuillez indiquer la liste des racines.");  
                    
                    System.out.println("Veuillez indiquer la liste des multiplicités correspondantes.");
                    System.out.println("(Saisissez 'exit' pour terminer)");
                    
                    ArrayList<Integer> listeMultiplicite = new ArrayList<>();  
                    boolean isFini = false;
                    while(!isFini) {
                        System.out.print("=> ");
                        String entree = scanner.nextLine().trim();
                        
                        if (entree.equalsIgnoreCase("exit")) {
                            isFini = true;
                        } else {
                            try {
                                int mult = Integer.parseInt(entree);
                                listeMultiplicite.add(mult);
                            } catch (NumberFormatException e) {
                                System.out.println(ERREUR_ENTREE_INVALIDE);
                            }
                        }
                    }
                    
                    if (listeMultiplicite.size() == listeRacines.size()) {
                        polynome = CreationPolynome.creationPolynomeRacines(listeRacines, listeMultiplicite, coefDominant);
                        listePolynomeEnregistre.add(polynome);
                        System.out.println(polynome.toString());
                    } else {
                        System.out.println(ERREUR_TAILLE_INVALIDE);
                    }
                    break;
                    
                case 0:
                    checkRetour = false;
                    break;
                default:
                    System.out.println(ERREUR_ENTREE_INVALIDE);
            }
        }
    }
    
    /**
     * Sous interface de la partie "Affichage d'un polynome".
     * S'occupe de l'affichage en console qui permet l'Affichage des polynomes
     * enregistrées en plus de gerer les entrées utilisateur et potentiels cas d'erreurs.
     */
    public void AffichagePolynomes(){
        if(listePolynomeEnregistre.isEmpty()) { 
            System.out.println("Aucun polynôme n'a été créé.");
        } else {
            for (Polynome p : listePolynomeEnregistre) {
                System.out.println(p.toString());
            }
        }
    }
}