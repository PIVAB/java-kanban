package service;

import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node<Task>> historyDb;
    private Node<Task> head;
    private Node<Task> tail;

    public InMemoryHistoryManager() {
        this.historyDb = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        int id = task.getId();
        remove(id);
        linkLast(task);
        historyDb.put(id, tail);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        Node<Task> removedNode = historyDb.remove(id);

        if (removedNode != null) {
            removeNode(removedNode);
        }
    }

    private void removeNode(Node<Task> node) {
        final Node<Task> prevNode = node.prev;
        final Node<Task> nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }

        if (head == node) {
            head = nextNode;
        }
        if (tail == node) {
            tail = prevNode;
        }
    }

    private void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode;

        if (oldTail == null) {
            newNode = new Node<>(null, task, null);
            head = newNode;
        } else {
            newNode = new Node<>(oldTail, task, null);
            oldTail.next = newNode;
        }
        tail = newNode;
    }

    public static class Node<E> {
        public E data;
        public Node<E> prev;
        public Node<E> next;

    public Node(Node<E> prev, E data, Node<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}

    private List<Task> getTasks() {
        Node<Task> node = head;
        List<Task> historyList = new ArrayList<>();

        for (int i = 0; i < historyDb.size(); i++) {
            historyList.add(node.data);
            node = node.next;
        }

        return historyList;
    }
}