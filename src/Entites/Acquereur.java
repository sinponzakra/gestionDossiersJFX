/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author pc
 */
@Entity
public class Acquereur extends Personne implements Serializable{

    @OneToMany(mappedBy = "acquereur")
    private List<LeBien> leBiens;

    public Acquereur() {
    }

    public Acquereur(int id, String nom, String prenom, String ben, Date dateNaissance, String cin, String telephone, String fonction, String adresse, String situationFamiliale, String regimeMariage, String lieuMariage, String associe, String adresseCourriel, String email, String typePersonne) {
        super(id, nom, prenom, ben, dateNaissance, cin, telephone, fonction, adresse, situationFamiliale, regimeMariage, lieuMariage, associe, adresseCourriel, email, typePersonne);
    }

    public Acquereur(String nom, String prenom, String ben, Date dateNaissance, String cin, String telephone, String fonction, String adresse, String situationFamiliale, String regimeMariage, String lieuMariage, String associe, String adresseCourriel, String email, String typePersonne) {
        super(nom, prenom, ben, dateNaissance, cin, telephone, fonction, adresse, situationFamiliale, regimeMariage, lieuMariage, associe, adresseCourriel, email, typePersonne);
    }

    public List<LeBien> getLeBiens() {
        return leBiens;
    }

    public void setLeBiens(List<LeBien> leBiens) {
        this.leBiens = leBiens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getBen() {
        return ben;
    }

    public void setBen(String ben) {
        this.ben = ben;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(String situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public String getRegimeMariage() {
        return regimeMariage;
    }

    public void setRegimeMariage(String regimeMariage) {
        this.regimeMariage = regimeMariage;
    }

    public String getLieuMariage() {
        return lieuMariage;
    }

    public void setLieuMariage(String lieuMariage) {
        this.lieuMariage = lieuMariage;
    }
    
    public String getAssocie() {
        return associe;
    }

    public void setAssocie(String associe) {
        this.associe = associe;
    }

    public String getAdresseCourriel() {
        return adresseCourriel;
    }

    public void setAdresseCourriel(String adresseCourriel) {
        this.adresseCourriel = adresseCourriel;
    }

    public String getEmail() {
        return email;
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

    @Override
    public String toString() {
        return this.getPrenom()+ " " +this.getNom();
    }

}
