package ca.umanitoba.comp2450.exercisetracker.model.map;

import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Coordinates;
import com.google.common.base.Preconditions;

/**
 * Immutable rectangular obstacle on the map
 */
public record Obstacle(Coordinates coordinates, int width, int height) {
    //defining the constructor
    public Obstacle {
        Preconditions.checkNotNull(coordinates, "coordinates must not be null.");
        Preconditions.checkState(width >= 1, "width must be greater than or equal to 1.");
        Preconditions.checkState(height >= 1, "height must be greater than or equal to 1.");
    }


    //defining a toString() method for the Obstacle object
    @Override
    public String toString() {
        //obstacle in string form.
        return "Obstacle at (" + coordinates.getX() + "," + coordinates.getY() + ")";
    }

}
