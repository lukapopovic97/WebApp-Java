/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Luka
 */
@Entity
public class PitanjeAnketa implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToMany(mappedBy = "pitanja", fetch = FetchType.EAGER)
    private Collection<Anketa> ankete;
    
    private int tip;
    private String tekst_pitanja;
    private String ponudjeni_odgovori;
    private boolean obavezno;

    public PitanjeAnketa() {
        ankete=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Anketa> getAnkete() {
        return ankete;
    }

    public void setAnkete(Collection<Anketa> ankete) {
        this.ankete = ankete;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getTekst_pitanja() {
        return tekst_pitanja;
    }

    public void setTekst_pitanja(String tekst_pitanja) {
        this.tekst_pitanja = tekst_pitanja;
    }

    public String getPonudjeni_odgovori() {
        return ponudjeni_odgovori;
    }

    public void setPonudjeni_odgovori(String ponudjeni_odgovori) {
        this.ponudjeni_odgovori = ponudjeni_odgovori;
    }

    public boolean isObavezno() {
        return obavezno;
    }

    public void setObavezno(boolean obavezno) {
        this.obavezno = obavezno;
    }
    
    public List<String> listaPonudjenihOdgovora(){
        if(ponudjeni_odgovori==null)return null;
        List<String> ret = Arrays.asList(ponudjeni_odgovori.split("#"));
        return ret;
    }
}
