package controllers;

import play.*;
import play.mvc.*;
import java.util.*;

import models.*;

public class DonationController extends Controller {
    public static void index() {
        User user = Accounts.getCurrentUser();
        List<Candidate> candidates = Candidate.findAll();
        List<User> users = User.findAll();
        List<User> filteredUsers = User.findAll();
        if (user instanceof models.Admin) users.remove(user);
        Logger.info("candidates size: " + candidates.size());
        String prog = getPercentTargetAchieved();
        String progress = prog + "%";
        Logger.info("Donation ctrler : user is " + user.email);
        Logger.info("Donation ctrler : percent target achieved " + progress);
        render(user, users, filteredUsers, candidates, progress);
    }

    public static void donate(long candidateId, int amountDonated,
            String methodDonated) {
        User user = Accounts.getCurrentUser();
        Candidate candidate = Candidate.findById(candidateId);
        Logger.info("candidate donated " + candidate.firstName + "amount donated "
                + amountDonated + " " + "method donated " + methodDonated);
        Donation donation = new Donation(candidate, amountDonated,
                methodDonated);
        donation.save();
        user.donations.add(donation);
        user.save();
//        candidate.addDonation(donation);
        index();
    }

    private static long getDonationTarget() {
        return 20000;
    }

    public static String getPercentTargetAchieved() {
        List<Donation> allDonations = Donation.findAll();
        long total = 0;
        for (Donation donation : allDonations) {
            total += donation.amount;
        }
        long target = getDonationTarget();
        long percentachieved = (total * 100 / target);
        String progress = String.valueOf(percentachieved);
        Logger.info("Percent of target achieved (string) " + progress
                + " percentachieved (long)= " + percentachieved);
        return progress;
    }
    
    public static void renderReport(long candidateId) {
        User user = Accounts.getCurrentUser();
        List<Candidate> candidates = Candidate.findAll(); 
        Logger.info("candidates size: " + candidates.size());
       //candidate filter
        if (candidateId > 0) {
            Logger.info("candidate id: " + candidateId);
        }
        render(user, candidates, candidateId);
      }
    }

