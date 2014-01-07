package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@SuppressWarnings("serial")
@Entity
public class Candidate extends Model {

    @Required @MinSize(3) public String firstName;
    @Required @MinSize(3) public String lastName;
    @Required @MinSize(3) public String office;

    public Candidate() 
    {}

    public Candidate(@Valid String firstName, @Valid String lastName, @Valid String office) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.office = office;
    }

    public String toString() 
    {
        return firstName + ", " + lastName + ", " + office;
    }
}
