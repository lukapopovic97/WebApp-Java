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
public class OdgovorTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idPitanja;
    @Transient
    private String[] nizOdgovora;
    private String odgovori;
    private double poeni;
    @Transient
    private PitanjeTest pitanje;
    private double maxPoeni;

    public OdgovorTest() {
    }
    
    
    public OdgovorTest(PitanjeTest p){
        idPitanja = p.getId();
        maxPoeni = p.getPoeni();
        pitanje=p;
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

    public double getPoeni() {
        return poeni;
    }

    public void setPoeni(double poeni) {
        this.poeni = poeni;
    }

    public PitanjeTest getPitanje() {
        return pitanje;
    }

    public void setPitanje(PitanjeTest pitanje) {
        this.pitanje = pitanje;
    }

    public double getMaxPoeni() {
        return maxPoeni;
    }

    public void setMaxPoeni(double maxPoeni) {
        this.maxPoeni = maxPoeni;
    }
    
    public void sacuvajOdgovore(){
        izracunajPoene();
        if(nizOdgovora != null){
            odgovori="";
            for(int i = 0; i < nizOdgovora.length; i++){
                odgovori+=nizOdgovora[i]+"#";
            }
        }
    }
    
    public void izracunajPoene(){
        int tip = pitanje.getTip();
        switch(tip){
            case 1:
            case 2: 
            case 4:
                String tac = pitanje.listaTacnihOdgovora().get(0);
                if(odgovori != null && odgovori.equals(tac)){
                    poeni = pitanje.getPoeni();
                }
                else poeni = 0;
                break;
            case 5:
                if(nizOdgovora == null){
                    poeni=0;
                }
                else {
                    List<String> tacnilist = pitanje.listaTacnihOdgovora();
                    int num = 0;
                    for(int i=0; i < nizOdgovora.length; i++){
                        if(tacnilist.contains(nizOdgovora[i])) num++;
                    }
                    poeni = (num - (nizOdgovora.length - num)) * maxPoeni / tacnilist.size();
                    if(poeni < 0) poeni = 0;
                }
        }
    }
    
    public List<String> listaOdgovora(){
        if(odgovori==null) return null;
        List<String> ret = Arrays.asList(odgovori.split("#"));
        return ret;
    }
    
    public String poeniString(){
        return poeni+"/"+maxPoeni;
    }
    
    public void postaviPitanje(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(PitanjeTest.class).add(Restrictions.eq("id", idPitanja));
        pitanje = (PitanjeTest) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
    }
}
