package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import play.data.validation.Required;
import play.db.jpa.Model;

@SuppressWarnings("serial")
@Entity
public class Donation extends Model {
    @ManyToOne
    @Required public Candidate candidate;
    @Required public int amount;
    @Required public String method;

    public Donation() 
    {
    }

    public Donation(@Valid Candidate candidate, @Valid int amount, @Valid String method) 
    {
        this.candidate = candidate;
        this.amount = amount;
        this.method = method;
    }

    public String toString() 
    {
        return amount + ", " + method + " for: " + candidate;
    }
}
