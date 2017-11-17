/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimagasin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Deoel
 */
public class Achat implements Serializable{
    private int numeroAchat;
    private Article articleAchete;
    private int quantite;
    
    private Connection connection;
    
    public Achat() {
        this.connection = ConnectionDB.getConnection();
    }

    public Achat(int numeroAchat, Article articleAchete, int quantite) {
        this.numeroAchat = numeroAchat;
        this.articleAchete = articleAchete;
        this.quantite = quantite;
        this.connection = ConnectionDB.getConnection();
    }
    
    public double getMontantAchat() {
        return this.articleAchete.getPrix() * quantite;
    }

    @Override
    public String toString() {
        return "Numéro Achat : " + this.numeroAchat + "\n" + this.articleAchete.toString() + "\n" + "Quantité : " + this.quantite;
    }

    public int getNumeroAchat() {
        return numeroAchat;
    }

    public void setNumeroAchat(int numeroAchat) {
        this.numeroAchat = numeroAchat;
    }

    public Article getArticleAchete() {
        return articleAchete;
    }

    public void setArticleAchete(Article articleAchete) {
        this.articleAchete = articleAchete;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public int enregistrer(int numFacture) {
        int result = 0;
        try {
            String req = "INSERT INTO achat(quantite, code_article, num_facture) VALUES(?, ?, ?)";
            PreparedStatement prepareStmt = this.connection.prepareStatement(req);
            
            prepareStmt.setInt(1, this.quantite);
            prepareStmt.setString(2, this.articleAchete.getCode());
            prepareStmt.setLong(3, numFacture);
            
            result = prepareStmt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
}
