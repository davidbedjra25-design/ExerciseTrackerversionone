package ca.umanitoba.comp2450.exercisetracker.model.swimmer.exceptions;

public class UserAlreadyExistsException extends Exception {
    private final String alreadyExists;
    public UserAlreadyExistsException(String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }
}
