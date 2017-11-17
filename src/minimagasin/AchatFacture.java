/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimagasin;

/**
 *
 * @author Deoel
 */
public class AchatFacture extends Achat {
    private long numFacture;

    public AchatFacture(int numeroAchat, Article articleAchete, int quantite, long numFacture) {
        super(numeroAchat, articleAchete, quantite);
        this.numFacture = numFacture;
    }
    
}
