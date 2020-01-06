/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog2;

/**
 *
 * @author 1742177
 */
public class Arbre { // représente les arbres dans le jeu

    private int taille;
    private Joueur arbreDuJoueur;

    public Arbre(int taille, Joueur joueur) { // Constructeur de l'arbre
        this.arbreDuJoueur = joueur;
        this.taille = taille;
    }

    public String affichageArbre() { // retourne ce qui sera affiché selon la taille et le joueur
        char lettre;
        lettre = this.arbreDuJoueur.getLettre();
        switch (taille) {
            case 0:
                if (lettre == 'a') {
                    return "a";
                } else {
                    return "b";
                }
            case 1:
                if (lettre == 'a') {
                    return "A";
                } else {
                    return "B";
                }
            case 2:
                if (lettre == 'a') {
                    return "AA";
                } else {
                    return "BB";
                }
            case 3:
                if (lettre == 'a') {
                    return "AAA";
                } else {
                    return "BBB";
                }
            case 4: // arbre coupé
                return "x";
            default:
                return null;
        }
    }

    public int getTaille() { // Permet d'obtenir la taille 
        return taille;
    }

    public Joueur getJoueur() { // Permet de retourner le joueur de l'arbre en question
        return this.arbreDuJoueur;
    }

    public void setTaille(int taille) { // permet de donner une nouvelle taille à l'arbre
        this.taille = taille;
    }

}
