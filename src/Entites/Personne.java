/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PERSONNE_TYPE")
public abstract class Personne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected int id;
    protected String nom;
    protected String prenom;
    protected String ben;
    @Temporal(TemporalType.DATE)
    protected Date dateNaissance;
    protected String cin;
    protected String telephone;
    protected String fonction;
    protected String adresse;
    protected String situationFamiliale;
    protected String regimeMariage;
    protected String lieuMariage;
    protected String associe;
    protected String adresseCourriel;
    protected String email;
    protected String typePersonne;

    public Personne() {
    }

    public Personne(int id, String nom, String prenom, String ben, Date dateNaissance, String cin, String telephone, String fonction, String adresse, String situationFamiliale, String regimeMariage, String lieuMariage, String associe, String adresseCourriel, String email, String typePersonne) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ben = ben;
        this.dateNaissance = dateNaissance;
        this.cin = cin;
        this.telephone = telephone;
        this.fonction = fonction;
        this.adresse = adresse;
        this.situationFamiliale = situationFamiliale;
        this.regimeMariage = regimeMariage;
        this.lieuMariage = lieuMariage;
        this.associe = associe;
        this.adresseCourriel = adresseCourriel;
        this.email = email;
        this.typePersonne = typePersonne;
    }

    public Personne(String nom, String prenom, String ben, Date dateNaissance, String cin, String telephone, String fonction, String adresse, String situationFamiliale, String regimeMariage, String lieuMariage, String associe, String adresseCourriel, String email, String typePersonne) {
        this.nom = nom;
        this.prenom = prenom;
        this.ben = ben;
        this.dateNaissance = dateNaissance;
        this.cin = cin;
        this.telephone = telephone;
        this.fonction = fonction;
        this.adresse = adresse;
        this.situationFamiliale = situationFamiliale;
        this.regimeMariage = regimeMariage;
        this.lieuMariage = lieuMariage;
        this.associe = associe;
        this.adresseCourriel = adresseCourriel;
        this.email = email;
        this.typePersonne = typePersonne;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getBen() {
        return ben;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getCin() {
        return cin;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getFonction() {
        return fonction;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getSituationFamiliale() {
        return situationFamiliale;
    }

    public String getRegimeMariage() {
        return regimeMariage;
    }

    public String getLieuMariage() {
        return lieuMariage;
    }

    public String getAssocie() {
        return associe;
    }

    public String getAdresseCourriel() {
        return adresseCourriel;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setBen(String ben) {
        this.ben = ben;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setSituationFamiliale(String situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public void setRegimeMariage(String regimeMariage) {
        this.regimeMariage = regimeMariage;
    }

    public void setLieuMariage(String lieuMariage) {
        this.lieuMariage = lieuMariage;
    }

    public void setAssocie(String associe) {
        this.associe = associe;
    }

    public void setAdresseCourriel(String adresseCourriel) {
        this.adresseCourriel = adresseCourriel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }
    
    
    
    
}
