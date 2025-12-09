package ca.umanitoba.comp2450.exercisetracker.output;

import ca.umanitoba.comp2450.exercisetracker.model.map.Map;
import com.google.common.base.Preconditions;

/**
 * Responsible for printing certain aspects of the map to the user
 */
public class MapPrinter {
    //declaring the instance variable
    private Map map;

    //defining the constructor
    public MapPrinter(Map map) {
        //initializing the instance variable
        this.map = map;
    }

    //defining a method to show the map
    public void showMap() {
        //implementation: Draw grid, obstacles, and route
    }

    //defining a method to print the distance
    public void printDistance(int distance) {
        System.out.println("Distance travelled: " + distance);
    }

    //defining a method to print the legend
    public void printLegend() {
        System.out.println("Legend:\nEmpty space - .\nActivity's Route - >\nObstacle - *");
    }

    //displays the dimensions of the map to the user
    public void printMapDimensions() {
        Preconditions.checkNotNull(map, "Map should not be null.");
        System.out.println("Map dimensions: " + map.getWidth() + "x" + map.getHeight());
    }

    //displays the map to the user in an ASCII grid format
    public void printGrid() {
        int height = map.getHeight();
        int width = map.getWidth();
        char[][] grid = map.getGrid();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    //displays the obstacles and their locations to the user
    public void showObstacles() {
        for(int i = 0; i < this.map.getObstacles().size(); i++) {
            System.out.println((i + 1) + "." + map.getObstacles().get(i));
        }
    }
}
