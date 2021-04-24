/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Korisnik;
import entities.Zahtev;
import db.HibernateUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Luka
 */
@ManagedBean
@Named(value = "registracijacontroller")
@SessionScoped
public class RegistracijaController implements Serializable {
    private Zahtev zahtev = new Zahtev();
    private String poruka="";
    private List<Zahtev> zahtevi = new ArrayList<Zahtev>();
    
    public Zahtev getZahtev() {
        return zahtev;
    }
    
    public void setZahtev(Zahtev zahtev) {
        this.zahtev = zahtev;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public List<Zahtev> getZahtevi() {
        return zahtevi;
    }

    public void setZahtevi(List<Zahtev> zahtevi) {
        this.zahtevi = zahtevi;
    }
    
    public void registracija() throws IOException{
        
        if(!proveriUsername(zahtev.getUsername())){
            poruka="Username je zauzet!";
            return;
        }
        if(!proveriEmail(zahtev.getEmail())){
            poruka="Ovaj e-mail vec koriste 2 naloga";
            return;
        }
        if(!potvrdiLozinku()){
            poruka="Lozinke se ne poklapaju";
            return;
        }
        
        zahtev.setId(0);
        zahtev.setTip("ispitanik");
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        session.save(zahtev);
        
        session.getTransaction().commit();
        session.close();
        
        poruka="";
        zahtev=new Zahtev();
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/zahtevPoslat.xhtml");
    }
    
    public boolean proveriUsername(String user){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query1 = session.createCriteria(Zahtev.class);
        Zahtev z = (Zahtev) query1.add(Restrictions.eq("username", user)).uniqueResult();
        Criteria query2 = session.createCriteria(Korisnik.class);
        Korisnik k = (Korisnik) query2.add(Restrictions.eq("username", user)).uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        if(z!=null || k != null){
            return false;
        }
        else return true;
        
    }
    
    public boolean potvrdiLozinku(){
        if(zahtev.getPassword() == null ? zahtev.getPotvrdaLozinke() != null : !zahtev.getPassword().equals(zahtev.getPotvrdaLozinke()))return false;
        else return true;
    }
    
    public boolean proveriEmail(String email){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query1 = session.createCriteria(Zahtev.class);
        List<Zahtev> z = (List<Zahtev>) query1.add(Restrictions.eq("email", email)).list();
        Criteria query2 = session.createCriteria(Korisnik.class);
        List<Korisnik> k = (List<Korisnik>) query2.add(Restrictions.eq("email", email)).list();
        
        session.getTransaction().commit();
        session.close();
        
        if((z.size()+k.size())>=2)return false;
        else return true;
    }
    
    public void redirect_index() throws IOException{
        poruka="";
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }
    
}
