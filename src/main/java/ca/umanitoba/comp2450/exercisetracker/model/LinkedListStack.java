package ca.umanitoba.comp2450.exercisetracker.model;

public class LinkedListStack<T> implements IStack {
    private Node top;
    private Coordinates coordinates;
    private int size;

    public void push(Coordinates coordinates) {

    }

    public Coordinates pop() {
        if(!isEmpty()){
           return coordinates;
        }
    }

    public Coordinates peek() {
        return coordinates;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        boolean isEmpty = false;
        if(top == null || size == 0) {
            isEmpty = true;
        }
        return isEmpty;
    }

    private static class Node<T> {
        private Node<T> next;
        private Coordinates coordinates;

        private Node(Coordinates coordinates, Node next) {
            this.coordinates = coordinates;
            this.next = next;
        }
    }
}
