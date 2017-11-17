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
public class ArticleEnSolde extends Article {
    private double remise;
    
    public ArticleEnSolde() {
        
    }

    public ArticleEnSolde(double remise, String code, String designation, double prix, Categorie categorie) throws CategorieInvalideException {
        super(code, designation, prix, categorie);
        this.remise = remise;
    }

    @Override
    public double getPrix() {
        return super.getPrix() - ((super.getPrix() * this.remise) / 100);
    }
    
    
}
