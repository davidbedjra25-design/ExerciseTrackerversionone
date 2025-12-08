package ca.umanitoba.comp2450.exercisetracker.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<Swimmer> users;

    public UserManager() {
        users = new ArrayList<>();
    }

    public Swimmer register(String username, Map worldMap) throws Exception {
        Preconditions.checkNotNull(username, "Username should not be null.");
        Preconditions.checkNotNull(worldMap, "World map should not be null.");

        if(findSwimmer(username) != null) {
            throw new Exception("This username is already in use.");
        }

        Swimmer swimmer = new Swimmer.SwimmerBuilder().username(username).build();
        swimmer.addMap(worldMap);

        users.add(swimmer);
        return swimmer;
    }

    private Swimmer findSwimmer(String username) {
        for(Swimmer swimmer : users) {
            if(swimmer.getUsername().equals(username)) {
                return swimmer;
            }
        }
        return null;
    }

    public Swimmer login(String username) {
        return findSwimmer(username);
    }

    public ArrayList<Swimmer> getAllUsers() {
        return users;
    }

}
