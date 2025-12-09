package ca.umanitoba.comp2450.exercisetracker;

import ca.umanitoba.comp2450.exercisetracker.ui.MapDisplay;


public class Main {
    public static void main(String[] args) {
        //creating the main UI controlling object that will handle all user interactions
        MapDisplay display = new MapDisplay();
        display.start();
        //Displaying a farewell message to the user once the program terminates
        System.out.println("See you later!");
    }
}


