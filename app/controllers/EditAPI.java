package controllers;

import play.*;
import play.mvc.*;
import utils.JsonParsers;

import java.util.*;

import com.google.gson.JsonElement;

import models.*;

public class EditAPI extends Controller{
    
    public static void editUser(JsonElement body) {
        User updatedUser = JsonParsers.json2User(body.toString());
        User oldUser = User.findByEmail(updatedUser.email);

        if (updatedUser.firstName.length() > 0) {
            oldUser.firstName = updatedUser.firstName;
        }
        if (updatedUser.lastName.length() > 0) {
            oldUser.lastName = updatedUser.lastName;
        }
        if (updatedUser.email.length() > 0) {
            oldUser.email = updatedUser.email;
        }
        if (updatedUser.password.length() > 0) {
            oldUser.password = updatedUser.password;
        }
        if(updatedUser.age >=0){
        oldUser.age = updatedUser.age;
        }
        
        if (updatedUser.addressLineOne.length() > 0) {
            oldUser.addressLineOne = updatedUser.addressLineOne;
        }

        if (updatedUser.addressLineTwo.length() > 0) {
            oldUser.addressLineTwo = updatedUser.addressLineTwo;
        }

        if (updatedUser.country.length() > 0) {
            oldUser.country = updatedUser.country;
        }

        if (updatedUser.region.length() > 0) {
            oldUser.region = updatedUser.region;
        }
        
        oldUser.save();
        renderJSON(JsonParsers.user2Json(oldUser));
    }


}
