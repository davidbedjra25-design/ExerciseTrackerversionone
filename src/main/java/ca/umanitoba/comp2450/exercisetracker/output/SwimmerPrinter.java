package ca.umanitoba.comp2450.exercisetracker.output;

import ca.umanitoba.comp2450.exercisetracker.gear.GearType;
import ca.umanitoba.comp2450.exercisetracker.model.Activity;
import ca.umanitoba.comp2450.exercisetracker.model.Swimmer;
import java.util.*;
/**
 * Responsible for displaying certain aspects of the swimmer class to the console.
 */
public class SwimmerPrinter {
    //declaring our instance variable
    private Swimmer swimmer;

    //defining our constructor
    public SwimmerPrinter(Swimmer swimmer) {
        //initializing our instance variable
        this.swimmer = swimmer;
    }

    //defining a method to show all the activities
    public void showActivities() {
        System.out.println("All activities:");
        for(int i = 0; i <= swimmer.getActivities().size() - 1; i++){
            System.out.println((i + 1) + ". " + swimmer.getActivities().get(i).getActivityName() + " (Distance: " + swimmer.getActivities().get(i).getDistance() + ")");
        }
    }

    //declaring a method to show the gears the user has added
    public void showGears() {
        if(swimmer.getGears().isEmpty()) {
            System.out.println("You have not yet added any gears.");
            return;
        }
        System.out.println("All gears:");
        int i = 1;
        for(GearType gear : swimmer.getGears()) {
            switch(gear) {
                case SNORKEL -> System.out.println(i + ". Snorkel");
                case OXYGEN_TANK -> System.out.println(i + ". Oxygen Tank.");
            }
            i++;
        }
    }

    //defining a method to display the total distance the user has travelled
    public void showTotalDistance() {
        System.out.println("The total distance travelled is " + swimmer.getDistance());
    }

    public void showFeed() {
        ArrayList<Activity> feed = swimmer.getFeed();
        if(feed.isEmpty()) {
            System.out.println("Your feed is empty.");
        }
        else {
            System.out.println("Activity feed: ");
            int i = 0;
            for(Activity activity : feed) {
                System.out.println((i + 1) + activity.getActivityName() + " (" + activity.getDistance());
            }
        }
    }
}
