package ca.umanitoba.comp2450.exercisetracker.model.swimmer;

import ca.umanitoba.comp2450.exercisetracker.gear.GearType;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.exceptions.InvalidSwimmerUsernameException;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * Represents a swimmer with a username, gear, total distance, a map and a list of activities
 */
public class Swimmer {
    //instance variables
    private String username;
    private Set<GearType> gears;
    private int distance;//total distance
    private ArrayList<Activity> activities;
    private Set<Swimmer> following;

    //Defining our constructor
    private Swimmer(String username)  {
        this.username = username;
        this.gears = new HashSet<>();
        this.activities = new ArrayList<>();
        this.distance = 0;
        this.following = new HashSet<>();
        //Postcondition for constructor: This instance is in a valid state.
        checkSwimmer();
    }

    //defining a method to get the username
    public String getUsername() {
        //One precondition: This instance of swimmer should be in a valid state before returning the username.
        checkSwimmer();
        return username;
    }

    //defining a method to add gear
    public void addGear(GearType gear) {
        Preconditions.checkNotNull(gear, "Gear should not be null.");
        //Precondition: this instance of activities must be valid before adding gearType.
        checkSwimmer();
        gears.add(gear);
        //Postcondition: this instance of swimmer must be valid just before returning from the mutator
        checkSwimmer();
    }

    //defining a method to remove gear
    public void removeGear(GearType gear) {
        //Precondition: this instance of activities must be valid before removing gearType.
        checkSwimmer();
        gears.remove(gear);
        //Postcondition: this instance of swimmer must be valid just before returning from the mutator
        checkSwimmer();
    }

    public void addActivity(Activity activity) {
        Preconditions.checkNotNull(activity, "Activity should not be null.");
        //Precondition: this instance of activity must be valid before adding the activity
        checkSwimmer();
        activities.add(activity);
        this.distance += activity.getDistance();
        checkSwimmer();
        //Postcondition: this instance of swimmer must be valid just before returning the mutator
    }

    //defining a getter method for distance
    public int getDistance() {
        //One precondition: This instance of swimmer should be in a valid state before returning the distance.
        checkSwimmer();
        return distance;
    }

    //defining a getter method for the list of activities
    public ArrayList<Activity> getActivities() {
        //One precondition: This instance of swimmer should be in a valid state before returning the list of activities.
        checkSwimmer();
        return activities;
    }

    //defining a method to remove an activity
    public void removeActivity(Activity activity) {
        //Two preconditions: This instance of swimmer should be in a valid state before removing the activity and the activity to be removed should not be null.
        checkSwimmer();
        Preconditions.checkNotNull(activity, "Activity should not be null.");
        if(!activities.contains(activity)) {
            System.out.println("There is no such activity in this list.");
            return;
        }
        distance -= activity.getDistance();
        activities.remove(activity);
        //One postcondition: this instance of swimmer must be in a valid state just after removing the activity.
        checkSwimmer();
    }

    //defining a getter method for the set of gears
    public Set<GearType> getGears() {
        //One precondition: This instance of swimmer should be in a valid state before returning the set of gears.
        checkSwimmer();
        return gears;
    }

    public void follow(Swimmer other) {
        Preconditions.checkNotNull(other, "The other swimmer should not be null.");
        if(!other.equals(this)) {
            following.add(other);
        }
    }

    public Set<Swimmer> getFollowing() {
        return following;
    }

    //method for precondition and postcondition to check for a valid state of swimmer
    private void checkSwimmer() {
        Preconditions.checkNotNull(username, "Username should never be null.");
        Preconditions.checkState(!username.isEmpty(), "Username should have at least one symbol.");
        Preconditions.checkState(distance >= 0, "Distance should never be negative.");
        Preconditions.checkNotNull(following, "following should not be null.");
        Preconditions.checkNotNull(activities, "Activities should never be null.");
        for(Activity activity : activities) {
            Preconditions.checkNotNull(activity, "Individual activities should never be null.");
        }
    }

    //builder class for swimmer
    public static class SwimmerBuilder {
        private String username;
        public SwimmerBuilder() {}

        public SwimmerBuilder username(String username) throws InvalidSwimmerUsernameException {
            //One precondition: The username should not be null.
            Preconditions.checkNotNull(username,"Username should not be null.");
            if(username.isEmpty()) {
                throw new InvalidSwimmerUsernameException();
            }
            this.username = username;
            return this;
        }

        public Swimmer build() {
            return new Swimmer(username);
        }
    }
}
