package com.example.cs246;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static boolean addUser(String username, String password, List<String> allergies) throws Exception {
       if(username=="" || username==null)
           throw new Exception("Username is null or empty");

       if(password=="" || password==null)
           throw new Exception("Password is null or empty");

       //TODO
        //Do stuff

        return true;
    }

    public static boolean verifyUser(String username, String password) throws Exception {
        if(username=="" || username==null)
            throw new Exception("Username is null or empty");

        if(password=="" || password==null)
            throw new Exception("Password is null or empty");

        //TODO verify user

        return true;
    }

    public static List<Item> getItemsFromRestaurantID (String ID) throws Exception {
        if(ID=="" || ID==null) {
            throw new Exception ("ID is null or empty");
        }

        //TODO get data from database

        return new ArrayList<Item>();
    }

    public static void addItemToRestaurant(Item item, String restaurantID) throws Exception {
        if(restaurantID=="" || restaurantID==null)
            throw new Exception("ID is null or empty");

        //TODO add item to database
    }


}
