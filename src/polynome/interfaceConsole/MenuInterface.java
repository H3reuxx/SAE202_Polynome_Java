/* * MenuInterface.java
 * * IUT de Rodez, no copy right/left    2026
 */
package polynome.interfaceConsole;

import java.util.ArrayList;
import java.util.Scanner;
import polynome.model.Polynome;
import polynome.model.CreationPolynome;
import polynome.calculs.CalculateurPolynome;
import polynome.calculs.CalculateurRacines;

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
                3 : Calculs liée au polynome (Addition, Multiplication, Scalaire)
                4 : Transformation de polynome (Dérivée / Primitive)
                5 : Recherche de racines (Sturm / Newton)
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
                    System.out.println("-> Lancement de la création...\n");
                    choixCreation();
                    break;
                case 2:
                    System.out.println("-> Affichage...\n");
                    affichagePolynomes();
                    System.out.println(); // Saut de ligne après l'affichage global
                    break;
                case 3:
                    System.out.println("-> Lancement des calculs...\n");
                    calculsPolynomes();
                    break;
                case 4:
                    System.out.println("-> Lancement des transformations...\n");
                    transfoPolynomes();
                    break;
                case 5:
                    System.out.println("-> Lancement de la recherche de racines...\n");
                    menuRacines();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    ok = false;
                    break;
                default:
                    System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
            }
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
    private void choixCreation(){
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
                    if (!listeCoefficients.isEmpty()) {
                        polynome = CreationPolynome.CreationPolynomeCoefficients(listeCoefficients);
                        listePolynomeEnregistre.add(polynome);
                        System.out.println("\nPolynôme créé : " + polynome.toString() + "\n");
                    } else {
                        System.out.println("\nAucun polynôme n'a été créé.\n");
                    }
                    break;
                    
                case 2:
                    System.out.println("Veuillez indiquer votre coefficient dominant.");
                    System.out.print("=> ");
                    
                    double coefDominant;
                    try {
                        coefDominant = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
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
                        System.out.println("\nPolynôme créé : " + polynome.toString() + "\n");
                    } else {
                        System.out.println("\n" + ERREUR_TAILLE_INVALIDE + "\n");
                    }
                    break;
                    
                case 0:
                    checkRetour = false;
                    break;
                default:
                    System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
            }
        }
    }
    
    /**
     * Sous interface de la partie "Affichage d'un polynome".
     * S'occupe de l'affichage en console qui permet l'Affichage des polynomes
     * enregistrées en plus de gerer les entrées utilisateur et potentiels cas d'erreurs.
     */
    private void affichagePolynomes(){
        if(listePolynomeEnregistre.isEmpty()) { 
            System.out.println("Aucun polynôme n'a été créé.");
        } else {
            for (int choixPolynome = 0; choixPolynome < listePolynomeEnregistre.size(); choixPolynome++) {
                System.out.println((choixPolynome + 1) + ") " + listePolynomeEnregistre.get(choixPolynome));
            }
        }
    }

    private int[] recupererIDPolynome() {
        int idPolynome1 = -1;
        int idPolynome2 = -1;

        System.out.print("Veuillez saisir l'id du premier polynome : ");
        String s1 = scanner.nextLine();
        try {
            idPolynome1 = Integer.parseInt(s1);
        } catch (NumberFormatException e) {
            System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
            return new int[]{-1, -1};
        }
        
        System.out.print("Veuillez saisir l'id du deuxième polynome : ");
        String s2 = scanner.nextLine();
        try {
            idPolynome2 = Integer.parseInt(s2);
        } catch (NumberFormatException e) {
            System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
            return new int[]{-1, -1};
        }

        return new int[]{idPolynome1, idPolynome2};
    }
    
    private void calculsPolynomes() {
        final String CHOIX_CALCULS_POLYNOME = """
                    1 : Addition de deux polynomes.
                    2 : Multiplication de deux polynomes. 
                    3 : Multiplication d'un polynome par un scalaire.
                    0 : Retour.
                    """;
        boolean checkRetour = true;
        
        while (checkRetour) {
            affichagePolynomes(); 
            System.out.print("\n" + CHOIX_CALCULS_POLYNOME);
            System.out.print("Votre choix : ");
            
            String saisie = scanner.nextLine();
            int selection;
            
            try {
                selection = Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                selection = -1;
            }
            
            Polynome polynomeFinal;
            switch (selection) {
                case 1:
                    int[] polynomesAddition = recupererIDPolynome();
                    if(polynomesAddition[0] >= 1 && polynomesAddition[1] >= 1 && polynomesAddition[0] <= listePolynomeEnregistre.size() && polynomesAddition[1] <= listePolynomeEnregistre.size()) {
                        polynomeFinal = CalculateurPolynome.additionnerPolynome(listePolynomeEnregistre.get(polynomesAddition[0]-1), listePolynomeEnregistre.get(polynomesAddition[1]-1));
                        listePolynomeEnregistre.add(polynomeFinal);
                        System.out.println("\nRésultat addition : " + polynomeFinal.toString() + "\n");
                    } else {
                        System.out.println("\n" + ERREUR_ENTREE_INVALIDE + "\n");
                    }
                    break;
                case 2:
                    int[] polynomesMultiplication = recupererIDPolynome();
                    if(polynomesMultiplication[0] >= 1 && polynomesMultiplication[1] >= 1 && polynomesMultiplication[0] <= listePolynomeEnregistre.size() && polynomesMultiplication[1] <= listePolynomeEnregistre.size()) {
                        polynomeFinal = CalculateurPolynome.multiplierPolynomes(listePolynomeEnregistre.get(polynomesMultiplication[0]-1), listePolynomeEnregistre.get(polynomesMultiplication[1]-1));
                        listePolynomeEnregistre.add(polynomeFinal);
                        System.out.println("\nRésultat multiplication : " + polynomeFinal.toString() + "\n");
                    } else {
                        System.out.println("\n" + ERREUR_ENTREE_INVALIDE + "\n");
                    }
                    break;
                case 3:
                    System.out.print("Veuillez saisir l'id du polynome à multiplier : ");
                    String sId = scanner.nextLine();
                    int idPolynome;
                    try {
                        idPolynome = Integer.parseInt(sId);
                    } catch (NumberFormatException e) {
                        System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                        break;
                    }

                    if(idPolynome >= 1 && idPolynome <= listePolynomeEnregistre.size()) {
                        System.out.print("Veuillez saisir votre scalaire : ");
                        String sScalaire = scanner.nextLine();
                        double scalaire;
                        try {
                            scalaire = Double.parseDouble(sScalaire);
                            polynomeFinal = listePolynomeEnregistre.get(idPolynome-1);
                            Polynome reponse = CalculateurPolynome.multiplierPolynomeAvecScalaire(polynomeFinal, scalaire);
                            listePolynomeEnregistre.add(reponse);
                            System.out.println("\nRésultat scalaire : " + reponse.toString() + "\n");
                        } catch (NumberFormatException e) {
                            System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                        }
                    } else {
                        System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                    }
                    break;
                case 0:
                    checkRetour = false;
                    break;
                default:
                    System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
            }
        }
    }
    
    private void transfoPolynomes() {
        final String CHOIX_TRANSFO_POLYNOME = """
                1 : Integrer le polynome (primitive).
                2 : Deriver le polynome.
                0 : Retour.
                """ ;
        
        boolean checkRetour = true;

        while (checkRetour) {
            affichagePolynomes(); 
            System.out.print("\n" + CHOIX_TRANSFO_POLYNOME);
            System.out.print("Votre choix : ");
            
            String saisie = scanner.nextLine();
            int selection;
            
            try {
                selection = Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                selection = -1;
            }
            
            Polynome polynomeFinal;
            switch (selection) {
                case 1:
                    System.out.print("Veuillez saisir l'id du polynome dont vous souhaitez la primitive : ");
                    String sIdInt = scanner.nextLine();
                    int idPolynomeInt;
                    try {
                        idPolynomeInt = Integer.parseInt(sIdInt);
                    } catch (NumberFormatException e) {
                        System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                        break;
                    }

                    if(idPolynomeInt >= 1 && idPolynomeInt <= listePolynomeEnregistre.size()) {
                        System.out.print("Veuillez saisir la constante d'integration (C) : ");
                        String sC = scanner.nextLine();
                        try {
                            double c = Double.parseDouble(sC);
                            polynomeFinal = listePolynomeEnregistre.get(idPolynomeInt-1).integrer(c);
                            listePolynomeEnregistre.add(polynomeFinal);
                            System.out.println("\nPrimitive obtenue : " + polynomeFinal.toString() + "\n");
                        } catch (NumberFormatException e) {
                            System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                        }
                    } else {
                        System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                    }
                    break;
                case 2:
                    System.out.print("Veuillez saisir l'id du polynome à dériver : ");
                    String sIdDer = scanner.nextLine();
                    int idPolynomeDer;
                    try {
                        idPolynomeDer = Integer.parseInt(sIdDer);
                    } catch (NumberFormatException e) {
                        System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                        break;
                    }

                    if(idPolynomeDer >= 1 && idPolynomeDer <= listePolynomeEnregistre.size()) {
                        polynomeFinal = listePolynomeEnregistre.get(idPolynomeDer-1).deriver();
                        listePolynomeEnregistre.add(polynomeFinal);
                        System.out.println("\nDérivée obtenue : " + polynomeFinal.toString() + "\n");
                    } else {
                        System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
                    }
                    break;
                case 0:
                    checkRetour = false;
                    break;
                default:
                    System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
            }
        }
    }

    /**
     * Sous-menu dédié à l'analyse et à la recherche de racines locales ou globales.
     */
    private void menuRacines() {
        final String CHOIX_RACINES = """
                1 : Nombre de racines dans [a, b] (Suite de Sturm).
                2 : Calcul d'une racine par approximation (Méthode de Newton).
                0 : Retour.
                """;
        boolean checkRetour = true;

        while (checkRetour) {
            affichagePolynomes();
            System.out.print("\n" + CHOIX_RACINES);
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
                    System.out.print("Veuillez saisir l'id du polynome : ");
                    String sIdSturm = scanner.nextLine();
                    try {
                        int id = Integer.parseInt(sIdSturm);
                        if (id >= 1 && id <= listePolynomeEnregistre.size()) {
                            Polynome p = listePolynomeEnregistre.get(id - 1);
                            
                            System.out.print("Saisir la borne MIN (a) : ");
                            double min = Double.parseDouble(scanner.nextLine());
                            
                            System.out.print("Saisir la borne MAX (b) : ");
                            double max = Double.parseDouble(scanner.nextLine());

                            int nbRacines = CalculateurRacines.suiteSturm(max, min, p);
                            System.out.println("\n[Sturm] Nombre de racine(s) distincte(s) dans [" + min + ", " + max + "] : " + nbRacines + "\n");
                        } else {
                            System.out.println("\n" + ERREUR_ENTREE_INVALIDE + "\n");
                        }
                    } catch (Exception e) {
                        System.out.println("\nErreur durant l'analyse de Sturm : " + e.getMessage() + "\n");
                    }
                    break;

                case 2:
                    System.out.print("Veuillez saisir l'id du polynome : ");
                    String sIdNewton = scanner.nextLine();
                    try {
                        int id = Integer.parseInt(sIdNewton);
                        if (id >= 1 && id <= listePolynomeEnregistre.size()) {
                            Polynome p = listePolynomeEnregistre.get(id - 1);
                            
                            System.out.print("Saisir le point de départ de l'approximation (x0) : ");
                            double x0 = Double.parseDouble(scanner.nextLine());

                            // Un saut de ligne avant le suivi des itérations de Newton pour la clarté
                            System.out.println();
                            double racineApprochee = CalculateurRacines.newton(p, x0);
                            System.out.println("\n[Newton] Racine trouvée à partir de x0 = " + x0 + " -> x ≈ " + racineApprochee + "\n");
                        } else {
                            System.out.println("\n" + ERREUR_ENTREE_INVALIDE + "\n");
                        }
                    } catch (Exception e) {
                        System.out.println("\nErreur d'approximation : " + e.getMessage() + "\n");
                    }
                    break;

                case 0:
                    checkRetour = false;
                    break;
                default:
                    System.out.println(ERREUR_ENTREE_INVALIDE + "\n");
            }
        }
    }
}