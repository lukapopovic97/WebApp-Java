/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.HibernateUtil;
import entities.Anketa;
import entities.AnketaOdgovori;
import entities.Korisnik;
import entities.PitanjeTest;
import entities.Test;
import entities.TestOdgovori;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
@Named(value = "ispitanikcontroller")
@SessionScoped
public class IspitanikController implements Serializable{
    private Test testIzrada;
    private List<Test> testoviPregled;
    private TestOdgovori testOdgovori;
    
    private Anketa anketaIzrada;
    private List<Anketa> anketePregled;
    private AnketaOdgovori anketaOdgovori;
    
    public IspitanikController(){
        dohvatiTestove();
        dohvatiAnkete();
    }
    
    public Test getTestIzrada() {
        return testIzrada;
    }

    public void setTestIzrada(Test testIzrada) {
        this.testIzrada = testIzrada;
    }

    public List<Test> getTestoviPregled() {
        return testoviPregled;
    }

    public void setTestoviPregled(List<Test> testoviPregled) {
        this.testoviPregled = testoviPregled;
    }

    public TestOdgovori getTestOdgovori() {
        return testOdgovori;
    }

    public void setTestOdgovori(TestOdgovori testOdgovori) {
        this.testOdgovori = testOdgovori;
    }

    public Anketa getAnketaIzrada() {
        return anketaIzrada;
    }

    public void setAnketaIzrada(Anketa anketaIzrada) {
        this.anketaIzrada = anketaIzrada;
    }

    public List<Anketa> getAnketePregled() {
        return anketePregled;
    }

    public void setAnketePregled(List<Anketa> anketePregled) {
        this.anketePregled = anketePregled;
    }

    public AnketaOdgovori getAnketaOdgovori() {
        return anketaOdgovori;
    }

    public void setAnketaOdgovori(AnketaOdgovori anketaOdgovori) {
        this.anketaOdgovori = anketaOdgovori;
    }
    
    public void dohvatiTestove(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Test.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        testoviPregled=query.list();
        
        session.getTransaction().commit();
        session.close();
        
    }
    
    public void dohvatiAnkete(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Anketa.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        anketePregled=query.list();
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void radiTest() throws IOException{
        Collections.shuffle((List<?>) testIzrada.getPitanja());
        testOdgovori=new TestOdgovori(testIzrada.getId(), testIzrada.getPitanja(), testIzrada.getTrajanje());
        FacesContext.getCurrentInstance().getExternalContext().redirect("izradaTesta.xhtml");
    }
    
    public void radiAnketu() throws IOException{
        anketaOdgovori=new AnketaOdgovori(anketaIzrada.getId(), anketaIzrada.getPitanja());
        FacesContext.getCurrentInstance().getExternalContext().redirect("izradaAnkete.xhtml");
    }
    
    public void redirect_ispitanik_pocetna() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("ispitanik.xhtml");
    }
    
    public void redirect_rezultatTest() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatTest.xhtml");
    }
    
    public void redirect_rezultatAnketa() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatAnketa.xhtml");
    }
    
    public void rezultatT() throws IOException{
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(TestOdgovori.class).add(Restrictions.eq("username", user.getUsername())).add(Restrictions.eq("idTesta", testIzrada.getId()));
        testOdgovori = (TestOdgovori) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        for(int i = 0; i < testOdgovori.getOdgovori().size();i++){
            testOdgovori.getOdgovori().get(i).postaviPitanje();
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatTest.xhtml");
    }
    
    public void rezultatA() throws IOException{
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(AnketaOdgovori.class).add(Restrictions.eq("username", user.getUsername())).add(Restrictions.eq("idAnkete", anketaIzrada.getId()));
        anketaOdgovori = (AnketaOdgovori) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        for(int i = 0; i < anketaOdgovori.getOdgovori().size();i++){
            anketaOdgovori.getOdgovori().get(i).postaviPitanje();
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatAnketa.xhtml");
    }
    
    public boolean render_radiT(){
        if(testIzrada != null && !radjen() && !render_istekao()) return true;
        else return false;
    }
    
    public boolean render_radiA(){
        if(anketaIzrada != null && !radjena() && !render_istekla()) return true;
        else return false;
    }
  
    public boolean render_rezultatT(){
        if(testIzrada != null && radjen()) return true;
        else return false;
    }
    
    public boolean render_rezultatA(){
        if(anketaIzrada != null && radjena()) return true;
        else return false;
    }
    
    public boolean render_istekao(){
        if(testIzrada != null && testIzrada.getDatumKraja().after(new Date(System.currentTimeMillis()))) return false;
        else return true;
    }
    
    public boolean render_istekla(){
        if(anketaIzrada != null && anketaIzrada.getDatumKraja().after(new Date(System.currentTimeMillis()))) return false;
        else return true;
    }
    
    public boolean radjen(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(TestOdgovori.class).add(Restrictions.eq("username", user.getUsername())).add(Restrictions.eq("idTesta", testIzrada.getId()));
        TestOdgovori radjen = (TestOdgovori) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        if(radjen==null)return false;
        else return true;
    }
    
    public boolean radjena(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(AnketaOdgovori.class).add(Restrictions.eq("username", user.getUsername())).add(Restrictions.eq("idAnkete", anketaIzrada.getId()));
        AnketaOdgovori radjena = (AnketaOdgovori) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        if(radjena==null)return false;
        else return true;
    }
    
    public List<String> postaviPonudjeneOdgovore(PitanjeTest pitanje){
        List<String> netacniOdgovori, tacniOdgovori;
        netacniOdgovori = pitanje.listaNetacnihOdgovora();
        tacniOdgovori = pitanje.listaTacnihOdgovora();
        
        ArrayList<String> ponudjeniOdgovori=new ArrayList<>();
        ponudjeniOdgovori.addAll(tacniOdgovori);
        if(netacniOdgovori != null) ponudjeniOdgovori.addAll(netacniOdgovori);
        Collections.shuffle(ponudjeniOdgovori);
        return ponudjeniOdgovori;
    }

    public void redirect_nazad_na_test() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("izradaTesta.xhtml");
    }
    
    public void redirect_nazad_na_anketu() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("izradaAnkete.xhtml");
    }
    
    public void redirect_progress_bar_test() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("progressBarTest.xhtml");
    }
    
    public void redirect_progress_bar_anketa() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("progressBarAnketa.xhtml");
    }
}
