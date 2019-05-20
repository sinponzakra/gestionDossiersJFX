/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

/**
 *
 * @author pc
 */
@Embeddable
public class LeBienPK implements Serializable{
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateContrat;
    private int vendeurId;
    private int acquereurId;

    public LeBienPK() {
    }

    public LeBienPK(Date dateContrat, int vendeurId, int AcquereurId) {
        this.dateContrat = dateContrat;
        this.vendeurId = vendeurId;
        this.acquereurId = AcquereurId;
    }

    public Date getDateContrat() {
        return dateContrat;
    }

    public void setDateContrat(Date dateContrat) {
        this.dateContrat = dateContrat;
    }

    public int getVendeurId() {
        return vendeurId;
    }

    public void setVendeurId(int vendeurId) {
        this.vendeurId = vendeurId;
    }

    public int getAcquereurId() {
        return acquereurId;
    }

    public void setAcquereurId(int AcquereurId) {
        this.acquereurId = AcquereurId;
    }

    @Override
    public String toString() {
        return this.dateContrat.toString();
    }
    
    
    
}
