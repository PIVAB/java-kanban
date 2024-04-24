package service;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class TaskManagerTest {

    static TaskManager taskManager = Managers.getDefault();

    @BeforeEach
    void beforeEach(){
        final int task1 = taskManager.addNewTask(new Task("Задача1", "Описание задачи1"));
        final int task2 = taskManager.addNewTask(new Task("Задача2", "Описание задачи2"));
        final int task3 = taskManager.addNewTask(new Task("Задача3", "Описание задачи3"));
        final int task4 = taskManager.addNewTask(new Task("Задача4", "Описание задачи4"));

        final int epic1 = taskManager.addNewEpic(new Epic("Эпик1"));
        final int epic2 = taskManager.addNewEpic(new Epic("Эпик2"));

        final int subtask1 = taskManager.addNewSubtask(new Subtask("Подзадача1", "Описание подзадачи1",epic1));
        final int subtask2 = taskManager.addNewSubtask(new Subtask("Подзадача2", "Описание подзадачи2",epic1));
        final int subtask3 = taskManager.addNewSubtask(new Subtask("Подзадача3", "Описание подзадачи3",epic1));
        final int subtask4 = taskManager.addNewSubtask(new Subtask("Подзадача4", "Описание подзадачи4",epic2));
        final int subtask5 = taskManager.addNewSubtask(new Subtask("Подзадача5", "Описание подзадачи5",epic2));
        final int subtask6 = taskManager.addNewSubtask(new Subtask("Подзадача6", "Описание подзадачи6",epic2));
    }

    @Test
    void deleteTask() {
        final List<Task> savedTasks = taskManager.getAllTasks();
        int savedTasksCount = savedTasks.size();

        taskManager.deleteTaskById(savedTasks.get(0).getId());
        final List<Task> updatedTasks = taskManager.getAllTasks();

        assertEquals(updatedTasks.size(),savedTasksCount -1, "Таска не удалилась");
    }

    @Test
    void deleteSubtask() {
        final List<Subtask> savedSubtasks = taskManager.getAllSubTasks();
        int savedSubtasksCount = savedSubtasks.size();

        taskManager.deleteSubtaskById(savedSubtasks.get(0).getId());
        final List<Subtask> updatedSubtasks = taskManager.getAllSubTasks();

        assertEquals(savedSubtasksCount -1, updatedSubtasks.size(), "Подзадача не удалилась");
    }

    @Test
    void deleteEpic() {
        final List<Epic> savedEpics = taskManager.getAllEpics();
        final List<Subtask> savedSubtasks = taskManager.getAllSubTasks();
        final List<Subtask> savedEpicSubtask = taskManager.getAllTasksByEpic(savedEpics.get(0).getId());
        final int savedEpicsSize = savedEpics.size();
        int countDeletedSubtaskInEpic = savedEpicSubtask.size();

        assertTrue(countDeletedSubtaskInEpic > 0,"В эпике нет подзадач");

        taskManager.deleteEpicById(savedEpics.get(0).getId());

        final List<Epic> updatedEpics = taskManager.getAllEpics();
        final List<Subtask> updatedSubtask = taskManager.getAllSubTasks();

        assertEquals(updatedEpics.size(), savedEpicsSize -1 , "Эпик не удалён");
        assertEquals(savedSubtasks.size() - countDeletedSubtaskInEpic, updatedSubtask.size(), "Не удалены подзадачи");
    }

    @Test
    void updateEpicStatus() {
        final List<Epic> savedEpics = taskManager.getAllEpics();
        final Epic epic = savedEpics.get(0);

        assertEquals(epic.getStatus(), TaskStatus.NEW, "Статус эпика не Новый");

        final List<Subtask> savedSubtask = taskManager.getAllTasksByEpic(epic.getId());
        assertTrue(savedSubtask.size() > 0, "Эпик не содержит задач");

        for (Subtask subtask : savedSubtask) {
            Subtask updateSubtask = new Subtask(subtask.getId(), subtask.getTitle(), subtask.getDescription(),
                    subtask.getEpicId(),"done");
            taskManager.updateSubtask(updateSubtask);
        }

        final Epic updatedEpic = taskManager.getEpicById(epic.getId());
        assertEquals(updatedEpic.getStatus(), TaskStatus.DONE, "Эпик не перешёл на конечный статус");

    }
}