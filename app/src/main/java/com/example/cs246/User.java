package com.example.cs246;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String first_name;
    public String last_name;
    public List<String> allergies;
    public List<String> items;
    public String ID;

    public User(String FIRSTNAME, String LASTNAME, List<String> ALLERGIES) {
        this.first_name = FIRSTNAME;
        this.last_name = LASTNAME;
        this.allergies = ALLERGIES;
        this.items = new ArrayList<String>();
        ID = "0";
    }

    public User(String FIRSTNAME, String LASTNAME, String ID, List<String> ALLERGIES) {
        this.first_name = FIRSTNAME;
        this.last_name = LASTNAME;
        this.allergies = ALLERGIES;
        this.items = new ArrayList<String>();
        this.ID = ID;
    }
}
