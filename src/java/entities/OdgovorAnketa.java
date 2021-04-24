/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import db.HibernateUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Luka
 */
@Entity
public class OdgovorAnketa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idPitanja;
    @Transient
    private String[] nizOdgovora;
    private String odgovori;
    @Transient
    private PitanjeAnketa pitanje;
    
    public OdgovorAnketa(){
        
    }
    
    public OdgovorAnketa(PitanjeAnketa pitanje){
        this.idPitanja = pitanje.getId();
        this.pitanje = pitanje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPitanja() {
        return idPitanja;
    }

    public void setIdPitanja(int idPitanja) {
        this.idPitanja = idPitanja;
    }

    public String[] getNizOdgovora() {
        return nizOdgovora;
    }

    public void setNizOdgovora(String[] nizOdgovora) {
        this.nizOdgovora = nizOdgovora;
    }

    public String getOdgovori() {
        return odgovori;
    }

    public void setOdgovori(String odgovori) {
        this.odgovori = odgovori;
    }

    public PitanjeAnketa getPitanje() {
        return pitanje;
    }

    public void setPitanje(PitanjeAnketa pitanje) {
        this.pitanje = pitanje;
    }
    
    public void sacuvajOdgovore(){
        if(nizOdgovora != null){
            odgovori="";
            for(int i = 0; i < nizOdgovora.length; i++){
                odgovori+=nizOdgovora[i]+"#";
            }
        }
    }
    
    public List<String> listaOdgovora(){
        if(odgovori==null)return null;
        List<String> ret = Arrays.asList(odgovori.split("#"));
        return ret;
    }
    
    public void postaviPitanje(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(PitanjeAnketa.class).add(Restrictions.eq("id", idPitanja));
        pitanje = (PitanjeAnketa) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
    }
}
