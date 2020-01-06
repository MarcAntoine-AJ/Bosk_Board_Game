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
public class Soleil { // représente le soleil dans le jeu

    public enum Position {
        NORD,
        SUD,
        OUEST,
        EST
    };
    private Position position;

    public Soleil() { // Constructeur du Soleil qui initialise a Nord
        this.position = position.NORD;
    }

    public void tournerSoleil() { // Sert a faire tourner le Soleil
        switch (position) {
            case NORD:
                this.position = position.EST;
                break;
            case EST:
                this.position = position.SUD;
                break;
            case SUD:
                this.position = position.OUEST;
                break;
            case OUEST:
                this.position = position.NORD;
                break;
        }

    }

    public Position getPosition() { // permet de savoir ou se trouve le soleil
        return this.position;
    }
}
