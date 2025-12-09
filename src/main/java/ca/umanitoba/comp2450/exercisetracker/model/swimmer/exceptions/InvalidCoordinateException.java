package ca.umanitoba.comp2450.exercisetracker.model.swimmer.exceptions;

public class InvalidCoordinateException extends Exception {
    private final String invalidCoordinate;

    public InvalidCoordinateException(String invalidCoordinate) {
        this.invalidCoordinate = invalidCoordinate;
    }
}
