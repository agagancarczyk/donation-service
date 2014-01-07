package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;


@SuppressWarnings("serial")
@Entity
@Table(name="my_admin")
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
