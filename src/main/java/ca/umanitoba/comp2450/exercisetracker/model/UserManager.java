package ca.umanitoba.comp2450.exercisetracker.model;

import ca.umanitoba.comp2450.exercisetracker.model.map.Map;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.Swimmer;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.exceptions.InvalidSwimmerUsernameException;
import ca.umanitoba.comp2450.exercisetracker.model.swimmer.exceptions.UserAlreadyExistsException;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<Swimmer> users;

    public UserManager() {
        users = new ArrayList<>();
    }

    public Swimmer register(String username, Map worldMap) throws InvalidSwimmerUsernameException, UserAlreadyExistsException {
        Preconditions.checkNotNull(username, "Username should not be null.");
        Preconditions.checkNotNull(worldMap, "World map should not be null.");
        if(username.isEmpty()) {
            throw new InvalidSwimmerUsernameException();
        }
        if(findSwimmer(username) != null) {
            throw new UserAlreadyExistsException("This username is already in use.");
        }

        Swimmer swimmer = new Swimmer.SwimmerBuilder().username(username).build();

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
