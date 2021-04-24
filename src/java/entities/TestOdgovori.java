/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import db.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Luka
 */
@Entity
public class TestOdgovori implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idTesta;
    @Transient
    private Collection<PitanjeTest> pitanja;        
    private String username;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OdgovorTest> odgovori;
    private double poeni;
    private double maxPoeni;
    @Transient
    private int sekunde;
    @Transient
    private String ime;
    @Transient
    private String prezime;
    @Transient
    @Temporal(TemporalType.DATE) 
    private Date datum;
    @Transient
    private String poruka;
    
    public TestOdgovori(){
        
    }
    
    public TestOdgovori(int idTesta, Collection<PitanjeTest> pitanja, int trajanje) {
        sekunde = trajanje * 60;
        this.idTesta = idTesta;
        this.pitanja=pitanja;
        poeni=0;
        maxPoeni=0;
        odgovori=new ArrayList<>();
        for(int i = 0; i < pitanja.size(); i++){
            List<PitanjeTest> lista = (List<PitanjeTest>) pitanja;
            odgovori.add(new OdgovorTest(lista.get(i)));
        }
        postaviUsername();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTesta() {
        return idTesta;
    }

    public void setIdTesta(int idTesta) {
        this.idTesta = idTesta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public List<OdgovorTest> getOdgovori() {
        return odgovori;
    }

    public void setOdgovori(List<OdgovorTest> odgovori) {
        this.odgovori = odgovori;
    }

    public Collection<PitanjeTest> getPitanja() {
        return pitanja;
    }

    public void setPitanja(Collection<PitanjeTest> pitanja) {
        this.pitanja = pitanja;
    }

    public double getPoeni() {
        return poeni;
    }

    public double getMaxPoeni() {
        return maxPoeni;
    }

    public void setMaxPoeni(double maxPoeni) {
        this.maxPoeni = maxPoeni;
    }

    public void setPoeni(double poeni) {
        this.poeni = poeni;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
    
    private void postaviUsername(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        setUsername(user.getUsername());
    }

    public int getSekunde() {
        return sekunde;
    }

    public void setSekunde(int sekunde) {
        this.sekunde = sekunde;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    public void zavrsi() throws IOException{
        for(int i = 0; i < odgovori.size(); i++){
            odgovori.get(i).sacuvajOdgovore();
            poeni+=odgovori.get(i).getPoeni();
            maxPoeni+=odgovori.get(i).getMaxPoeni();
        }
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        session.persist(this);
        
        session.getTransaction().commit();
        session.close();
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatTest.xhtml");
    }
    
    public void decr() throws IOException{
        sekunde--;
        if(sekunde==5)poruka="Test se uskoro zavrsava!";
        if(sekunde <= 0)zavrsi();
    }
    
    public String poeniString(){
        return poeni+"/"+maxPoeni;
    }
    
    public void postaviLicnePodatke(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Korisnik.class).add(Restrictions.eq("username", username));
        Korisnik user = (Korisnik) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        ime=user.getIme();
        prezime=user.getPrezime();
        datum=user.getDatumRodjenja();
    }
    
    public String popunjeno(){
        int p=0;
        for(int i=0;i<odgovori.size();i++){
            if(odgovori.get(i).getPitanje().getTip()==5){
                if(odgovori.get(i).getNizOdgovora().length!=0)p++;
            }
            else{
                if((odgovori.get(i).getOdgovori()!=null) && (!"".equals(odgovori.get(i).getOdgovori()))) p++;
            }
        }
        return "Popunjeno je "+p+"/"+odgovori.size()+" pitanja";
    }
}
