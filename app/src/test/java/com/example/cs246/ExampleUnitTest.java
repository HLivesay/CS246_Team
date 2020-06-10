package com.example.cs246;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void modelTests() {
        assertEquals(4, 2 + 2);

        //Test add user exception handling
        ArrayList<String> allergies = new ArrayList<String>();
        allergies.add("Hello");
        try {
            assertEquals(false, DatabaseManager.addUser("", "", allergies));
        } catch (Exception e) {
            System.out.println("Test passed");
        }

        //Test verify user exception handling
        try {
            assertEquals(false, DatabaseManager.verifyUser("", ""));
        } catch (Exception e) {
            System.out.println("Test passed");
        }

        //Test adding and verifying user
        try {
            DatabaseManager.addUser("Username" ,"Password", allergies);
            assertEquals(true, DatabaseManager.verifyUser("Username", "Password"));
        } catch (Exception e) {
            System.out.print("Test failed\n    -");
            System.out.print(e.getMessage());
        }

        //Test getItems from ID exception handling
        try {
            assertEquals(false, DatabaseManager.getItemsFromRestaurantID(""));
        } catch (Exception e) {
            System.out.println("Test passed");
        }

        //Test places manager exception  handling
        try {
            assertEquals(false, PlacesManager.getRestaurantIDinRange(-1));
        } catch(Exception e) {
            System.out.println("Test passed");
        }
        try {
            assertEquals(false, PlacesManager.getRestaurantIDinRange(200));
        } catch(Exception e) {
            System.out.println("Test passed");
        }
        //Test adding and retrieving item from database
        try {
            Item t = new Item();
            DatabaseManager.addItemToRestaurant(t, "1");
            assertEquals(t, DatabaseManager.getItemsFromRestaurantID("1").get(0));
        } catch(Exception e) {
            System.out.print("Test failed\n    -");
            System.out.print(e.getMessage());
        }
    }
}