enum TaskStatus {
    NEW, IN_PROGRESS, DONE
}
class Task {
    private static int idCounter = 1; // Статический счётчик для идентификаторов каждой задачи
    private final int taskId;
    private final String name;
    private final String description;
    private TaskStatus status;

    public Task(String name, String description) {
        this.taskId = idCounter++;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    // Getters
    public int getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    // Setter для обновления статуса
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}