package ca.umanitoba.comp2450.exercisetracker.ui;

import ca.umanitoba.comp2450.exercisetracker.gear.GearType;
import ca.umanitoba.comp2450.exercisetracker.model.*;
import ca.umanitoba.comp2450.exercisetracker.model.Map;
import ca.umanitoba.comp2450.exercisetracker.output.MapPrinter;
import ca.umanitoba.comp2450.exercisetracker.output.SwimmerPrinter;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * Handles all console-based user interaction for the exercise tracker: creating swimmers, maps, obstacles, activities, and displaying/removing them.
 */
public class MapDisplay {
    //instance variables
    //input source for all user commands
    private Scanner keyboard;
    //Current map being used
    private Map map;
    //The single swimmer associated with this UI session.
    private Swimmer swimmer;

    //defining the constructor
    public MapDisplay() {
        this.keyboard = new  Scanner(System.in);
        swimmer = createSwimmer();
    }

    //defining a method to create the swimmer object
    public Swimmer createSwimmer() {
        Swimmer.SwimmerBuilder builder = new Swimmer.SwimmerBuilder();

        getUserNameInput(builder);
        return builder.build();
    }

    //defining a method to get the username
    private void getUserNameInput(Swimmer.SwimmerBuilder builder) {
        Preconditions.checkNotNull(builder, "Builder should not be null.");
        String username;

        do {
            System.out.println("Please, enter your username: ");
            username = keyboard.nextLine();
            try {
                builder.username(username);
            }
            catch(Exception e) {
                System.out.println("Usernames must have at least one letter. E.g. Jack");
                username = null;
            }
        } while(username == null);
    }

    //defining a method to get the width of the map from the user
    private void getMapWidth(Map.MapBuilder builder) {
        Preconditions.checkNotNull(builder, "builder should not be null.");
        int width = -1;

        do {
            System.out.println("Enter the width of the map:");
            width = keyboard.nextInt();

            try {
                builder.width(width);
            }
            catch(Exception e) {
                System.out.println("The width must be a positive whole number.");
                width = -1;
            }
        } while(width == -1);
    }

    //defining a method to get the height of the map from the user
    private void getMapHeight(Map.MapBuilder builder) {
        Preconditions.checkNotNull(builder, "Builder should not be null.");
        int height = -1;

        do {
            System.out.println("Enter the height of the map:");
            height = keyboard.nextInt();
            try {
                builder.height(height);
            }
            catch(Exception e) {
                System.out.println("The height must be a positive whole number.");
                height = -1;
            }
        } while(height == -1);
    }

    /**
     * Shows the main menu and executes the action corresponding to the selected number.
     * @return true to continue, false otherwise
     */
    public boolean displayOptions() {
        System.out.println("What would you like to do?");
        String[] options = {"Add gear", "Add map", "Add obstacle", "Add activity", "Show map", "Show gear", "Show obstacles", "Show activities", "Show activitiy", "Remove gear", "Remove activity", "Remove obstacle", "Remove map", "Exit"};
        String s = "";
        for(int i = 0; i < options.length; i++) {
            if(i == options.length - 1) {
                s += (i+1) + ". " + options[i];
            }
            else {
                s += (i+1) + ". " + options[i] + "\n";
            }
        }
        System.out.println(s);

        System.out.println("Please select an option by selecting the number next to your desired option.");
        int option = keyboard.nextInt();
        keyboard.nextLine();
        switch(option) {
            case 1 -> addGear();
            case 2 -> addMap();
            case 3 -> addObstacle();
            case 4 -> addActivity();
            case 5 -> showMap();
            case 6 -> showGear();
            case 7 -> showObstacles();
            case 8 -> showActivities();
            case 9 -> showActivity();
            case 10 -> removeGear();
            case 11 -> removeActivity();
            case 12 -> removeObstacle();
            case 13 -> removeMap();
            case 14 -> exit();
            default -> System.out.println("Invalid option.");
        }
        return true;
    }

    /**
     * adds a map
     */
    private void addMap() {
        System.out.print("Please enter the width of the map:");
        int width = keyboard.nextInt();
        System.out.print("Please enter the height of the map:");
        int height = keyboard.nextInt();

        map = new Map.MapBuilder().width(width).height(height).build();
        System.out.println("You have created a map of size " + width + "x" + height + ".");
        swimmer.addMap(map);
    }

    /**
     * displays an ASCII grid to the user containing the map info
     */
    private void showMap() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }

        int width = map.getWidth();
        int height = map.getHeight();

        map.createGrid();
        char[][] grid = map.getGrid();
        if(!swimmer.getActivities().isEmpty()) {
            for(Activity activity : swimmer.getActivities()) {
                displayActivityOnMap(activity);
            }
        }
        MapPrinter mapPrinter = new MapPrinter(map);
        mapPrinter.printGrid();
        mapPrinter.printLegend();
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(swimmer);
        swimmerPrinter.showTotalDistance();
    }

    /**
     * adds Gear
     */
    private void addGear() {
        System.out.println("Select gear to add:\n1. Oxygen Tank\n2. Snorkel");
        int choice = keyboard.nextInt();
        switch(choice) {
            case 1 :
                if(swimmer.getGears().contains(GearType.OXYGEN_TANK)) {
                    System.out.println("You have already added an Oxygen Tank.");
                    return;
                }
                swimmer.addGear(GearType.OXYGEN_TANK);
                System.out.println("Oxygen Tank has been added.");
                break;
            case 2 :
                if(swimmer.getGears().contains(GearType.SNORKEL)) {
                    System.out.println("You have already added an Snorkel.");
                    return;
                }
                swimmer.addGear(GearType.SNORKEL);
                System.out.println("Snorkel has been added.");
                break;
            default :
                System.out.println("Invalid input. Please enter 1 or 2.");
        }
    }

    /**
     * shows the gear added to the user
     */
    private void showGear() {
        SwimmerPrinter printer = new SwimmerPrinter(swimmer);
        printer.showGears();
    }

    /**
     * adds an obstacle to the map.
     */
    private void addObstacle() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }
        System.out.println("Please, enter the x-coordinate of the obstacle:");
        int x = keyboard.nextInt();
        System.out.println("Please, enter the y-coordinate of the obstacle:");
        int y = keyboard.nextInt();
        System.out.println("Please, enter the width of the obstacle:");
        int width = keyboard.nextInt();
        System.out.println("Please, enter the height of the obstacle:");
        int height = keyboard.nextInt();
        keyboard.nextLine();

        if(x < 0 || y < 0 || (x + width) > map.getWidth() || (y + height) > map.getHeight()) {
            System.out.println("The obstacle cannot fit inside the map.");
            return;
        }
        Coordinates obstacleCoordinates = new Coordinates(x,y);
        Obstacle obstacle = new Obstacle(obstacleCoordinates, width, height);
        map.addObstacle(obstacle);
        System.out.println("The obstacle has been added.");
    }

    /**
     * adds an activity
     */
    private void addActivity() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }

        Activity tempActivity = createActivity();
        Route route = tempActivity.getRoute();
        PathFinder pathFinder = new PathFinder(route,map);
        if(map.getGrid() != null) {
            pathFinder.createPath(map.getGrid());
        }
        else {
            map.createGrid();
            pathFinder.createPath(map.getGrid());
        }
        Activity finalActivity = new Activity.ActivityBuilder().activityName(tempActivity.getActivityName()).route(route).build();
        swimmer.addActivity(finalActivity);
        System.out.println("You have added a new activity.");
    }

    private Activity createActivity() {
        Activity.ActivityBuilder builder = new Activity.ActivityBuilder();

        getActivityNameInput(builder);
        getRouteInput(builder);

        return builder.build();
    }

    /**
     * gets the name of the activity from the user.
     * @param builder The builder of the acrtivity class
     */
    public void getActivityNameInput(Activity.ActivityBuilder builder) {
        Preconditions.checkNotNull(builder, "builder should not be null.");
        String activityName;

        do {
            System.out.println("Please, enter the name of the activity you want to add: ");
            activityName = keyboard.nextLine();
            try {
                builder.activityName(activityName);
            }
            catch(Exception e) {
                System.out.println("The names of activities must have at least one letter. E.g. Jack");
                activityName = null;
            }
        } while(activityName == null);

        Preconditions.checkNotNull(activityName, "The activity name should not be null.");
    }

    /**
     * Gets the starting and ending positions from the user to create the route
     * @param builder the builder of the activity class
     */
    public void getRouteInput(Activity.ActivityBuilder builder) {
        Preconditions.checkNotNull(builder, "builder should not be null.");
        Route route = null;
        int startPositionX = -1;
        int startPositionY = -1;
        int endPositionX = -1;
        int endPositionY = -1;

        do{
            System.out.println("Please, enter the x-coordinate of the start position of the route:");
            startPositionX = keyboard.nextInt();
            System.out.println("Please, enter the y-coordinate of the start position of the route:");
            startPositionY = keyboard.nextInt();

            System.out.println("Please, enter the x-coordinate of the end position of the route:");
            endPositionX = keyboard.nextInt();
            System.out.println("Please, enter the y-coordinate of the end position of the route:");
            endPositionY = keyboard.nextInt();

            if(isInObstacle(startPositionX, startPositionY)) {
                System.out.println("Start position is inside an obstacle. Choose another location.");
                continue;
            }

            if(isInObstacle(endPositionX, endPositionY)) {
                System.out.println("End position is inside an obstacle. Choose another location.");
                continue;
            }
            Coordinates startPosition = new Coordinates(startPositionX, startPositionY);
            Coordinates endPosition = new Coordinates(endPositionX, endPositionY);
            route = new Route(startPosition, endPosition);
            builder.route(route);
        } while(startPositionX < 0 ||  startPositionY < 0 || endPositionX < 0 || endPositionY < 0 || startPositionX >= map.getWidth() ||  startPositionY >= map.getHeight() || endPositionX >= map.getWidth() || endPositionY >= map.getHeight());

        Preconditions.checkNotNull(route, "Route should not be null.");
    }

    /**
     * checks if the starting or ending position of the route is in an obstacle
     * @param x x-coordinate of Position
     * @param y y-coordinate of Position
     * @return true if the position is in the obstacle, false otherwise
     */
    private boolean isInObstacle(int x , int y) {
        for(Obstacle obstacle : map.getObstacles()) {
            int obstacleX = obstacle.coordinates().getX();
            int obstacleY = obstacle.coordinates().getY();

            int obstacleWidth = obstacle.width();
            int obstacleHeight = obstacle.height();

            if(x >= obstacleX && x < obstacleX + obstacleWidth && y >= obstacleY && y < obstacleY + obstacleHeight) {
                return true;
            }
        }
        return false;
    }

    /**
     * shows the obstacles and their properties to the user
     */
    private void showObstacles() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }
        MapPrinter mapPrinter = new MapPrinter(map);
        mapPrinter.showObstacles();
    }

    /**
     * Displays a list of activities and the distance travelled for each one
     */
    private void showActivities() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(swimmer);
        swimmerPrinter.showActivities();
    }

    /**
     * Displays the route of the activity on the map
     */
    private void showActivity() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }
        if(swimmer.getActivities().isEmpty()) {
            System.out.println("No activities have been added yet.");
            return;
        }

        System.out.println("Select an activity to view:");
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(swimmer);
        swimmerPrinter.showActivities();
        System.out.print("Select the activity by entering the number beside it:");
        int input = keyboard.nextInt();
        keyboard.nextLine();
        ArrayList<Activity> activities = swimmer.getActivities();
        if(input < 1 || input > activities.size()) {
            System.out.println("Invalid input.");
            return;
        }
        Activity activity = activities.get(input - 1);
        displayActivityOnMap(activity);
        MapPrinter printer = new MapPrinter(map);
        printer.printGrid();
    }

    /**
     * Helper method for the showActivity() method and the showMap() method
     * @param activity The activity being shown
     */
    private void displayActivityOnMap(Activity activity) {
        ArrayList<Coordinates> path = activity.getRoute().getPath();
        char[][] grid = map.getGrid();
        for(Coordinates coordinates : path) {
            grid[coordinates.getY()][coordinates.getX()] = '>';
        }
    }

    /**
     * removes the gear the user wants to remove
     */
    private void removeGear() {
        if(swimmer.getGears().isEmpty()) {
            System.out.println("You cannot add any gears as no gears have been added yet.");
            return;
        }
        System.out.println("Select the gear to remove:");
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(swimmer);
        swimmerPrinter.showGears();
        System.out.print("Select the gear by entering the number beside it:");
        int input = keyboard.nextInt();
        if(input < 1 || input > swimmer.getGears().size()) {
            System.out.println("Invalid input.");
            return;
        }

        ArrayList<GearType> gearList = new ArrayList<>(swimmer.getGears());
        swimmer.removeGear(gearList.get(input - 1));
        System.out.println("The gear has been removed.");
    }

    /**
     * Removes the obstacle the user wants to remove
     */
    private void removeObstacle() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }
        if(map.getObstacles().isEmpty()) {
            System.out.println("You cannot remove any obstacles as there are no obstacles to remove.");
            return;
        }
        System.out.println("Select the obstacle to remove:");
        MapPrinter mapPrinter = new MapPrinter(map);
        mapPrinter.showObstacles();
        System.out.print("Select the obstacle by entering the number beside it:");
        int input = keyboard.nextInt();
        if(input < 1 || input > map.getObstacles().size()) {
            System.out.println("Invalid input.");
            return;
        }

        map.getObstacles().remove(input - 1);
        System.out.println("The obstacle has been removed.");
    }

    /**
     * removes the activity the user wants to remove
     */
    private void removeActivity() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }

        if(swimmer.getActivities().isEmpty()) {
            System.out.println("No activities have been added yet.");
            return;
        }

        System.out.println("Select an activity to remove:");
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(swimmer);
        swimmerPrinter.showActivities();
        System.out.print("Select the activity by entering the number beside it:");
        int input = keyboard.nextInt();
        if(input < 1 || input > swimmer.getActivities().size()) {
            System.out.println("Invalid input.");
            return;
        }
        swimmer.removeActivity(swimmer.getActivities().get(input - 1));
    }

    /**
     * removes the map
     */
    private void removeMap() {
        if(map == null) {
            System.out.println("You have not yet added a map.");
            return;
        }
        swimmer.removeMap();
        map = null;

    }

    /**
     * Ends the program
     * @return false to end the program
     */
    private boolean exit() {
        close();
        System.out.println("Program ended.");
        return false;
    }

    /**
     * closes the Scanner object
     */
    public void close() {
        keyboard.close();
    }

}
