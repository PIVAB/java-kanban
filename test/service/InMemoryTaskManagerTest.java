package service;

import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import static model.TaskStatus.NEW;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void addTasks() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task = new Task("Test addTasks",
                "Test addTasks description", NEW);
        inMemoryTaskManager.makeNewTask(task);
        Epic epic = new Epic("Test addTasks",
                "Test addTasks description");
        inMemoryTaskManager.makeNewEpic(epic);
        Subtask subtask = new Subtask("Test addTasks",
                "Test addTasks description", NEW, epic.getId());
        inMemoryTaskManager.makeNewSubtask(subtask);

        assertEquals(task, inMemoryTaskManager.getTaskById(task.getId()));
        assertEquals(epic, inMemoryTaskManager.getEpicById(epic.getId()));
        assertEquals(subtask, inMemoryTaskManager.getSubtaskById(subtask.getId()));
    }
}