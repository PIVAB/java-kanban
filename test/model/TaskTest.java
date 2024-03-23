package model;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static model.TaskStatus.*;
import service.*;


class TaskTest {

    @Test
    void taskEqualsIfIdEquals() {
        Task task = new Task("Test taskEqualsIfIdEquals",
                "Test taskEqualsIfIdEquals description", NEW);
        TaskManager taskManager = Managers.getDefault();
        taskManager.makeNewTask(task);
        int taskId = task.getId();

        assertEquals(taskManager.getTaskById(taskId), taskManager.getTaskById(taskId),
                "Задачи с одинаковым id не совпадают");
    }

    @Test
    void ShouldNotConflictWhenSetIdAndGenerateId() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test ShouldNotConflictWhenSetIdAndGenerateId",
                "task", NEW);
        taskManager.makeNewTask(task);
        int id = task.getId();
        Task task1 = new Task("Test ShouldNotConflictWhenSetIdAndGenerateId",
                "task1", NEW);
        task1.setId(id);
        taskManager.makeNewTask(task1);

        assertEquals(taskManager.getTaskById(id), task);
        assertNotEquals(taskManager.getTaskById(id), task1);
    }

    @Test
    void addNewTask() {
        int taskId = task.getId();
        TaskManager taskManager = Managers.getDefault();
        taskManager.makeNewTask(task);
        int taskId = task.getId();

        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }
}