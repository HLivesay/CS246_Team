package com.example.cs246;

import org.junit.Test;

public class DatabaseTests {
    @Test
    public void doTests() {
        DatabaseManager.init();
        try {
            DatabaseManager.AddData("Test", "Users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
