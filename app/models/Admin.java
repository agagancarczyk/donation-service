package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;


@SuppressWarnings("serial")
@Entity
public class Admin extends User
{
    public String username;
    
    public Admin()
    {}
    
    public String toString()
    {
      return username + ", " + password;
    }
}
