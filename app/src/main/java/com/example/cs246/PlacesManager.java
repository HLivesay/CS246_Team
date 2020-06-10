package com.example.cs246;

import java.util.ArrayList;
import java.util.List;

public class PlacesManager {

    public static List<String> getRestaurantIDinRange(int range) throws Exception {
        if(range< 0 || range > 150)
            throw new Exception ("Range must be nonnegative and less than 150");

        return new ArrayList<String>();
    }
}
