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
import entities.PitanjeAnketa;
import entities.PitanjeTest;
import entities.Test;
import entities.TestOdgovori;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "autorcontroller")
@SessionScoped
public class AutorController implements Serializable {

    private PitanjeTest pitanjeT;
    private List<String> tacni_odgovori;
    private List<String> netacni_odgovori;
    int broj_pitanja;
    private List<PitanjeTest> pitanjaT;
    private Test noviTest;
    private List<PitanjeTest> postojecaPitanjaT;
    private List<Test> testoviPregled;
    private Test testPregled;

    private PitanjeAnketa pitanjeA;
    private List<PitanjeAnketa> pitanjaA;
    private Anketa novaAnketa;
    private List<PitanjeAnketa> postojecaPitanjaA;
    private List<Anketa> anketePregled;
    private Anketa anketaPregled;
    private List<String> ponudjeni_odgovori;

    private List<TestOdgovori> rezultati_testova;
    private List<AnketaOdgovori> rezultati_anketa;
    private boolean personalizovana_anketa_rezultati;
    private AnketaOdgovori pregled_odgovora_ankete;

    public AnketaOdgovori getPregled_odgovora_ankete() {
        return pregled_odgovora_ankete;
    }

    public void setPregled_odgovora_ankete(AnketaOdgovori pregled_odgovora_ankete) {
        this.pregled_odgovora_ankete = pregled_odgovora_ankete;
    }
    
    public boolean isPersonalizovana_anketa_rezultati() {
        return personalizovana_anketa_rezultati;
    }

    public void setPersonalizovana_anketa_rezultati(boolean personalizovana_anketa_rezultati) {
        this.personalizovana_anketa_rezultati = personalizovana_anketa_rezultati;
    }
    
    public AutorController() {
        pitanjeT = new PitanjeTest();
        tacni_odgovori = new ArrayList<>();
        netacni_odgovori = new ArrayList<>();
        pitanjaT = new ArrayList<>();
        broj_pitanja = 0;
        noviTest = new Test();
        dohvatiTestove();

        pitanjeA = new PitanjeAnketa();
        ponudjeni_odgovori = new ArrayList<>();
        pitanjaA = new ArrayList<>();
        novaAnketa = new Anketa();
        dohvatiAnkete();
    }

    public PitanjeTest getPitanjeT() {
        return pitanjeT;
    }

    public void setPitanjeT(PitanjeTest pitanjeT) {
        this.pitanjeT = pitanjeT;
    }

    public List<String> getTacni_odgovori() {
        return tacni_odgovori;
    }

    public void setTacni_odgovori(List<String> tacni_odgovori) {
        this.tacni_odgovori = tacni_odgovori;
    }

    public List<String> getNetacni_odgovori() {
        return netacni_odgovori;
    }

    public void setNetacni_odgovori(List<String> netacni_odgovori) {
        this.netacni_odgovori = netacni_odgovori;
    }

    public int getBroj_pitanja() {
        return broj_pitanja;
    }

    public void setBroj_pitanja(int broj_pitanja) {
        this.broj_pitanja = broj_pitanja;
    }

    public List<PitanjeTest> getPitanjaT() {
        return pitanjaT;
    }

    public void setPitanjaT(List<PitanjeTest> pitanjaT) {
        this.pitanjaT = pitanjaT;
    }

    public Test getNoviTest() {
        return noviTest;
    }

    public void setNoviTest(Test noviTest) {
        this.noviTest = noviTest;
    }

    public List<PitanjeTest> getPostojecaPitanjaT() {
        return postojecaPitanjaT;
    }

    public void setPostojecaPitanjaT(List<PitanjeTest> postojecaPitanjaT) {
        this.postojecaPitanjaT = postojecaPitanjaT;
    }

    public List<Test> getTestoviPregled() {
        return testoviPregled;
    }

    public void setTestoviPregled(List<Test> testoviPregled) {
        this.testoviPregled = testoviPregled;
    }

    public Test getTestPregled() {
        return testPregled;
    }

    public void setTestPregled(Test testPregled) {
        this.testPregled = testPregled;
    }

    public PitanjeAnketa getPitanjeA() {
        return pitanjeA;
    }

    public void setPitanjeA(PitanjeAnketa pitanjeA) {
        this.pitanjeA = pitanjeA;
    }

    public List<PitanjeAnketa> getPitanjaA() {
        return pitanjaA;
    }

    public void setPitanjaA(List<PitanjeAnketa> pitanjaA) {
        this.pitanjaA = pitanjaA;
    }

    public Anketa getNovaAnketa() {
        return novaAnketa;
    }

    public void setNovaAnketa(Anketa novaAnketa) {
        this.novaAnketa = novaAnketa;
    }

    public List<PitanjeAnketa> getPostojecaPitanjaA() {
        return postojecaPitanjaA;
    }

    public void setPostojecaPitanjaA(List<PitanjeAnketa> postojecaPitanjaA) {
        this.postojecaPitanjaA = postojecaPitanjaA;
    }

    public List<Anketa> getAnketePregled() {
        return anketePregled;
    }

    public void setAnketePregled(List<Anketa> anketePregled) {
        this.anketePregled = anketePregled;
    }

    public Anketa getAnketaPregled() {
        return anketaPregled;
    }

    public void setAnketaPregled(Anketa anketaPregled) {
        this.anketaPregled = anketaPregled;
    }

    public List<String> getPonudjeni_odgovori() {
        return ponudjeni_odgovori;
    }

    public void setPonudjeni_odgovori(List<String> ponudjeni_odgovori) {
        this.ponudjeni_odgovori = ponudjeni_odgovori;
    }

    public List<TestOdgovori> getRezultati_testova() {
        return rezultati_testova;
    }

    public void setRezultati_testova(List<TestOdgovori> rezultati_testova) {
        this.rezultati_testova = rezultati_testova;
    }

    public List<AnketaOdgovori> getRezultati_anketa() {
        return rezultati_anketa;
    }

    public void setRezultati_anketa(List<AnketaOdgovori> rezultati_anketa) {
        this.rezultati_anketa = rezultati_anketa;
    }

    public void dodajPitanjeT() {
        PitanjeTest novo = new PitanjeTest();
        novo.setTekst_pitanja(pitanjeT.getTekst_pitanja());
        novo.setTip(pitanjeT.getTip());
        novo.setPoeni(pitanjeT.getPoeni());
        String odg;
        StringBuilder svi_tacni_odgovori = new StringBuilder();
        while (!tacni_odgovori.isEmpty()) {
            odg = tacni_odgovori.remove(0);
            svi_tacni_odgovori.append(odg);
            svi_tacni_odgovori.append("#");
        }
        novo.setTacni_odgovori(svi_tacni_odgovori.toString());
        StringBuilder svi_netacni_odgovori = new StringBuilder();
        if (netacni_odgovori != null) {
            while (!netacni_odgovori.isEmpty()) {
                odg = netacni_odgovori.remove(0);
                svi_netacni_odgovori.append(odg);
                svi_netacni_odgovori.append("#");
            }
            novo.setNetacni_odgovori(svi_netacni_odgovori.toString());
        }
        novo.getTestovi().add(noviTest);
        pitanjaT.add(novo);
        broj_pitanja++;
        pitanjeT.setTekst_pitanja("");
        pitanjeT.setPoeni(3.00);
    }

    public void dodajPitanjeA() {
        PitanjeAnketa novo = new PitanjeAnketa();
        novo.setTekst_pitanja(pitanjeA.getTekst_pitanja());
        novo.setTip(pitanjeA.getTip());
        novo.setObavezno(pitanjeA.isObavezno());
        String odg;
        StringBuilder svi_ponudjeni_odgovori = new StringBuilder();
        if (ponudjeni_odgovori != null) {
            while (!ponudjeni_odgovori.isEmpty()) {
                odg = ponudjeni_odgovori.remove(0);
                svi_ponudjeni_odgovori.append(odg);
                svi_ponudjeni_odgovori.append("#");
            }
            novo.setPonudjeni_odgovori(svi_ponudjeni_odgovori.toString());
        }
        novo.getAnkete().add(novaAnketa);
        pitanjaA.add(novo);
        broj_pitanja++;
        pitanjeA.setTekst_pitanja("");
    }

    public void dodajPostojecePitanjeT(PitanjeTest pitanje) throws IOException {
        pitanjaT.add(pitanje);
        broj_pitanja++;
        redirect_novi_test();
    }

    public void dodajPostojecePitanjeA(PitanjeAnketa pitanje) throws IOException {
        pitanjaA.add(pitanje);
        broj_pitanja++;
        redirect_nova_anketa();
    }

    public void kreiraj_test() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.getTransaction().begin();

        for (int i = 0; i < pitanjaT.size(); i++) {
            pitanjaT.get(i).getTestovi().add(noviTest);
            if (pitanjaT.get(i).getId() != 0) {
                session.update(pitanjaT.get(i));
            } else {
                session.save(pitanjaT.get(i));
            }
        }

        noviTest.setPitanja(pitanjaT);
        noviTest.setAutorUsername(user.getUsername());
        session.save(noviTest);

        session.getTransaction().commit();
        session.close();

        pitanjaT = new ArrayList<>();
        broj_pitanja = 0;
        noviTest = new Test();

        dohvatiTestove();
        redirect_autor_pocetna();
    }

    public void kreiraj_anketu() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.getTransaction().begin();

        for (int i = 0; i < pitanjaA.size(); i++) {
            pitanjaA.get(i).getAnkete().add(novaAnketa);
            if (pitanjaA.get(i).getId() != 0) {
                session.update(pitanjaA.get(i));
            } else {
                session.save(pitanjaA.get(i));
            }
        }

        novaAnketa.setPitanja(pitanjaA);
        novaAnketa.setAutorUsername(user.getUsername());
        session.save(novaAnketa);

        session.getTransaction().commit();
        session.close();

        pitanjaA = new ArrayList<>();
        broj_pitanja = 0;
        novaAnketa = new Anketa();

        dohvatiAnkete();
        redirect_autor_pocetna();
    }

    public void redirect_postojeca_pitanjaT() throws IOException {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(PitanjeTest.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        postojecaPitanjaT = query.list();

        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().getExternalContext().redirect("postojecaPitanjaT.xhtml");
    }

    public void redirect_postojeca_pitanjaA() throws IOException {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(PitanjeAnketa.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        postojecaPitanjaA = query.list();

        session.getTransaction().commit();
        session.close();

        FacesContext.getCurrentInstance().getExternalContext().redirect("postojecaPitanjaA.xhtml");
    }

    public void redirect_novi_test() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("noviTest.xhtml");
    }

    public void redirect_nova_anketa() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("novaAnketa.xhtml");
    }

    public void redirect_autor_pocetna() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("autor.xhtml");
    }

    public void ponisti_i_redirect_autor_pocetna() throws IOException {
        pitanjaT = new ArrayList<>();
        pitanjaA = new ArrayList<>();
        broj_pitanja = 0;
        FacesContext.getCurrentInstance().getExternalContext().redirect("autor.xhtml");
    }

    public void redirect_test_pregled(Test test) throws IOException {
        testPregled = test;
        FacesContext.getCurrentInstance().getExternalContext().redirect("testPregled.xhtml");
    }

    public void redirect_anketa_pregled(Anketa anketa) throws IOException {
        anketaPregled = anketa;
        FacesContext.getCurrentInstance().getExternalContext().redirect("anketaPregled.xhtml");
    }

    public boolean render_obrisiT(Test test) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        if (test.getAutorUsername() != null && user.getUsername().equals(test.getAutorUsername())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean render_obrisiA(Anketa anketa) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Korisnik user = (Korisnik) hs.getAttribute("user");
        if (anketa.getAutorUsername() != null && user.getUsername().equals(anketa.getAutorUsername())) {
            return true;
        } else {
            return false;
        }
    }

    public void obrisiTest(Test test) {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(TestOdgovori.class).add(Restrictions.eq("idTesta", test.getId())).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<TestOdgovori> testOdgovori = query.list();
        if (testOdgovori != null) {
            TestOdgovori rem;
            while (!testOdgovori.isEmpty()) {
                rem = testOdgovori.remove(0);
                session.delete(rem);
            }
        }

        session.delete(test);
        session.getTransaction().commit();
        session.close();
        dohvatiTestove();
    }

    public void obrisiAnketu(Anketa anketa) {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(AnketaOdgovori.class).add(Restrictions.eq("idAnkete", anketa.getId())).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<AnketaOdgovori> anketaOdgovori = query.list();
        if (anketaOdgovori != null) {
            AnketaOdgovori rem;
            while (!anketaOdgovori.isEmpty()) {
                rem = anketaOdgovori.remove(0);
                session.delete(rem);
            }
        }

        session.delete(anketa);
        session.getTransaction().commit();
        session.close();
        dohvatiAnkete();
    }

    public void redirect_rezultatiT(Test test) throws IOException {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(TestOdgovori.class).add(Restrictions.eq("idTesta", test.getId())).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        rezultati_testova = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        for(int i=0;i<rezultati_testova.size();i++){
            rezultati_testova.get(i).postaviLicnePodatke();
        }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatiTestova.xhtml");
    }

    public void redirect_rezultatiA(Anketa anketa) throws IOException {
        personalizovana_anketa_rezultati=anketa.isPersonalizovana();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(AnketaOdgovori.class).add(Restrictions.eq("idAnkete", anketa.getId())).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        rezultati_anketa = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        for(int i=0;i<rezultati_anketa.size();i++){
            rezultati_anketa.get(i).postaviLicnePodatke();
        }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatiAnketa.xhtml");
    }

    public void redirect_pregled_odgovora_ankete(AnketaOdgovori a) throws IOException{
        for(int i =0;i<a.getOdgovori().size();i++){
            if(a.getOdgovori().get(i).getPitanje()==null)a.getOdgovori().get(i).postaviPitanje();
        }
        pregled_odgovora_ankete = a;
        FacesContext.getCurrentInstance().getExternalContext().redirect("pregledOdgovoraAnkete.xhtml");
    }
    
    public void redirect_back_rezultatiAnketa() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("rezultatiAnketa.xhtml");
    }
    
    public void dohvatiTestove() {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(Test.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        testoviPregled = query.list();

        session.getTransaction().commit();
        session.close();
    }

    public void dohvatiAnkete() {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();

        Criteria query = session.createCriteria(Anketa.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        anketePregled = query.list();

        session.getTransaction().commit();
        session.close();
    }
}
