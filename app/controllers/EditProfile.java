package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class EditProfile extends Controller {
	public static void index() {
		User user = Accounts.getCurrentUser();
		render(user);
	}

	public static void editDetails(String firstName, String lastName,
			String email, String password, Integer age, String addressLineOne, String addressLineTwo, String country, String region) {
		
	    validation.match(firstName, "[A-Za-z]+"); // Alphabetic characters only
        validation.match(lastName, "[A-Za-z]+"); // Alphabetic characters only
        validation.email(email); // Email address containing @
        validation.match(password, "^(?=.*?[0-9].*?[0-9])(?=.*[!@#$%])[0-9a-zA-Z!@#$%0-9]{8,}$"); // Password at least 8 characters long with at least 2 digits and 2 special characters
        validation.range(age, 18, 120); // Age between 18 and 120
        validation.match(addressLineOne, "^[A-Za-z0-9 -]*[A-Za-z0-9][A-Za-z0-9 -]*$"); // Address containing capital and lowercase letters and "-" sign
        validation.match(addressLineTwo, "^[A-Za-z0-9 -]*[A-Za-z0-9][A-Za-z0-9 -]*$"); // Address containing capital and lowercase letters and "-" sign
        
     // Handle errors
        if(validation.hasErrors()) {
            render("EditProfile/index.html");
        }
	    
	    User user = Accounts.getCurrentUser();

		if (firstName.length() > 0) {
			user.firstName = firstName;
		}
		if (lastName.length() > 0) {
			user.lastName = lastName;
		}
		if (email.length() > 0) {
			user.email = email;
		}
		if (password.length() > 0) {
			user.password = password;
		}

		user.age = age;

		if (addressLineOne.length() > 0) {
			user.addressLineOne = addressLineOne;
		}
		
		if (addressLineTwo.length() > 0) {
			user.addressLineTwo = addressLineTwo;
		}
		
		if (country.length() > 0) {
			user.country = country;
		}
		
		if (region.length() > 0) {
			user.region = region;
		}
		
		user.save();

		Logger.info("First Name changed to " + firstName
				+ "Last Name changed to " + lastName + "Email changed to "
				+ email + "Password changed to " + password + "Age changed to " + age + "Address Line 1 changed to " + addressLineOne + "Address Line 2 changed to " + addressLineTwo + "Country changed to " + country + "Region changed to " + region);
		index();

	}
}
