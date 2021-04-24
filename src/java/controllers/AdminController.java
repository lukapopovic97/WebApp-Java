/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Korisnik;
import entities.Zahtev;
import db.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Luka
 */
@ManagedBean
@Named(value = "admincontroller")
@SessionScoped
public class AdminController implements Serializable{
    private List<Korisnik> korisnici;
    private List<Zahtev> zahtevi;
    private Korisnik user;
    
    Random rand = new Random(System.currentTimeMillis());
    double random_number;

    private Korisnik korisnikIzmena;
    
    public AdminController() {
        random_number = rand.nextDouble();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        user = (Korisnik) hs.getAttribute("user");
        initKorisnici();
        initZahtevi();
    }

    public Korisnik getUser() {
        return user;
    }

    public void setUser(Korisnik user) {
        this.user = user;
    }

    public double getRandom_number() {
        return random_number;
    }

    public void setRandom_number(double random_number) {
        this.random_number = random_number;
    }

    private void initKorisnici(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Korisnik.class);
        korisnici = query.list();
        
        session.getTransaction().commit();
        session.close();
    }
    
    private void initZahtevi(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Zahtev.class);
        zahtevi = query.list();
        
        session.getTransaction().commit();
        session.close();
    }
    
    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public List<Zahtev> getZahtevi() {
        return zahtevi;
    }

    public void setZahtevi(List<Zahtev> zahtevi) {
        this.zahtevi = zahtevi;
    }

    public Korisnik getKorisnikIzmena() {
        return korisnikIzmena;
    }

    public void setKorisnikIzmena(Korisnik korisnikIzmena) {
        this.korisnikIzmena = korisnikIzmena;
    }
    
    public void prihvati(Zahtev zahtev){
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(zahtev.getIme());
        korisnik.setPrezime(zahtev.getPrezime());
        korisnik.setUsername(zahtev.getUsername());
        korisnik.setPassword(zahtev.getPassword());
        korisnik.setDatumRodjenja(zahtev.getDatumRodjenja());
        korisnik.setMestoRodjenja(zahtev.getMestoRodjenja());
        korisnik.setJMBG(zahtev.getJMBG());
        korisnik.setEmail(zahtev.getEmail());
        korisnik.setTelefon(zahtev.getTelefon());
        korisnik.setTip(zahtev.getTip());
        
        ubaciKorisnika(korisnik);
        izbaciZahtev(zahtev.getId());
        
        initKorisnici();
        initZahtevi();
    }
    
    public void odbij(Zahtev zahtev){
        izbaciZahtev(zahtev.getId());
        
        initKorisnici();
        initZahtevi();
    }
    
    private void ubaciKorisnika(Korisnik korisnik){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        session.save(korisnik);
        
        session.getTransaction().commit();
        session.close();
    }
    
    private void izbaciZahtev(int id){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query1 = session.createCriteria(Zahtev.class);
        Zahtev z = (Zahtev) query1.add(Restrictions.eq("id", id)).uniqueResult();
        session.delete(z);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void izmeni(Korisnik korisnik) throws IOException{
        korisnikIzmena = korisnik;
        FacesContext.getCurrentInstance().getExternalContext().redirect("izmena.xhtml");
    }
    
    public void sacuvaj() throws IOException{
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        session.update(korisnikIzmena);
        
        session.getTransaction().commit();
        session.close();
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
    }
    
    public void redirect_admin_pocetna() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
    }
}
