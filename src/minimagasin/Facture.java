/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimagasin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Deoel
 */
public class Facture implements Serializable {
    private int num;
    private Date date;
    private ArrayList<Achat> collectionAchat;
    private Connection connection;
    
    public Facture() {
        this.date = Date.valueOf(LocalDate.now());
        this.collectionAchat = new ArrayList<>();
        this.connection = ConnectionDB.getConnection();
    }

    public Facture(int num, Date date, ArrayList<Achat> collectionAchat) {
        this.num = num;
        this.date = date;
        this.collectionAchat = collectionAchat;
        this.connection = ConnectionDB.getConnection();
    }

    public Date getDate() {
        return date;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<Achat> getCollectionAchat() {
        return collectionAchat;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public boolean ajouter(Achat achat) {
        boolean find = false;
        for(Achat a : this.collectionAchat) {
            if(a.getArticleAchete().equals(achat.getArticleAchete())) {
                find = true;
                break;
            }
        }
        
        if(!find) {
            this.collectionAchat.add(achat);
            return true;
        } else {
            System.out.println("Ce produit est déjà dans la collection d'achat!");
            return false;
        }
    }
    
    public double getMontantFacture() {
       double prixTotal = 0;
       for(Achat achat : this.collectionAchat) {
           prixTotal += achat.getMontantAchat();
       }
       
       return prixTotal;
    }
    
    public void enregistrerAchats(String fileName) {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(fileName))));
            oos.writeObject(this);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String result = "Num : " + this.num + "\nDate : " + this.date.toString() + "\n";
        
        for(Achat a : this.collectionAchat) {
            result += a.toString();
        }
                
        return result;
    }
    
    public int enregistrer() {
        int result = 0;
        try {
            String req = "INSERT INTO facture(num, date_facture) VALUES(?, ?)";
            PreparedStatement prepareStmt = this.connection.prepareStatement(req);
            
            prepareStmt.setInt(1, this.num);
            prepareStmt.setDate(2, this.date);
            
            result = prepareStmt.executeUpdate();
            
            for(Achat achat : this.collectionAchat) {
                achat.enregistrer(this.num);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
}
