package ca.umanitoba.comp2450.exercisetracker.model.swimmer;

import com.google.common.base.Preconditions;
import java.util.*;

/**
 * Represents a route with starting and ending positions, and a sequence of coordinates forming
 * the path betweem them.
 */
public class Route {
    //instance variable
    private final Coordinates startPosition;
    private final Coordinates endPosition;
    private ArrayList<Coordinates> path;

    //defining the constructor
    public Route(Coordinates startPosition, Coordinates endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        path = new ArrayList<>();

        //Postcondition for constructor: this instance is in a valid state
        checkRoute();
    }

    //defining a getter method for the starting position
    public Coordinates getStartPosition() {
        //One precondition: This instance of route should be in a valid state before returning the starting position.
        checkRoute();
        return startPosition;
    }

    //defining a getter method for the ending position
    public Coordinates getEndPosition() {
        //One precondition: This instance of route should be in a valid state before returning the ending position.
        checkRoute();
        return endPosition;
    }

    //defining a method for the path
    public ArrayList<Coordinates> getPath() {
        //One precondition: this instance of route should be in a valid state before returning the path.
        checkRoute();
        return path;
    }

    //defining a method to add coordinates to the path
    public void addCoordinates(Coordinates coordinates) {
        //Two preconditions: This instance of route should be in a valid state and the coordinates being entered should not be null
        //before adding the coordinates.
        Preconditions.checkNotNull(coordinates, "Coordinates should not be null.");
        checkRoute();
        path.add(coordinates);
    }

    //method for precondition and postcondition to check for a valid state of route
    private void checkRoute() {
        Preconditions.checkNotNull(startPosition, "startPosition should not be null.");
        Preconditions.checkNotNull(endPosition, "endPosition should not be null.");
    }

}
