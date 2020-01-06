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
public class Joueur { // Ce qui représente les deux joueurs

    private int nombreChlorophylle;
    private char lettreJoueur;
    private int pointDeVictoire=0;

    public Joueur(int nbrChlorophylle, char lettre) { // constructeur
        this.lettreJoueur = lettre;
        this.nombreChlorophylle = nbrChlorophylle;
    } 

    public char getLettre() { // retourne le joueur a ou b
        return lettreJoueur;
    }

    public void augmenterCholorophylle(int nbrAugmenter) { // Permet d'augmenter la chlorophylle
        this.nombreChlorophylle = this.nombreChlorophylle + nbrAugmenter;
    }

    public int getNbrChlorophylle() { // permet d'avoir le nombre de chlorophylle 
        return this.nombreChlorophylle;
    }

    public void augmenterPointDeVictoire(int nbrAugmenter) { // permet d'augmente rle nombre de point de victoire
        this.pointDeVictoire = this.pointDeVictoire + nbrAugmenter;
    }
    public int getPointDeVictoire(){ // permet d'obtenir les points de victoire
        return this.pointDeVictoire;
    }

}
