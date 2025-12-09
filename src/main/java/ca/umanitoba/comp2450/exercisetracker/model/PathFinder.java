package ca.umanitoba.comp2450.exercisetracker.model;

import ca.umanitoba.comp2450.exercisetracker.model.map.Map;
import ca.umanitoba.comp2450.exercisetracker.model.map.Obstacle;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Coordinates;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Route;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.exceptions.InvalidCoordinateException;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * Responsible for finding a path from a route's start position to its end position
 * on a given map, avoiding any obstacles in the process.
 */
public class PathFinder {
    //Instance variables
    //The route whose path will be computed and filled in.
    private Route route;
    //The map on which the path is computed.
    private ca.umanitoba.comp2450.exercisetracker.model.map.Map map;
    private List<Coordinates> allowedCoordinates;

    //defining the constructor
    public PathFinder(Route route, ca.umanitoba.comp2450.exercisetracker.model.map.Map map, List<Coordinates> allowedCoordinates) {
        //Two preconditions: The route and map being entered should not be null.
        Preconditions.checkNotNull(route, "Route should not be null.");
        Preconditions.checkNotNull(map, "Map should not be null.");
        this.route = route;
        this.map = map;
        this.allowedCoordinates = allowedCoordinates;
    }

    public PathFinder(Route route, Map map) {
        Preconditions.checkNotNull(route, "Route should not be null.");
        Preconditions.checkNotNull(map, "Map should not be null.");
        this.route = route;
        this.map = map;
    }

    //defining a method to find the path from a route's start position to its end position on a map grid, avoiding obstacles in the process
    public void createPath(char[][] grid) throws InvalidCoordinateException {
        //creating variables to keep track of the map's width and height
        int width = map.getWidth();
        int height = map.getHeight();
        //creating variables to keep track of the route's starting position and ending position
        Coordinates startPosition = route.getStartPosition();
        Coordinates endPosition = route.getEndPosition();

        //created a 2-D boolean array to keep track of whether a position has already been discovered.
        boolean[][] visited = new boolean[height][width];
        //creating a 2-D coordinates array to store which position has been reached in order to reconstruct the path after.
        Coordinates[][] parent = new Coordinates[height][width];

        Stack<Coordinates> validPositions = new LinkedListStack<>();
        validPositions.push(startPosition);

        //creating 2 arrays to store the relative steps to move in 4 directions:right, left, down, up.
        int[] stepX = {1,-1,0,0};
        int[] stepY = {0,0,1,-1};

        while(!validPositions.isEmpty()) {
            Coordinates currentPos = validPositions.pop();

            visited[currentPos.getY()][currentPos.getX()] = true;

            //reconstucting the path and stopping if the end was reached
            if(currentPos.equals(endPosition)) {
                buildPath(parent, endPosition);
                return;
            }

            //checking each of the 4 neighbours
            for(int i = 0; i < 4; i++) {
                int newX = currentPos.getX() + stepX[i];
                int newY = currentPos.getY() + stepY[i];

                //checking if the position is valid
                if(isValid(newX, newY, visited)) {
                    //marking the position being added as visited and recording how the cell was reached
                    if(parent[newY][newX] == null) {
                        Coordinates neighbour = new Coordinates(newX, newY);
                        parent[newY][newX] = currentPos;
                        validPositions.push(neighbour);
                    }
                }
            }

        }

    }

    /**
     * Checks if a position is inside the map, not yet visited, and not in any obstacle.
     * @param x x-cooordinate of position
     * @param y y-coordinate of position
     * @param visited array containing explored positions
     * @return true if the position is inside the map, not yet visited, and not in any obstacle, false otherwise
     */
    private boolean isValid(int x, int y, boolean[][] visited) throws InvalidCoordinateException {
        //checking if the position is out-of-bounds
        if(x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return false;
        }

        //checking if the position has already been visited
        if(visited[y][x]) {
            return false;
        }

        //checking if the position is in any obstacle
        for(Obstacle obstacle : map.getObstacles()){
            int obstacleX = obstacle.coordinates().getX();
            int obstacleY = obstacle.coordinates().getY();
            int obstacleWidth = obstacle.width();
            int obstacleHeight = obstacle.height();

            if(x >= obstacleX && x < obstacleX + obstacleWidth && y >= obstacleY && y < obstacleY + obstacleHeight) {
                return false;
            }
        }

        if (allowedCoordinates != null) {
            Coordinates coordinate = new Coordinates(x,y);
            if(!allowedCoordinates.contains(coordinate)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reconstructs a path from the end position back to the start using the parent matrix, then writes it into the route in start-to-end order.
     */
    private void buildPath(Coordinates[][] parent, Coordinates endPosition) {
        ArrayList<Coordinates> tempPath = new ArrayList<>();
        Coordinates current = endPosition;
        while(current != null) {
            tempPath.add(current);
            Coordinates p = parent[current.getY()][current.getX()];
            current = p;
        }

        route.getPath().clear();
        //Reverse the path so it goes from start to end.
        for(int i = tempPath.size() - 1; i >= 0; i--) {
            route.addCoordinates(tempPath.get(i));
        }

    }

}
