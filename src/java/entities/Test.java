/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Collection;
import java.util.Date;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
/**
 *
 * @author Luka
 */
@Entity
public class Test extends Upitnik{
    
    private int trajanje;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<PitanjeTest> pitanja;
    
    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Collection<PitanjeTest> getPitanja() {
        return pitanja;
    }

    public void setPitanja(Collection<PitanjeTest> pitanja) {
        this.pitanja = pitanja;
    }
    
    public int broj_pitanja(){
        return pitanja.size();
    }
}
