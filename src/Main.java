import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Epic epic = new Epic("Создание нового продукта", "Разработка нового продукта c нуля");

        Subtask subtask1 = new Subtask("Подготовить макет дизайна", "Создание макета интерфейса приложения", epic);
        Subtask subtask2 = new Subtask("Написать тестовый код", "Кодирование модульных тестов", epic);

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        taskManager.createTask(epic);
        taskManager.createTask(subtask1);
        taskManager.createTask(subtask2);

        // Пример обновления статуса задачи
        subtask1.setStatus(TaskStatus.IN_PROGRESS);

        // Получение списка всех задач
        List<Task> allTasks = taskManager.getAllTasks();
        for (Task task : allTasks) {
            System.out.println("ID: " + task.getTaskId() + ", Name: " + task.getName() +
                    ", Status: " + task.getStatus());
        }

        // Получение подзадач конкретного эпика
        List<Subtask> epicSubtasks = taskManager.getSubtasksByEpic(epic);
        System.out.println("Subtasks of Epic: " + epic.getName());
        for (Subtask subtask : epicSubtasks) {
            System.out.println("ID: " + subtask.getTaskId() + ", Name: " + subtask.getName() +
                    ", Status: " + subtask.getStatus());
        }
    }
}