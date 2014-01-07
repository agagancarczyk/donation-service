package controllers;

import play.*;
import play.mvc.*;
import utils.JsonParsers;

import java.util.*;

import com.google.gson.JsonElement;

import models.*;

public class AdminsAPI extends Controller {
    public static void admins() {
        List<Admin> admins = Admin.findAll();
        renderJSON(JsonParsers.admin2Json(admins));
    }

    public static void admin(Long id) {
        Admin admin = Admin.findById(id);
        if (admin == null) {
            notFound();
        } else {
            renderJSON(JsonParsers.admin2Json(admin));
        }
    }

    public static void createAdmin(JsonElement body) {
        Admin admin = JsonParsers.json2Admin(body.toString());
        admin.save();
        renderJSON(JsonParsers.admin2Json(admin));
    }

    
    public static void deleteAdmin(Long id) {
        Admin admin = Admin.findById(id);
        if (admin == null) {
            notFound();
        } else {
            admin.delete();
            renderText("success");
        }
    }

    public static void deleteAllAdmins() {
        Admin.deleteAll();
        renderText("success");
    }
}
