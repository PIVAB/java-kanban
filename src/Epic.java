import java.util.ArrayList;
import java.util.List;

class Epic extends Task {
    private final List<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasks = new ArrayList<>();
    }

    // Добавление подзадачи к эпику
    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    // Получение всех подзадач эпика
    public List<Subtask> getSubtasks() {
        return subtasks;
    }
}
