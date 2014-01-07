package controllers;

import play.*;
import play.mvc.*;
import utils.JsonParsers;

import java.util.*;

import com.google.gson.JsonElement;

import models.*;

public class DonationsAPI extends Controller
{
  public static void donations(Long userId)
  {
    User user = User.findById(userId);
    List<Donation> donations = user.donations;
    renderText(JsonParsers.donation2Json(donations));
  }
  
  public static void donation (Long userId, Long id)
  {
    User user = User.findById(userId);
    Donation donation = Donation.findById(id);
    if (user.donations.contains(donation))
    { 
      renderJSON (JsonParsers.donation2Json(donation));
    }
    else
    {
      badRequest();
    }
  }
  
  public static void createDonation(Long userId, JsonElement body)
  {
    User user = User.findById(userId);
    Logger.info("getting here");
    Donation donation = JsonParsers.json2Donation(body.toString());
    Logger.info("getting here 2");
    Donation newDonation = new Donation ( donation.candidate, donation.amount, donation.method);
    Logger.info("getting here 3");
    user.donations.add(donation);
    user.save();
    renderJSON (JsonParsers.donation2Json(newDonation));
  }  
  
  public static void deleteDonation(Long userId, Long id)
  {
    User user = User.findById(userId);
    Donation donation = Donation.findById(id);
    if (!user.donations.contains(donation))
    {
      notFound();
    }
    else
    {
      user.donations.remove(donation);
      donation.delete();
      user.save();
      ok();
    }
  }  
}
