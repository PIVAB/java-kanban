package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new LinkedList<>();
    private static final int HISTORY_SIZE = 10;

    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void add(Task task) {
        if (history.size() >= HISTORY_SIZE) {
            history.remove(0);
        }
        history.add(task);
    }
}