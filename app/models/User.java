package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import controllers.BCrypt;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Range;
import play.db.jpa.Model;

@SuppressWarnings("serial")
@Entity
@Table(name="my_user")
public class User extends Model
{
    @Required @MinSize(3) public String firstName;
    @Required @MinSize(3) public String lastName;
    @Required @Email public String email;
    @Required @MinSize(2) public String password;
    @Required @Range(min=18, max=120) public int age; 
    @Required @MinSize(3) public String addressLineOne; 
    @Required @MinSize(3) public String addressLineTwo;
    @Required public String country;
    @Required public String region; 
  
  @OneToMany(cascade = CascadeType.ALL)
  public List<Donation> donations = new ArrayList<Donation>();
   
  public User()
  {}
  
  public User(@Valid String firstName, @Valid String lastName, @Valid String email, @Valid String password, @Valid int age, @Valid String addressLineOne, @Valid String addressLineTwo, @Valid String country, @Valid String region)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    
    this.age = age;
    this.addressLineOne = addressLineOne;
    this.addressLineTwo = addressLineTwo;
    this.country = country;
    this.region = region;
  } 
  
  public static User findByEmail(String email)
  {
    return find("email", email).first();
  }  
  
  public boolean checkPassword(String candidate)
  {
      return BCrypt.checkpw(candidate, this.password);
      //return this.password.equals(password);
  }  
}