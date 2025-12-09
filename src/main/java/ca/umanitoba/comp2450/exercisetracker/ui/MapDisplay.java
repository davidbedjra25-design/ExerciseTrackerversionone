package ca.umanitoba.comp2450.exercisetracker.ui;

import ca.umanitoba.comp2450.exercisetracker.gear.GearType;
import ca.umanitoba.comp2450.exercisetracker.model.*;
import ca.umanitoba.comp2450.exercisetracker.model.map.Map;
import ca.umanitoba.comp2450.exercisetracker.model.map.Obstacle;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Activity;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Coordinates;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Route;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Swimmer;
import ca.umanitoba.comp2450.exercisetracker.output.MapPrinter;
import ca.umanitoba.comp2450.exercisetracker.output.SwimmerPrinter;
import com.google.common.base.Preconditions;
import ca.umanitoba.comp2450.exercisetracker.model.TrackerManager;

import java.util.*;

/**
 * Handles all console-based user interaction for the exercise tracker: creating swimmers, maps, obstacles, activities, and displaying/removing them.
 */
public class MapDisplay {
    //instance variables
    //input source for all user commands
    private Scanner keyboard;
    //The current swimmer signed in.
    private Swimmer currentSwimmer;
    private TrackerManager manager;

    //defining the constructor
    public MapDisplay() {
        this.keyboard = new  Scanner(System.in);
        this.manager = new TrackerManager();
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


    public void start() {
        boolean systemRunning = true;
        while (systemRunning) {
            if (currentSwimmer == null) {
                systemRunning = showLoginMenu();
            }
            else {
                boolean signedIn = displayUserOptions();
                if(!signedIn) {
                    currentSwimmer = null;
                }
            }
        }
    }

    private boolean showLoginMenu() {
        System.out.println("1. Login");
        System.out.println("2. Create New Profile");
        System.out.println("3. Exit System");

        int choice = keyboard.nextInt();
        keyboard.nextLine();

        switch(choice) {
            case 1:
                performLogin();
                break;
            case 2:
                performRegistration();
                break;
            case 3:
                exit();
                break;
            default:
                System.out.println("Invalid option.");
        }
        return true;
    }

    private void performLogin() {
        System.out.println("Enter username: ");
        String username = keyboard.nextLine();
        Swimmer swimmer = manager.loginUser(username);
        if(swimmer != null) {
            currentSwimmer = swimmer;
            System.out.println("Welcome " + swimmer.getUsername());
        }
        else {
            System.out.println("User not found.");
        }
    }

    private void performRegistration() {
        System.out.print("Choose a username: ");
        String username = keyboard.nextLine();
        try {
            currentSwimmer = manager.registerUser(username);
            System.out.println("You have successfully created a profile. You are now logged in.");
        }
        catch(Exception e) {
            System.out.println("Error creating profile: " + e.getMessage());
        }
    }

    /**
     * Shows the main menu and executes the action corresponding to the selected number.
     * @return true to continue, false otherwise
     */
    public boolean displayUserOptions() {
        System.out.println("What would you like to do?");
        String[] options = {"Add gear", "Add obstacle", "Add activity Manually", "Find new path", "Add duplicate activity", "Show map", "Show gear", "Show obstacles", "Show activities", "Show activity", "View feed", "Follow", "Remove gear", "Remove activity", "Remove obstacle", "Remove map", "Log out"};
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
            case 2 -> addObstacle();
            case 3 -> addActivityManually();
            case 4 -> addDuplicateActivity();
            case 5 -> findNewPath();
            case 6 -> showMap();
            case 7 -> showGear();
            case 8 -> showObstacles();
            case 9 -> showActivities();
            case 10 -> showActivity();
            case 11 -> viewFeed();
            case 12 -> followUser();
            case 13 -> removeGear();
            case 14 -> removeActivity();
            case 15 -> removeObstacle();
            case 16 -> logOut();
            default -> System.out.println("Invalid option.");
        }
        return true;
    }

    /**
     * displays an ASCII grid to the user containing the map info
     */
    private void showMap() {
        Map map = manager.getWorldMap();
        map.createGrid();

        char[][] grid = map.getGrid();
        if(!currentSwimmer.getActivities().isEmpty()) {
            for(Activity activity : currentSwimmer.getActivities()) {
                displayActivityOnMap(activity);
            }
        }
        MapPrinter mapPrinter = new MapPrinter(map);
        mapPrinter.printGrid();
        mapPrinter.printLegend();
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(currentSwimmer);
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
                if(currentSwimmer.getGears().contains(GearType.OXYGEN_TANK)) {
                    System.out.println("You have already added an Oxygen Tank.");
                    return;
                }
                currentSwimmer.addGear(GearType.OXYGEN_TANK);
                System.out.println("Oxygen Tank has been added.");
                break;
            case 2 :
                if(currentSwimmer.getGears().contains(GearType.SNORKEL)) {
                    System.out.println("You have already added an Snorkel.");
                    return;
                }
                currentSwimmer.addGear(GearType.SNORKEL);
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
        SwimmerPrinter printer = new SwimmerPrinter(currentSwimmer);
        printer.showGears();
    }

    /**
     * adds an obstacle to the map.
     */
    private void addObstacle() {
        Map map = manager.getWorldMap();

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
    private void addActivityManually() {
        Map map = manager.getWorldMap();

        Activity tempActivity = createActivity();
        Route route = tempActivity.getRoute();
        PathFinder pathFinder = new PathFinder(route,map);
        if(map.getGrid() != null) {
            pathFinder.createPath(map.getGrid());
        }
        else {
            pathFinder.createPath(map.getGrid());
        }
        Activity finalActivity = new Activity.ActivityBuilder().activityName(tempActivity.getActivityName()).route(route).build();
        currentSwimmer.addActivity(finalActivity);
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
     * @param builder The builder of the activity class
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
        Map map = manager.getWorldMap();
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
        Map map = manager.getWorldMap();
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
        Map map = manager.getWorldMap();
        MapPrinter mapPrinter = new MapPrinter(map);
        mapPrinter.showObstacles();
    }

    /**
     * Displays a list of activities and the distance travelled for each one
     */
    private void showActivities() {
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(currentSwimmer);
        swimmerPrinter.showActivities();
    }

    /**
     * Displays the route of the activity on the map
     */
    private void showActivity() {
        Map map = manager.getWorldMap();
        if(currentSwimmer.getActivities().isEmpty()) {
            System.out.println("No activities have been added yet.");
            return;
        }

        System.out.println("Select an activity to view:");
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(currentSwimmer);
        swimmerPrinter.showActivities();
        System.out.print("Select the activity by entering the number beside it:");
        int input = keyboard.nextInt();
        keyboard.nextLine();
        ArrayList<Activity> activities = currentSwimmer.getActivities();
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
        Map map = manager.getWorldMap();
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
        if(currentSwimmer.getGears().isEmpty()) {
            System.out.println("You cannot add any gears as no gears have been added yet.");
            return;
        }
        System.out.println("Select the gear to remove:");
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(currentSwimmer);
        swimmerPrinter.showGears();
        System.out.print("Select the gear by entering the number beside it:");
        int input = keyboard.nextInt();
        if(input < 1 || input > currentSwimmer.getGears().size()) {
            System.out.println("Invalid input.");
            return;
        }

        ArrayList<GearType> gearList = new ArrayList<>(currentSwimmer.getGears());
        currentSwimmer.removeGear(gearList.get(input - 1));
        System.out.println("The gear has been removed.");
    }

    /**
     * Removes the obstacle the user wants to remove
     */
    private void removeObstacle() {
        Map  map = manager.getWorldMap();
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
        if(currentSwimmer.getActivities().isEmpty()) {
            System.out.println("No activities have been added yet.");
            return;
        }

        System.out.println("Select an activity to remove:");
        SwimmerPrinter swimmerPrinter = new SwimmerPrinter(currentSwimmer);
        swimmerPrinter.showActivities();
        System.out.print("Select the activity by entering the number beside it:");
        int input = keyboard.nextInt();
        if(input < 1 || input > currentSwimmer.getActivities().size()) {
            System.out.println("Invalid input.");
            return;
        }
        currentSwimmer.removeActivity(currentSwimmer.getActivities().get(input - 1));
    }

    private boolean logOut() {
        System.out.println("Bye " + currentSwimmer.getUsername());
        return false;
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

    private void viewFeed() {
        SwimmerPrinter printer = new SwimmerPrinter(currentSwimmer);
        ArrayList<Activity> feed = manager.getFeedFor(currentSwimmer);
        printer.showFeed(feed);
    }

    private void followUser() {
        System.out.println("Users in the system:");
        int i = 0;
        for(Swimmer swimmer : manager.getAllUsers()) {
            if(!swimmer.getUsername().equals(currentSwimmer.getUsername())) {
                System.out.println((i + 1) + swimmer.getUsername());
            }
        }
        System.out.println("Enter the name of the user to follow: ");
        String name = keyboard.nextLine();
        Swimmer target = manager.loginUser(name);
        if(target != null && !target.equals(currentSwimmer)) {
            currentSwimmer.follow(target);
            System.out.println("You have started following " + target.getUsername());
        }
        else {
            System.out.println("Invalid user.");
        }
    }

    private void findNewPath() {
        System.out.println("Please choose what you want to use to find the path:");
        System.out.println("1. My previous route only");
        System.out.println("2. All routes in my feed");
        int input = keyboard.nextInt();
        keyboard.nextLine();

        ArrayList<Coordinates> allowedCoordinates = new ArrayList<>();
        ArrayList<Activity> source;
        if (input == 1) {
            source = currentSwimmer.getActivities();
        }
        else if(input == 2){
            source = manager.getFeedFor(currentSwimmer);
        }
        else {
            System.out.println("Invalid input.");
            return;
        }

        for(Activity activity : source) {
            allowedCoordinates.addAll(activity.getRoute().getPath());
        }

        if(allowedCoordinates.isEmpty()) {
            System.out.println("No route has been added yet.");
            return;
        }

        System.out.println("Enter the x-coordinate of the starting position: ");
        int startX = keyboard.nextInt();
        keyboard.nextLine();
        System.out.println("Enter the y-coordinate of the starting position: ");
        int startY = keyboard.nextInt();
        keyboard.nextLine();
        System.out.println("Enter the x-coordinate of the ending position: ");
        int endX = keyboard.nextInt();
        keyboard.nextLine();
        System.out.println("Enter the y-coordinate of the ending position: ");
        int endY = keyboard.nextInt();
        keyboard.nextLine();

        Coordinates startPosition = new Coordinates(startX, startY);
        Coordinates endPosition = new Coordinates(endX, endY);
        Route tempRoute = new Route(startPosition, endPosition);

        PathFinder pathFinder = new PathFinder(tempRoute, manager.getWorldMap(), allowedCoordinates);
        pathFinder.createPath(manager.getWorldMap().getGrid());

        if(!tempRoute.getPath().isEmpty()) {
            System.out.println("A path was found. The length of this path is " + tempRoute.getPath().size());
            System.out.println("Would you like to save this route as a new activity?");
            System.out.println("1. Yes\n2. No");
            System.out.println("Please select the option by entering the number beside it.");
            int choice = keyboard.nextInt();
            keyboard.nextLine();

            if(choice == 1) {
                Activity.ActivityBuilder builder = new Activity.ActivityBuilder();

                getActivityNameInput(builder);

                builder.route(tempRoute);

                try {
                    Activity newActivity = builder.build();
                    currentSwimmer.addActivity(newActivity);
                    System.out.println("The activity " + newActivity.getActivityName() + "has been saved.");
                }
                catch(Exception e) {
                    System.out.println("Error saving activity: " + e.getMessage());
                }
            }
        }
        else {
            System.out.println("No path was found using the previous routes.");
        }
    }

    private void addDuplicateActivity() {
        ArrayList<Activity> myActivities = currentSwimmer.getActivities();
        if(myActivities.isEmpty()) {
            System.out.println("No previous activities.");
            return;
        }
        for(int i = 0; i < myActivities.size(); i++) {
            System.out.println((i+1) +  myActivities.get(i).getActivityName());
        }
        System.out.println("Select the route you would like to duplicate by entering the number beside it.");
        int choice = keyboard.nextInt();
        if(choice < 1 && choice >= myActivities.size()) {
            System.out.println("Invalid input.");
            return;
        }
        Activity duplicate = myActivities.get(choice - 1);
        System.out.print("Enter the name of the new activity: ");
        String activityName = keyboard.nextLine();
        Activity newActivity = new Activity.ActivityBuilder().activityName(activityName).route(duplicate.getRoute()).build();
        currentSwimmer.addActivity(newActivity);
        System.out.println("the activity has been duplicated.");
    }

}
