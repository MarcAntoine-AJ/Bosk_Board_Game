/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog2;

import ca.qc.bdeb.prog2.Soleil.Position;
import java.util.Scanner;

/**
 *
 * @author Marc-Antoine Abou Jaoude
 */
public class Arboretum { // Contient la table de jeu

    private Scanner clavier = new Scanner(System.in);
    private Arbre[][] arboretum = new Arbre[5][6];
    private Joueur joueur1 = new Joueur(0, 'a');
    private Joueur joueur2 = new Joueur(0, 'b');
    private Soleil soleil = new Soleil();
    private Joueur joueurCourant;
    private final int POSITION_INITIALE_SUD = arboretum.length - 1;
    private final int VARIATION_SUD = -1;
    private final int POSITION_INITIALE_OUEST = 0;
    private final int VARIATION_OUEST = 1;
    private final int POSITION_INITIALE_EST = arboretum[0].length - 1;
    private final int VARIATION_EST = -1;
    private final int POSITION_INITIALE_NORD = 0;
    private final int VARIATION_NORD = 1;

    public Arboretum() {
        arboretum[1][0] = new Arbre(1, joueur1);
        arboretum[3][5] = new Arbre(1, joueur2);
// juste pour verifier que sa marche
    } // Constructeur de l'arboretum avec les arbres initiaux

    private void cycleDeVie() {
        String choix;
        int choixChiffre = 0;
        boolean erreur = true;
        boolean tourDuJoueurA = true;
        boolean tourDuJoueurB = false;
        boolean dejaPousserArbreA = false;
        boolean dejaPousserArbreB = false;
        while (tourDuJoueurA || tourDuJoueurB) {
            if (tourDuJoueurA) {
                joueurCourant = this.joueur1;
            } else {
                joueurCourant = this.joueur2;
            }
            System.out.println("Voici la chlorophylle du joueur courant : " + joueurCourant.getNbrChlorophylle());
            System.out.println("1) Semer une graine (coût de 1Θ)\n"
                    + "2) Faire pousser un arbre (coût variant entre 1Θ et 3Θ)\n"
                    + "3) Couper un arbre mature (coût de 4Θ)\n"
                    + "4) Terminer son tour\n"
                    + "5) Quitter le jeu");
            choix = clavier.nextLine();
            while (erreur) {
                try {
                    choixChiffre = Integer.parseInt(choix);
                    if (choixChiffre > 0 && choixChiffre < 6) {
                        erreur = false;
                    } else {
                        System.out.println("Entrez un chiffre entre 1 et 5");
                        choix = clavier.nextLine();
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Entrez un chiffre SVP");
                    choix = clavier.nextLine();
                }
            }
            erreur = true;
            switch (choixChiffre) {
                case 1:
                    if (joueurCourant.getNbrChlorophylle() > 0) {
                        semerGraine();
                    } else {
                        System.out.println("Désolée vous ne détenez pas assez de chlorophylle");
                    }
                    break;
                case 2:
                    if (joueurCourant.getNbrChlorophylle() > 0) {
                        if (!dejaPousserArbreA && tourDuJoueurA) {
                            dejaPousserArbreA = pousserArbre();
                        } else if (!dejaPousserArbreB && tourDuJoueurB) {
                            dejaPousserArbreB = pousserArbre();
                        } else {
                            System.out.println("Vous avez déjà fait poussé un arbre durant ce tour");
                        }
                    } else {
                        System.out.println("Cette commande n'est pas possible puisque vous n'avez pas assez de cholorophylle");
                    }
                    break;
                case 3:
                    if (joueurCourant.getNbrChlorophylle() > 3) {
                        couperArbre();
                    } else {
                        System.out.println("Vous n'avez pas assez de chlorophylle");
                    }

                    break;
                case 4:
                    if (tourDuJoueurA) {
                        tourDuJoueurA = false;
                        tourDuJoueurB = true;
                        System.out.println("Le tour du joueur A est terminé");
                        this.joueur1 = joueurCourant;
                    } else {
                        tourDuJoueurB = false;
                        System.out.println("Le tour du joueur B est terminé");
                        this.joueur2 = joueurCourant;
                    }

                    break;
                case 5:
                    System.exit(0);
                    break;

            }
            affichageArboretum();
        }

    } // Affiche le menu et les options pour les deux joueurs

    private void semerGraine() {
        int voisinage = 2;
        boolean trouverArbreDuMemeType = false;
        int positionVerticale = positionVerticale();
        int positionHorizontale = positionHorizontale();
        try {
            if (arboretum[positionVerticale][positionHorizontale] == null) {
                for (int i = positionVerticale - voisinage; i <= positionVerticale + voisinage && !trouverArbreDuMemeType; i++) {
                    try {
                        if (arboretum[i][positionHorizontale].getJoueur().getLettre() == (joueurCourant.getLettre())
                                && (arboretum[i][positionHorizontale].getTaille() < 4)) {
                            trouverArbreDuMemeType = true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                    } catch (NullPointerException e) {
                    }
                }
                for (int j = positionHorizontale - voisinage; j <= positionHorizontale + voisinage && !trouverArbreDuMemeType; j++) {
                    try {
                        if (arboretum[positionVerticale][j].getJoueur().getLettre() == (joueurCourant.getLettre())
                                && (arboretum[positionVerticale][j].getTaille() < 4)) {
                            trouverArbreDuMemeType = true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                    } catch (NullPointerException e) {
                    }
                }
                if (trouverArbreDuMemeType) {
                    arboretum[positionVerticale][positionHorizontale] = new Arbre(0, joueurCourant);
                    joueurCourant.augmenterCholorophylle(-1);
                    System.out.println("Vous avez semé une graine !");
                } else {
                    System.out.println("Désolée il n'y a pas d'arbre assez proche");
                }
            } else {
                System.out.println("La case est déjà occupé veuillez réssayer ");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Vous allez devoir resaisir les positions");
        }
    } // Permet de semer une graine

    private boolean pousserArbre() {
        int positionHorizontale;
        int positionVerticale;
        boolean arbreAPousser = false;
        positionVerticale = positionVerticale();
        positionHorizontale = positionHorizontale();
        try {
            if (arboretum[positionVerticale][positionHorizontale].getJoueur().getLettre() == joueurCourant.getLettre()) {
                switch (arboretum[positionVerticale][positionHorizontale].getTaille()) {
                    case 0:
                        arboretum[positionVerticale][positionHorizontale].setTaille(1);
                        joueurCourant.augmenterCholorophylle(-1);
                        arbreAPousser = true;
                        break;
                    case 1:
                        if (arboretum[positionVerticale][positionHorizontale].getJoueur().getNbrChlorophylle() > 1) {
                            arboretum[positionVerticale][positionHorizontale].setTaille(2);

                            joueurCourant.augmenterCholorophylle(-2);
                            arbreAPousser = true;
                        } else {
                            System.out.println("Vous n'avez pas assez de chlorophylle");
                        }
                        break;

                    case 2:
                        if (arboretum[positionVerticale][positionHorizontale].getJoueur().getNbrChlorophylle() > 2) {
                            arboretum[positionVerticale][positionHorizontale].setTaille(3);
                            joueurCourant.augmenterCholorophylle(-3);
                            arbreAPousser = true;
                            break;
                        } else {
                            System.out.println("Vous n'avez pas assez de chlorophylle");
                        }
                        break;
                    case 3:
                        System.out.println("Désolée mais il est impossible d'augmenter un arbre de taille 3");
                        break;

                }
            } else {
                System.out.println("Ceci n'est pas votre arbre");
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Les positions entrées sont invalides");
        } catch (NullPointerException e) {
            System.out.println("Il n'y a pas d'abres à cette position");
        }
        return arbreAPousser;
    } // Permet de faire pousser un arbre

    private void couperArbre() {
        int positionVerticale = positionVerticale();
        int positionHorizontale = positionHorizontale();
        try {
            if ((arboretum[positionVerticale][positionHorizontale].getJoueur().getLettre() == joueurCourant.getLettre())
                    && (arboretum[positionVerticale][positionHorizontale].getTaille() == 3)) {
                if (positionVerticale == 0 || positionVerticale == 5) {
                    joueurCourant.augmenterPointDeVictoire(5);
                    joueurCourant.augmenterCholorophylle(-4);
                    arboretum[positionVerticale][positionHorizontale].setTaille(4);
                    System.out.println("Vous venez de gagner 5 points de victoire");
                } else if (positionHorizontale == 0 || positionVerticale == 4) {
                    joueurCourant.augmenterPointDeVictoire(5);
                    joueurCourant.augmenterCholorophylle(-4);
                    arboretum[positionVerticale][positionHorizontale].setTaille(4);
                    System.out.println("Vous venez de gagner 5 points de victoire");
                } else if (positionHorizontale > 0 && positionHorizontale < 5 && positionVerticale == 1 || positionVerticale == 4) {
                    joueurCourant.augmenterPointDeVictoire(7);
                    joueurCourant.augmenterCholorophylle(-4);
                    arboretum[positionVerticale][positionHorizontale].setTaille(4);
                    System.out.println("Vous venez de gagner 7 points de victoire");
                } else if (positionHorizontale > 1 && positionHorizontale < 4 && positionVerticale == 2) {
                    joueurCourant.augmenterPointDeVictoire(9);
                    joueurCourant.augmenterCholorophylle(-4);
                    arboretum[positionVerticale][positionHorizontale].setTaille(4);
                    System.out.println("Vous venez de gagner 9 points de victoire");
                } else {
                    joueurCourant.augmenterPointDeVictoire(7);
                    joueurCourant.augmenterCholorophylle(-4);
                    arboretum[positionVerticale][positionHorizontale].setTaille(4);
                    System.out.println("Vous venez de gagner 7 points de victoire");
                }

            } else {
                System.out.println("Il est impossible de couper cet arbre");
            }
        } catch (NullPointerException e) {
            System.out.println("Il n'y a aucun arbre a cette position");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Les positions sont invalides");
        }
    } // Permet de couper un arbre

    private void affichageArboretum() { // Ce qui permet de voir larboretum 
        if (soleil.getPosition() == Position.NORD) {
            System.out.println("                0");
            System.out.println("");
            System.out.println("");
        }
        System.out.println("      1   2   3   4   5   6");
        System.out.println("    --------------------------");
        for (int i = 0; i < arboretum.length; i++) {
            if (i == 2 && soleil.getPosition() == Position.OUEST) {
                System.out.print("0");

                System.out.print(" " + (i + 1) + "-" + "|");
            } else {
                System.out.print("  " + (i + 1) + "-" + "|");
            }
            for (int j = 0; j < arboretum[i].length; j++) {
                if (arboretum[i][j] == null) {
                    System.out.print("   " + "|"); // ICI ON MET L'ARBRE
                } else {
                    switch (arboretum[i][j].affichageArbre()) {
                        case "a":
                            System.out.print(arboretum[i][j].affichageArbre() + "  " + "|");
                            break;
                        case "A":
                            System.out.print(arboretum[i][j].affichageArbre() + "  " + "|");
                            break;
                        case "AA":
                            System.out.print(arboretum[i][j].affichageArbre() + " " + "|");
                            break;
                        case "AAA":
                            System.out.print(arboretum[i][j].affichageArbre() + "|");
                            break;
                        case "b":
                            System.out.print(arboretum[i][j].affichageArbre() + "  " + "|");
                            break;
                        case "B":
                            System.out.print(arboretum[i][j].affichageArbre() + "  " + "|");
                            break;
                        case "BB":
                            System.out.print(arboretum[i][j].affichageArbre() + " " + "|");
                            break;
                        case "BBB":
                            System.out.print(arboretum[i][j].affichageArbre() + "|");
                            break;
                        case "x":
                            System.out.print(arboretum[i][j].affichageArbre() + "  " + "|");
                            break;
                    }

                }
                if (j == 5 && i == 2 && soleil.getPosition() == Position.EST) {
                    System.out.print("         0");
                }

            }
            System.out.println("");
        }
        System.out.println("    --------------------------");
        if (soleil.getPosition() == Position.SUD) {
            System.out.println("");
            System.out.println("");
            System.out.println("                  0");
        }

    } // Permet d'afficher la plateforme de jeu ainsi que le Soleil

    private void ensoleillement() {
        int borneMaxParallele;
        boolean inverserIndice;
        int borneMaxPerpendiculaire;

        switch (soleil.getPosition()) {
            case NORD:
                borneMaxPerpendiculaire = arboretum.length;
                borneMaxParallele = arboretum[0].length;
                inverserIndice = false;
                verificationCholoro(POSITION_INITIALE_NORD, borneMaxPerpendiculaire, borneMaxParallele, VARIATION_NORD, inverserIndice);
                break;
            case SUD:
                inverserIndice = false;
                borneMaxPerpendiculaire = arboretum.length;
                borneMaxParallele = arboretum[0].length;
                verificationCholoro(POSITION_INITIALE_SUD, borneMaxPerpendiculaire, borneMaxParallele, VARIATION_SUD, inverserIndice);
                break;
            case OUEST:
                inverserIndice = true;
                borneMaxPerpendiculaire = arboretum[0].length;
                borneMaxParallele = arboretum.length;
                verificationCholoro(POSITION_INITIALE_OUEST, borneMaxPerpendiculaire, borneMaxParallele, VARIATION_OUEST, inverserIndice);
                break;
            case EST:
                inverserIndice = true;
                borneMaxPerpendiculaire = arboretum[0].length;
                borneMaxParallele = arboretum.length;
                verificationCholoro(POSITION_INITIALE_EST, borneMaxPerpendiculaire, borneMaxParallele, VARIATION_EST, inverserIndice);
                break;

        }
//Augmenter la chlorophylle du joueur de larbre en question
    } // Permet de recueillir la chlorophylle selon le soleil

    private void verificationCholoro(int positionInitiale, int borneMaxPerpendiculaire, int borneMaxParallele, int variation, boolean inverser) {
        Arbre arbre = new Arbre(0, null);
        int tailleMax = 0;
        for (int j = 0; j < borneMaxParallele; j++) {
            for (int i = positionInitiale; i < borneMaxPerpendiculaire && i > -1; i = i + variation) {

                try {
                    if (inverser) {
                        arbre = arboretum[j][i];
                    } else {
                        arbre = arboretum[i][j];
                    }
                    if (arbre.getTaille() > tailleMax) {

                        tailleMax = arbre.getTaille();
                        switch (arbre.affichageArbre()) {
                            case "A":
                                joueur1.augmenterCholorophylle(1);
                                break;
                            case "AA":
                                joueur1.augmenterCholorophylle(2);
                                break;
                            case "AAA":
                                joueur1.augmenterCholorophylle(3);
                                break;
                            case "B":
                                joueur2.augmenterCholorophylle(1);
                                break;
                            case "BB":
                                joueur2.augmenterCholorophylle(2);
                                break;
                            case "BBB":
                                joueur2.augmenterCholorophylle(3);
                                break;

                        }
                    }
                } catch (NullPointerException e) {

                }
            }
            tailleMax = 0;
        }

    } //Permet de parcourir le tableau peu importe la position du Soleil

    public void jouer() { // permet de commencer le jeu
        int nombreTours = 12;
        for (int i = 1; i <= nombreTours; i++) {
            // permet de faire les 12 tours
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            affichageArboretum();
            ensoleillement();
            cycleDeVie();
            soleil.tournerSoleil();
        }
        pointGagnantFin();
    }

    private int positionHorizontale() {
        String choix;
        int choixChiffre = 0;
        boolean erreur = true;
        System.out.println("Entrez la position horizontale voulue");
        choix = clavier.nextLine();
        while (erreur) {
            try {
                choixChiffre = Integer.parseInt(choix);
                erreur = false;
            } catch (NumberFormatException e) {
                System.out.println("Entrez un chiffre SVP");
                choix = clavier.nextLine();
            }
        }
        int positionHorizontale = choixChiffre - 1;
        return positionHorizontale;
    } // permet de demander la position Horizontale

    private int positionVerticale() {
        String choix;
        int choixChiffre = 0;
        boolean erreur = true;
        System.out.println("Indiquer la position  verticale voulue");
        choix = clavier.nextLine();
        while (erreur) {
            try {
                choixChiffre = Integer.parseInt(choix);
                erreur = false;
            } catch (NumberFormatException e) {
                System.out.println("Entrez un chiffre SVP");
                choix = clavier.nextLine();
            }
        }
        int positionVerticale = choixChiffre - 1;
        return positionVerticale;
    }// permet d'avoir la position verticale

    private void pointGagnantFin() {
        for (int i = 0; i < arboretum.length; i++) {
            for (int j = 0; j < arboretum[0].length; j++) {
                try {
                    if (arboretum[i][j].getTaille() > 0 && arboretum[i][j].getTaille() < 4) {
                        arboretum[i][j].getJoueur().augmenterPointDeVictoire(1);
                    }
                } catch (NullPointerException e) {

                }
            }
        }
        if (joueur1.getPointDeVictoire() > joueur2.getPointDeVictoire()) {
            System.out.println("Voici le gagnant joueur1 avec : " + joueur1.getPointDeVictoire() + "  points de vicotire");
        } else {
            System.out.println("Voici le gagnant joueur2 avec : " + joueur2.getPointDeVictoire() + "  points de vicotire");
        }
    } // permet de déterminer les points gagnants à la fin de la partie

    public boolean recommencerJeu() {
        String choix;
        boolean erreur = true;
        System.out.println("Voulez-vous recommencer le jeu?");
        System.out.println("1-Oui /n 2-Non");
        choix = clavier.nextLine();
        while (erreur) {
            try {
                Integer.parseInt(choix);
                if (Integer.parseInt(choix) < 3 && Integer.parseInt(choix) > 0) {
                    erreur = false;
                } else {
                    System.out.println("Vous devez entrer un chiffre entre 1 ou 2");
                    choix = clavier.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrez 1 ou 2 svp");
                choix = clavier.nextLine();
            }
        }
        switch (Integer.parseInt(choix)) {
            case 1:
                return true;

            default:
                return false;
        }
    } // permet de donner l'option de recommencer le jeu
}
