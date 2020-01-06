/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog2;

/**
 *
 * @author Marc-Antoine Abou Jaoude
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean continuer = true;
        while (continuer) {
            Arboretum arboretum = new Arboretum();
            arboretum.jouer();
            continuer = arboretum.recommencerJeu();
        }
    }

}
