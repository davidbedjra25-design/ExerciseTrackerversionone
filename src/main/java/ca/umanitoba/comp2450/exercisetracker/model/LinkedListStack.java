package ca.umanitoba.comp2450.exercisetracker.model;

import com.google.common.base.Preconditions;

public class LinkedListStack<T> implements Stack<T> {

    private class Node {
        Node next;
        T data;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    public  LinkedListStack() {
        this.top = null;
        this.size = 0;
        checkLinkedListStack();
    }

    /**
     * Push something on to the top of the stack. The stack's size will increase
     * by one.
     *
     * @param item the item that should become the top of the stack.
     */
    public void push(T item) {
        Preconditions.checkNotNull(item, "Item should not be null.");
        checkLinkedListStack();
        Node current = new Node(item);
        current.next = top;
        top = current;
        size++;
        checkLinkedListStack();
    }

    /**
     * Pop the top of the stack off. The stack's size may be reduced by one, if
     * the stack is not already empty. If the stack is empty, this method will
     * return {@code null}.
     *
     * @return the item on the top of the stack, or {@code null} if the stack is
     * empty.
     * @throws Stack.EmptyStackException when the stack is empty (we can't pop an empty stack).
     */
    public T pop() throws EmptyStackException {
        checkLinkedListStack();
        if(size == 0 || top == null) {
            throw new EmptyStackException("There is nothing to pop since the stack is empty.");
        }
        Node current = top;
        top = top.next;
        size--;
        checkLinkedListStack();
        return current.data;
    }

    /**
     * How many items are currently on the stack? Always returns zero or a
     * positive number.
     *
     * @return the number of items on the stack.
     */
    public int size() {
        checkLinkedListStack();
        return size;
    }

    /**
     * A convenience test to see if the stack is currently empty (its size is
     * zero and the underlying storage container has no elements).
     *
     * @return {@code true} if the stack is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        checkLinkedListStack();
        return size == 0;
    }

    /**
     * Look at the thing that's on the top of the stack without {@link
     * Stack#pop()}ing it off the top of the stack.
     *
     * @return the item on the top of the stack.
     * @throws EmptyStackException when the stack is empty (we can't peek an empty stack).
     */
    public T peek() throws EmptyStackException {
        checkLinkedListStack();
        if(size == 0 || top == null) {
            throw new EmptyStackException("There is nothing in the stack to peek.");
        }
        checkLinkedListStack();
        return top.data;
    }

    private void checkLinkedListStack() {
        Preconditions.checkState(size >= 0, "Size cannot be negative.");
    }

}
