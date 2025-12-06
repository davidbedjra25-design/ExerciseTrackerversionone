package ca.umanitoba.comp2450.exercisetracker.model;

import com.google.common.base.Preconditions;

import java.util.*;

/**
 * Represents a 2-D map with obstacles
 */
public class Map {
    //declaring our instance variables
    private final int width;
    private final int height;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private char[][] grid;

    //defining the constructor
    private Map(int width, int height) {
        //initializing some of our instance variables
        this.width = width;
        this.height = height;
        //Postcondition for constructor: This instance is in a valid state
        checkMap();
    }

    //defining a getter method for the list of obstacles
    public ArrayList<Obstacle> getObstacles() {
        //One precondition: This instance of map should be in a valid state before returning the obstacles.
        checkMap();
        return obstacles;
    }

    //defining a getter method for the width of the map
    public int getWidth() {
        //One precondition: this instance of map must be in a valid state before returning the width of the map
        checkMap();
        return width;
    }

    //defining a getter method for the height of the map
    public int getHeight() {
        //One precondition: this instance of map must be in a valid state before returning the height of the map
        checkMap();
        return height;
    }


    //defining a method which takes an Obstacle object as input to add an obstacle to the map
    public void addObstacle(Obstacle obstacle){
        //2 Preconditions: These instances of obstacle and map must be valid before adding the obstacle
        checkMap();
        Preconditions.checkNotNull(obstacle);
        int x = obstacle.coordinates().getX();
        int y = obstacle.coordinates().getY();
        Preconditions.checkState(x >= 0 && x + obstacle.width() <= width && y >= 0 && y + obstacle.height() <= height, "Obstacle should not be out of bounds.");
        obstacles.add(obstacle);
        //One postcondition: This instance of map must be valid just before returning from the mutator.
        checkMap();
    }

    //defining a method to create the grid
    public void createGrid() {
        //One preconditon: This instance of map should be in a valid state before creating the grid
        checkMap();
        grid = new char[height][width];

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = '.';
            }
        }

        for(Obstacle obstacle : obstacles) {
            int obstacleX = obstacle.coordinates().getX();
            int obstacleY = obstacle.coordinates().getY();

            for(int i = 0; i < obstacle.height(); i++) {
                for(int j = 0; j < obstacle.width() ; j++) {
                    grid[obstacleY + i][obstacleX + j] = '*';
                }
            }
        }
        //One postcondition: this instance of map should still be in a valid state right after creating the grid
        checkMap();
    }

    //defining a getter method for the grid
    public char[][] getGrid() {
        checkMap();
        return grid;
    }

    //method for precondition and postcondition to check for a valid state of map
    private void checkMap() {
        Preconditions.checkState(width >= 1, "Width must always be greater than or equal to 1.");
        Preconditions.checkState(height >= 1, "Height must always be greater than or equal to 1.");
        Preconditions.checkNotNull(obstacles, "Obstacles should never be null.");
        for(Obstacle obstacle : obstacles) {
            Preconditions.checkNotNull(obstacle, "Individual obstacles should never be null");
        }
    }

    //creating a builder class for Map
    public static class MapBuilder {
        private int width;
        private int height;

        public MapBuilder() {
        }

        public MapBuilder width(int width) {
            Preconditions.checkState(width >= 1, "Width must be greater than or equal to 1.");

            this.width = width;

            return this;
        }

        public MapBuilder height(int height) {
            Preconditions.checkState(height >= 1, "Height must be greater than or equal to 1.");

            this.height = height;

            return this;
        }

        public Map build() {
            return new Map(width, height);
        }

    }

}
