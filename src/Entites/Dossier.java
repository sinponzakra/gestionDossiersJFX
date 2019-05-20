/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
public class Dossier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Acquereur acquereur;
    @ManyToOne(fetch = FetchType.EAGER)
    private LeBien lebien;
    @ManyToOne(fetch = FetchType.EAGER)
    private Vendeur vendeur; 
    @Temporal(TemporalType.DATE)
    private Date date;
    private String etat;

    public Dossier() {
    }

    public Dossier(int id, Acquereur acquereur, LeBien lebien, Vendeur vendeur, Date date, String etat) {
        this.id = id;
        this.acquereur = acquereur;
        this.lebien = lebien;
        this.vendeur = vendeur;
        this.date = date;
        this.etat = etat;
    }
    
    public Dossier(Acquereur acquereur, LeBien lebien, Vendeur vendeur, Date date, String etat) {
        this.acquereur = acquereur;
        this.lebien = lebien;
        this.vendeur = vendeur;
        this.date = date;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Acquereur getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(Acquereur acquereur) {
        this.acquereur = acquereur;
    }

    public LeBien getLebien() {
        return lebien;
    }

    public void setLebien(LeBien lebien) {
        this.lebien = lebien;
    }

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

   
    
   
}
