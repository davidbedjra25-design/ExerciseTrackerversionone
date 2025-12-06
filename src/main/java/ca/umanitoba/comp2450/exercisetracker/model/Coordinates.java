package ca.umanitoba.comp2450.exercisetracker.model;

import com.google.common.base.Preconditions;

/**
 * Immutable pair of non-negative coordinates(x,y) on the map.
 */
public class Coordinates {
    private final int x;//x coordinate
    private final int y;//y coordinate

    //defining our constructor
    public Coordinates(int x, int y){
        //initializing our instance variables
        this.x = x;
        this.y = y;
        //Postcondition for constructor: This instance is in a valid state
        checkCoordinates();
    }

    //defining a getter method for the x coordinate
    public int getX() {
        //One precondition: This instance of location should be in a valid state before returning the x coordinate.
        checkCoordinates();
        return x;
    }

    //defining a getter method for the y coordinate
    public int getY() {
        //One precondition: This instance of location should be in a valid state before returning the y coordinate.
        checkCoordinates();
        return y;
    }

    @Override
    //defining a method to check if this instance of Coordinates is equal to another instance of coordinates
    public boolean equals(Object object){
        //One precondition: This instance of coordinates must be in a valid state before checking
        checkCoordinates();
        if(this == object){
            return true;
        }
        if(object == null || getClass() != object.getClass()){
            return false;
        }
        Coordinates other = (Coordinates) object;
        return x == other.x && y == other.y;
    }

    //method for precondition and postcondition to check for a valid state of coordinates
    public void checkCoordinates() {
        Preconditions.checkState(x >= 0, "x should never be negative");
        Preconditions.checkState(y >= 0, "y should never be negative");
    }

}
