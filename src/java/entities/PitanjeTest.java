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
public class PitanjeTest implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @ManyToMany(mappedBy = "pitanja", fetch = FetchType.EAGER)
    private Collection<Test> testovi;
    
    public PitanjeTest(){
        testovi=new ArrayList<>();
        poeni = new Double(3.00);
    }
    
    @Column(name = "tip")
    private int tip; //1 - numericka vrednost sa 1. poljem, 2 - tekst sa jednim poljem, 3 - textarea, 4 - radio/select, 5 - checkbox m / n
    
    @Column(name = "tekst_pitanja")
    private String tekst_pitanja;
    
    @Column(name = "netacni_odgovori")
    private String netacni_odgovori;
    
    @Column(name = "tacni_odgovori")
    private String tacni_odgovori;
    
    @Column(name = "poeni")
    private Double poeni;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTacni_odgovori() {
        return tacni_odgovori;
    }

    public void setTacni_odgovori(String tacni_odgovori) {
        this.tacni_odgovori = tacni_odgovori;
    }

    public String getNetacni_odgovori() {
        return netacni_odgovori;
    }

    public void setNetacni_odgovori(String netacni_odgovori) {
        this.netacni_odgovori = netacni_odgovori;
    }

    public Double getPoeni() {
        return poeni;
    }

    public void setPoeni(Double poeni) {
        this.poeni = poeni;
    }

    public Collection<Test> getTestovi() {
        return testovi;
    }

    public void setTestovi(Collection<Test> testovi) {
        this.testovi = testovi;
    }
    
    public List<String> listaTacnihOdgovora(){
        List<String> ret = Arrays.asList(tacni_odgovori.split("#"));
        return ret;
    }
    
    public List<String> listaNetacnihOdgovora(){
        if(netacni_odgovori==null) return null;
        List<String> ret = Arrays.asList(netacni_odgovori.split("#"));
        return ret;
    }
}
