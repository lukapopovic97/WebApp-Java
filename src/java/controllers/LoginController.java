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
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "logincontroller")
@SessionScoped

public class LoginController implements Serializable{
    private String username;
    private String password;
    private String poruka="";
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    public void login() throws IOException{
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Korisnik.class);
        Korisnik user = (Korisnik) query.add(Restrictions.eq("username", username)).add(Restrictions.eq("password", password)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        if(user!=null) {
            poruka="";
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            hs.setAttribute("user", user);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/"+user.getTip()+".xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
        else{
            //FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
            poruka = "Pogresno korisnicko ime ili lozinka!";
        }       
    }
    
    public String logout(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession s = (HttpSession) fc.getExternalContext().getSession(false);
        s.invalidate();
        return "index?faces-redirect=true";
    }
    
    public void redirect_registracija() throws IOException{
        poruka="";
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/registracija.xhtml");
    }
}
