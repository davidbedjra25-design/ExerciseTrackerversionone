package ca.umanitoba.comp2450.exercisetracker.model;

public interface IStack<T> {
    void push(Coordinates coordinates);

    Coordinates pop();

    Coordinates peek();

    int size();

    boolean isEmpty();

}
