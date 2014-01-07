package controllers;

import play.*;
import play.data.validation.Valid;
import play.mvc.*;
import models.*;
import controllers.BCrypt;


public class Accounts extends Controller {
    public static void index() {
        render();
    }

    public static void signup() {
        render();
    }

    public static void register(@Valid String firstName,
            @Valid String lastName, @Valid String email, @Valid String password, @Valid int age,
            @Valid String addressLineOne, @Valid String addressLineTwo, String country,
            String region) {
        
        validation.match(firstName, "[A-Za-z]+"); // Alphabetic characters only
        validation.match(lastName, "[A-Za-z]+"); // Alphabetic characters only
        validation.email(email); // Email address containing @
        validation.match(password, "^(?=.*?[0-9].*?[0-9])(?=.*[!@#$%])[0-9a-zA-Z!@#$%0-9]{8,}$"); // Password at least 8 characters long with at least 2 digits and 2 special characters
        validation.range(age, 18, 120); // Age between 18 and 120
        validation.match(addressLineOne, "^[A-Za-z0-9 -]*[A-Za-z0-9][A-Za-z0-9 -]*$"); // Address containing capital and lowercase letters and "-" sign
        validation.match(addressLineTwo, "^[A-Za-z0-9 -]*[A-Za-z0-9][A-Za-z0-9 -]*$"); // Address containing capital and lowercase letters and "-" sign
        
     // Handle errors
        if(validation.hasErrors()) {
            render("Accounts/signup.html");
        }
        
        Logger.info(firstName + " " + lastName + " " + email + " " + password
                + " " + age + " " + addressLineOne + " " + addressLineTwo + " "
                + country + " " + region);
        User user = new User(firstName, lastName, email, password, age,
                addressLineOne, addressLineTwo, country, region);
        user.save();
        index();
    }

    public static void login() {
        //render();
          String str = "";
          if (session.contains("Wrong Login"))
          {
            str = session.get("Wrong Login");
          }
          boolean wrongLogin = (str.equals("true")) ? true : false;

          render(wrongLogin);
    }

    public static void logout() {
        session.clear();
        index();
    }

    public static void authenticate(@Valid String email, @Valid String password) {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        User user = User.findByEmail(email);
        if (user == null) {
            user = Admin.findByEmail(email);
        }
        Logger.info("User: " + user);
        if ((user != null) && (user.checkPassword(password) == true)) {
            Logger.info("Successfull authentication of  " + user.firstName
                    + " " + user.lastName);
            session.put("logged_in_userid", user.id);
            if (user instanceof models.Admin) AdminPanel.index(null, null,-1,-1);
            else DonationController.index();
        }

        else {
            // 1st option
            //Logger.info("Authentication failed");
            //boolean wrongLogin = true;
            //session.put("Wrong Login", wrongLogin);
            //login();
            
            // 2nd option
            validation.email(email); // Email address containing @
            validation.match(password, "^(?=.*?[0-9].*?[0-9])(?=.*[!@#$%])[0-9a-zA-Z!@#$%0-9]{8,}$"); // Password at least 8 characters long with at least 2 digits and 2 special characters
            
            if(validation.hasErrors()) {
                render("Accounts/login.html");
            }
        }
    }

    public static User getCurrentUser() {
        String userId = session.get("logged_in_userid");
        if (userId == null) {
            index();
        }
        User logged_in_user = User.findById(Long.parseLong(userId));
        Logger.info("In Accounts controller: Logged in user is "
                + logged_in_user.firstName);
        return logged_in_user;
    }
}