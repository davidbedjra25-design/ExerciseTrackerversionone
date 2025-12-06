package ca.umanitoba.comp2450.exercisetracker.model;

import com.google.common.base.Preconditions;

/**
 * Represents a single swimming activity with a name, a route and a calculated distance(length of the route's path)
 */
public class Activity {
    //instance variables
    private String activityName;
    private Route route;
    private int distance;

    //defining the constructor
    private Activity(String activityName, Route route) {
        this.activityName = activityName;
        this.route = route;
        distance = route.getPath().size();
        checkActivity();
        //Post condition for constructor: this instance is in a valid state
    }

    //defining a getter method for the name of the activity
    public String getActivityName() {
        //One precondition: this instance of activity should not be null before returning the name of the activity
        checkActivity();
        return activityName;
    }

    //defining a getter method for the route
    public Route getRoute() {
        //One precondition: this instance of activity should not be null before returning the route
        checkActivity();
        return route;
    }

    //defining a getter method for the distance travelled for an activity
    public int getDistance() {
        //One precondition: this instance of activity should not be null before returning the distance travelled
        checkActivity();
        return distance;
    }

    //method for precondition and postcondition to check for a valid state of an activity
    public void checkActivity() {
        Preconditions.checkNotNull(activityName, "Activity should not be null.");
        Preconditions.checkState(!activityName.isEmpty(), "The name of the activity should have at least one character.");
        Preconditions.checkNotNull(route, "Route should not be null.");
        Preconditions.checkState(distance >= 0, "Distance should greater than or equal to 0.");
    }

    //builder class for Activity
    public static class ActivityBuilder {
        private String activityName;
        private Route route;

        public ActivityBuilder() {}
            public ActivityBuilder route(Route route) {
                Preconditions.checkNotNull(route, "The route should not be null.");
                this.route = route;

                return this;
            }

            public  ActivityBuilder  activityName(String activityName) {
                Preconditions.checkNotNull(activityName, "activityName should not be null.");
                this.activityName = activityName;

                return this;
            }


        public Activity build() {
            return new Activity(activityName, route);
        }

    }
}
