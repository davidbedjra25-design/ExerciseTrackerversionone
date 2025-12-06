package ca.umanitoba.comp2450.exercisetracker;

import ca.umanitoba.comp2450.exercisetracker.gear.GearType;
import ca.umanitoba.comp2450.exercisetracker.model.Map;
import ca.umanitoba.comp2450.exercisetracker.model.Swimmer;
import ca.umanitoba.comp2450.exercisetracker.output.MapPrinter;
import ca.umanitoba.comp2450.exercisetracker.ui.MapDisplay;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        //creating the main UI controlling object that will handle all user interactions
        MapDisplay display = new MapDisplay();

        //loop to keep showing options until the user chooses exit
        boolean running = true;
        while(running) {
            running = display.displayOptions();
        }

        //Displaying a farewell message to the user once the program terminates
        System.out.println("See you later!");
    }
}


