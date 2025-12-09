package ca.umanitoba.comp2450.exercisetracker.model;

import ca.umanitoba.comp2450.exercisetracker.model.map.Map;
import ca.umanitoba.comp2450.exercisetracker.model.map.WorldMap;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Activity;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Swimmer;

import java.util.ArrayList;

public class TrackerManager {
    private final UserManager userManager;
    private final WorldMap worldMap;
    private Swimmer currentSwimmer;

    public TrackerManager() {
        this.userManager = new UserManager();
        this.worldMap = new WorldMap();
    }

    public Swimmer loginUser(String username) {
        Swimmer swimmer = userManager.login(username);
        if(swimmer != null){
            this.currentSwimmer = swimmer;
        }
        return swimmer;
    }

    public Swimmer registerUser(String username) throws Exception {
        Swimmer swimmer = userManager.register(username, worldMap.getWorldMap());
        this.currentSwimmer = swimmer;
        return swimmer;
    }

    public Map getWorldMap() {
        return worldMap.getWorldMap();
    }

    public Swimmer getCurrentUser() {
        return currentSwimmer;
    }

    public ArrayList<Swimmer> getAllUsers() {
        return userManager.getAllUsers();
    }

    public ArrayList<Activity> getFeedFor(Swimmer swimmer) {
        ArrayList<Activity> feed = new ArrayList<>();

        feed.addAll(swimmer.getActivities());

        for(Swimmer followedSwimmer : swimmer.getFollowing()) {
            feed.addAll(followedSwimmer.getActivities());
        }
        return feed;
    }
}
