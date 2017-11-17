/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimagasin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    
    public ArrayList<Achat> getAchats(int numFacture) {
        ArrayList<Achat> arrayAchats = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM achat WHERE num_facture = ?";
            PreparedStatement stmt = this.connection.prepareStatement(req);
            stmt.setInt(1, numFacture);
            
            ResultSet result = stmt.executeQuery();
            
            while(result.next()) {
                int num = result.getInt("num");
                int quantite = result.getInt("quantite");
                String codeArticle = result.getString("code_article");
                
                for(Article article : new Article().chargerArticles()) {
                    if(article.getCode().equals(codeArticle)) {
                        Achat achat = new Achat(num, article, quantite);
                        arrayAchats.add(achat);
                        break;
                    }
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return arrayAchats;
    }
}
