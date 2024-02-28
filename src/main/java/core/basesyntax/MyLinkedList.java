package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE
            = "The index value is outside the list";

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T item) {
            this.item = item;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
            size++;
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        if (currentNode.next == null) {
            T lastItem = currentNode.item;
            currentNode.item = value;
            add(lastItem);
        } else {
            currentNode.prev.next = newNode;
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev = newNode;
            size++;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        assertIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        assertIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        assertIndex(index);
        Node<T> removedNode = findNodeByIndex(index);
        T removedValue = removedNode.item;
        if (removedNode == first && first.next == null) {
            first = null;
        } else if (removedNode == first) {
            first.next.prev = null;
            first = first.next;
        } else if (removedNode == last) {
            removedNode.prev.next = null;
            removedNode.prev = last;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> objectToRemove = first;
        for (int i = 0; i < size; i++) {
            if (object == objectToRemove.item
                    || (object != null && object.equals(objectToRemove.item))) {
                remove(i);
                return true;
            }
            objectToRemove = objectToRemove.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void assertIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
}
