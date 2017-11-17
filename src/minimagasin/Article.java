/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimagasin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Deoel
 */


public class Article implements Serializable {
    protected String code;
    protected String designation;
    protected double prix;
    protected Categorie categorie;
    
    protected Connection connection;
    
    public Article() {
        this.connection = ConnectionDB.getConnection();
    }

    public Article(String code, String designation, double prix, Categorie categorie) throws CategorieInvalideException {
        
        if (categorie == Categorie.Bureautique || categorie == Categorie.Informatique) {
            this.code = code;
            this.designation = designation;
            this.prix = prix;
            this.categorie = categorie;
            
            this.connection = ConnectionDB.getConnection();
        } else {
            throw new CategorieInvalideException();
        }        
    }

    public String getCode() {
        return code;
    }

    public String getDesignation() {
        return designation;
    }
    
    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    @Override
    public String toString() {
        return "Code : " + this.code + "\nDesignation : " + this.designation + "\nPrix : " + this.prix + "\n" + this.categorie;
    }

    public boolean equals(Article art) {
        return (this.code.equals(art.code) && 
                this.designation.equals(art.designation) && 
                this.prix == art.prix &&
                this.categorie == art.categorie);
    }

    public int enregistrer() {
        int result = 0;
        try {
            String req = "INSERT INTO article(code, designation, prix, categorie) VALUES(?, ?, ?, ?)";
            PreparedStatement prepareStmt = this.connection.prepareStatement(req);
            
            prepareStmt.setString(1, this.code);
            prepareStmt.setString(2, this.designation);
            prepareStmt.setDouble(3, this.getPrix());
            prepareStmt.setString(4, this.categorie.name());
            
            result = prepareStmt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    public ArrayList<Article> chargerArticles() {
        ArrayList<Article> arrayArticles = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM article";
            Statement stmt = this.connection.createStatement();
            
            ResultSet result = stmt.executeQuery(req);
            
            while(result.next()) {
                String code = result.getString("code");
                String designation = result.getString("designation");
                double prix = result.getDouble("prix");
                Categorie categorie = Categorie.valueOf(result.getString("categorie"));
                
                Article article = new Article(code, designation, prix, categorie);
                arrayArticles.add(article);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (CategorieInvalideException ex) {
            ex.printStackTrace();
        }
        
        return arrayArticles;
    }
}
