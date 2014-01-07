package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import models.Candidate;
import models.Donation;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class AdminPanel extends Controller {

    public static void index(String donorsAge, String donorsRegion, long donorsId, long candidateId) {
        User user = Accounts.getCurrentUser();
        List<Candidate> candidates = Candidate.findAll();
        List<User> users = User.findAll();
        List<User> filteredUsers = new ArrayList<User>(users);
        users.remove(user);
        Logger.info("users size: " + users.size());
        Logger.info("candidates size: " + candidates.size());
        // render(user, users, filteredUsers, candidates);

        // admin filters
        filteredUsers.remove(user);
        // age filter
        Logger.info("age range= " + donorsAge);
        if (donorsAge == null || donorsAge.length() < 5) donorsAge = "00-99";
        int ageStart = Integer.parseInt(donorsAge.substring(0, 2));
        int ageEnd = Integer.parseInt(donorsAge.substring(3, 5));
        for (User u : users) {
            if (u.age < ageStart || u.age > ageEnd) {
                filteredUsers.remove(u);
            }
        }
        // country filter
        if (donorsRegion != null && donorsRegion.length() > 1) {
            String str = donorsRegion.trim().toLowerCase();
            for (User u : users) {
                if (!u.region.trim().toLowerCase().equals(str)) {
                    filteredUsers.remove(u);
                }
            }
        }

        // user filter
        Logger.info("donors id= " + donorsId);
        if (donorsId > 0) {
            User select = User.findById((long) donorsId);
            filteredUsers.removeAll(filteredUsers);
            filteredUsers.add(select);
        }
        
        //candidate filter
        if (candidateId > 0) {
//            Candidate candidate = Candidate.findById(candidateId);
//            for(User u: filteredUsers){
//                for(Donation d: u.donations){
//                    if(d.candidate.id != candidateId)
//                }
//            }
        }
        render(users, filteredUsers,candidates,candidateId);
    }
    
    public static void createCandidate(@Valid String firstName, @Valid String lastName, @Valid String office)
    {
        Candidate createdCand = new Candidate(firstName, lastName, office);
        
        validation.match(firstName, "[A-Za-z]+"); // Alphabetic characters only
        validation.match(lastName, "[A-Za-z]+"); // Alphabetic characters only
        validation.match(office, "[A-Za-z]+"); // Alphabetic characters only
        
     // Handle errors
        if(validation.hasErrors()) {
            render("AdminPanel/createCandidate.html");
        }
        
        createdCand.save();
        Logger.info("candidate created " + firstName + " "
                + lastName + " " + office);
        render();
    }
}
