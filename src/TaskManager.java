import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();

    // Создание новой задачи
    public void createTask(Task task) {
        tasksMap.put(task.getTaskId(), task);
    }

    // Получение задачи по идентификатору
    public Task getTaskById(int taskId) {
        return tasksMap.get(taskId);
    }

    // Обновление задачи
    public void updateTask(Task updatedTask) {
        tasksMap.put(updatedTask.getTaskId(), updatedTask);
    }

    // Удаление задачи по идентификатору
    public void deleteTaskById(int taskId) {
        tasksMap.remove(taskId);
    }

    // Получение списка всех задач
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    // Получение списка всех подзадач конкретного эпика
    public List<Subtask> getSubtasksByEpic(Epic epic) {
        List<Subtask> subtasks = new ArrayList<>();
        for (Task task : tasksMap.values()) {
            if (task instanceof Subtask) {
                Subtask subtask = (Subtask) task;
                if (subtask.getEpic().equals(epic)) {
                    subtasks.add(subtask);
                }
            }
        }
        return subtasks;
    }
}
