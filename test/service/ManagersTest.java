package service;

import model.Task;
import org.junit.jupiter.api.Test;

import static model.TaskStatus.NEW;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void newManager() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test newManager",
                "Test newManager description", NEW);
        manager.makeNewTask(task);
        assertEquals("Test newManager", task.getName());
    }

    @Test
    void taskEqualsAfterAdd() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test taskEqualsAfterAdd", "Test taskEqualsAfterAdd description", NEW);
        manager.makeNewTask(task);
        assertEquals("Test taskEqualsAfterAdd", task.getName());
        assertEquals("Test taskEqualsAfterAdd description", task.getDescription());
        assertEquals(NEW, task.getStatus());
        assertEquals(1, task.getId());
    }
}