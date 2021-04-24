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
public class AnketaOdgovori implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idAnkete;
    @Transient
    private Collection<PitanjeAnketa> pitanja;
    private String username;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OdgovorAnketa> odgovori;
    @Transient
    private String ime;
    @Transient
    private String prezime;
    @Transient
    @Temporal(TemporalType.DATE) 
    private Date datum;
    
    public AnketaOdgovori() {
    }
    
    public AnketaOdgovori(int idAnkete, Collection<PitanjeAnketa> pitanja) {
        this.idAnkete = idAnkete;
        this.pitanja=pitanja;
        odgovori=new ArrayList<>();
        for(int i = 0; i < pitanja.size(); i++){
            List<PitanjeAnketa> lista = (List<PitanjeAnketa>) pitanja;
            odgovori.add(new OdgovorAnketa(lista.get(i)));
        }
        postaviUsername();
    }  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnkete() {
        return idAnkete;
    }

    public void setIdAnkete(int idAnkete) {
        this.idAnkete = idAnkete;
    }

    public Collection<PitanjeAnketa> getPitanja() {
        return pitanja;
    }

    public void setPitanja(Collection<PitanjeAnketa> pitanja) {
        this.pitanja = pitanja;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OdgovorAnketa> getOdgovori() {
        return odgovori;
    }

    public void setOdgovori(List<OdgovorAnketa> odgovori) {
        this.odgovori = odgovori;
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
    
    public void zavrsi() throws IOException{
        for(int i = 0; i < odgovori.size(); i++){
            odgovori.get(i).sacuvajOdgovore();
        }
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        session.persist(this);
        
        session.getTransaction().commit();
        session.close();
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatAnketa.xhtml");
    }
    
    private void postaviUsername(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        setUsername(user.getUsername());
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
