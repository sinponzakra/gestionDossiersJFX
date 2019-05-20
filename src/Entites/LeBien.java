/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author pc
 */
@Entity
public class LeBien {
    @EmbeddedId
    private LeBienPK id;
    private String tf;
    private String ri;
    private String rc;
    private String adresse;
    private String superficie;
    private String consistance;
    private String charge;
    private String situationLocative;
    private Double prixCession;
    private String situationSyndic;
    private String chargesEtTaxes;
    private Double avance;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date delaiDuCompromisDeVente;
    @JoinColumn(name = "vendeurId" , referencedColumnName = "id" , insertable = false, updatable = false)
    @ManyToOne
    private Vendeur vendeur;
    @JoinColumn(name = "acquereurId", referencedColumnName = "id" , insertable = false, updatable = false)
    @ManyToOne
    private Acquereur acquereur;

    public LeBien() {
    }

    public LeBien(LeBienPK id, String tf, String ri, String rc, String adresse, String superficie, String consistance, String charge, String situationLocative, Double prixCession, String situationSyndic, String chargesEtTaxes, Double avance, Date delaiDuCompromisDeVente, Vendeur vendeur, Acquereur acquereur) {
        this.id = id;
        this.tf = tf;
        this.ri = ri;
        this.rc = rc;
        this.adresse = adresse;
        this.superficie = superficie;
        this.consistance = consistance;
        this.charge = charge;
        this.situationLocative = situationLocative;
        this.prixCession = prixCession;
        this.situationSyndic = situationSyndic;
        this.chargesEtTaxes = chargesEtTaxes;
        this.avance = avance;
        this.delaiDuCompromisDeVente = delaiDuCompromisDeVente;
        this.vendeur = vendeur;
        this.acquereur = acquereur;
    }

    public LeBienPK getId() {
        return id;
    }

    public void setId(LeBienPK id) {
        this.id = id;
    }

    public String getTf() {
        return tf;
    }

    public void setTf(String tf) {
        this.tf = tf;
    }

    public String getRi() {
        return ri;
    }

    public void setRi(String ri) {
        this.ri = ri;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getConsistance() {
        return consistance;
    }

    public void setConsistance(String consistance) {
        this.consistance = consistance;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getSituationLocative() {
        return situationLocative;
    }

    public void setSituationLocative(String situationLocative) {
        this.situationLocative = situationLocative;
    }

    public Double getPrixCession() {
        return prixCession;
    }

    public void setPrixCession(Double prixCession) {
        this.prixCession = prixCession;
    }

    public String getSituationSyndic() {
        return situationSyndic;
    }

    public void setSituationSyndic(String situationSyndic) {
        this.situationSyndic = situationSyndic;
    }

    public String getChargesEtTaxes() {
        return chargesEtTaxes;
    }

    public void setChargesEtTaxes(String chargesEtTaxes) {
        this.chargesEtTaxes = chargesEtTaxes;
    }

    public Double getAvance() {
        return avance;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }

    public Date getDelaiDuCompromisDeVente() {
        return delaiDuCompromisDeVente;
    }

    public void setDelaiDuCompromisDeVente(Date delaiDuCompromisDeVente) {
        this.delaiDuCompromisDeVente = delaiDuCompromisDeVente;
    }

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    public Acquereur getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(Acquereur acquereur) {
        this.acquereur = acquereur;
    }       

    @Override
    public String toString() {
        return this.adresse;
    }
    
    
}
