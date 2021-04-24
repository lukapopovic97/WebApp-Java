/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Korisnik;
import db.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
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
@Named(value = "lozinkacontroller")
@SessionScoped
public class LozinkaController implements Serializable{
    private String username;
    private String staraLozinka;
    private String novaLozinka;
    private String potvrdaLozinke;
    private String poruka="";
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStaraLozinka() {
        return staraLozinka;
    }

    public void setStaraLozinka(String staraLozinka) {
        this.staraLozinka = staraLozinka;
    }

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getPotvrdaLozinke() {
        return potvrdaLozinke;
    }

    public void setPotvrdaLozinke(String potvrdaLozinke) {
        this.potvrdaLozinke = potvrdaLozinke;
    }
    
    public void promeniLozinku() throws IOException{
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Korisnik.class);
        Korisnik kor = (Korisnik) query.add(Restrictions.eq("username", user.getUsername())).uniqueResult();
        if(user!=null){
            if(!staraLozinka.equals(kor.getPassword())){
                poruka="Pogresno ste uneli staru lozinku!";
                return;
            }
            else{
                if(!novaLozinka.equals(potvrdaLozinke)){
                    poruka="Lozinke se ne poklapaju";
                    return;
                }
                kor.setPassword(novaLozinka);
                session.update(kor);
                FacesContext.getCurrentInstance().getExternalContext().redirect("lozinkaPromenjena.xhtml");
            }
        }
        else{
            poruka="Nepoznata greska";
        }
        
        session.getTransaction().commit();
        session.close();
    }

    
    public void redirect() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
    }
    
    public void redirect_promenalozinke() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("promenaLozinke.xhtml");
    }
}
