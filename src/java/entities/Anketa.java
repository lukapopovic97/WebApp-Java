/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 *
 * @author Luka
 */
@Entity
public class Anketa extends Upitnik{
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<PitanjeAnketa> pitanja;
    private boolean personalizovana;
    
    public Collection<PitanjeAnketa> getPitanja() {
        return pitanja;
    }

    public void setPitanja(Collection<PitanjeAnketa> pitanja) {
        this.pitanja = pitanja;
    }

    public boolean isPersonalizovana() {
        return personalizovana;
    }

    public void setPersonalizovana(boolean personalizovana) {
        this.personalizovana = personalizovana;
    }
    
    public int broj_pitanja(){
        return pitanja.size();
    }
    
}
