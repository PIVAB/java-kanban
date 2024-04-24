package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class HistoryManagerTest {

    @Test
    void shouldBeHistory() {
        TaskManager taskManager = Managers.getDefault();
        addManyTasks(taskManager);

        List<Task> tasks = taskManager.getAllTasks();
        List<Epic> epics = taskManager.getAllEpics();
        List<Subtask> subtasks = taskManager.getAllSubTasks();

        final int countAllTasks = tasks.size() + epics.size() + subtasks.size();

        HistoryManager historyManager = Managers.getDefaultHistory();

        for (Task task : tasks) {
            historyManager.add(task);
        }
        for (Epic epic : epics) {
            historyManager.add(epic);
        }
        for (Subtask subtask : subtasks) {
            historyManager.add(subtask);
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(countAllTasks, history.size(), "История не равна количеству запрошенных задач");
    }

    @Test
    void inHistoryShouldChangeTask() {
        TaskManager taskManager = Managers.getDefault();

        final String title = "TITLE";
        final String updatedTitle = "NEW_TITLE";
        final String description = "DESCRIPTION";

        final int taskId = taskManager.addNewTask(new Task(title, description));
        taskManager.getTaskById(taskId);

        List<Task> historyList = taskManager.getHistory();
        Task historyTask = historyList.get(0);
        assertEquals(title, historyTask.getTitle(), "Ожидалось другое название задачи");

        Task updatedTask = new Task(taskId, updatedTitle, "NEW_DESCRIPTION");
        taskManager.updateTask(updatedTask);
        updatedTask = taskManager.getTaskById(taskId);

        assertEquals("NEW_TITLE", updatedTask.getTitle(), "Задача не обновилась");

        historyList.clear();
        historyList = taskManager.getHistory();

        assertEquals(1, historyList.size(), "Старая история не удалилась");

        historyTask = historyList.get(0);

        assertEquals(updatedTitle, historyTask.getTitle(), "В историю попала обновленная задача");
    }

    @Test
    void inHistoryShouldNotChangeEpicAndSubtask() {
        TaskManager taskManager = Managers.getDefault();

        final String titleEpic = "TITLE_EPIC";
        final String titleSubtask = "TITLE_SUBTASK";
        final String description = "DESCRIPTION";

        final int epicId = taskManager.addNewEpic(new Epic(titleEpic));
        final int subtaskId = taskManager.addNewSubtask(new Subtask(titleSubtask, description, epicId));

        taskManager.getEpicById(epicId);
        taskManager.getSubtaskById(subtaskId);

        Epic updatedEpic = new Epic(epicId, "NEW_TITLE_EPIC");
        taskManager.updateEpic(updatedEpic);
        Subtask updatedSubtask = new Subtask(subtaskId, "NEW_TITLE,SUBTASK", "NEW_TITLE,DESCRIPTION"
                , epicId, TaskStatus.IN_PROGRESS.toString());
        taskManager.updateSubtask(updatedSubtask);

        final List<Task> history = taskManager.getHistory();

        assertEquals(titleEpic, history.get(0).getTitle(), "В истории обновленный эпик");
        assertEquals(titleSubtask, history.get(1).getTitle(), "В истории обновленная подзадача");
    }

    @Test
    void ShouldBeUniqueIdsInHistory() {
        TaskManager taskManager = Managers.getDefault();
        addManyTasks(taskManager);

        HistoryManager historyManager = Managers.getDefaultHistory();
        List<Task> tasks = taskManager.getAllTasks();

        for (int i = 0; i < 2; i++) {
            for (Task task : tasks) {
                historyManager.add(task);
            }
        }

        List<Task> historyList = historyManager.getHistory();

        assertEquals(tasks.size(), historyList.size(), "Количество задач не совпадает с количеством" +
                " в истории");

        Set<Integer> taskNumbers = new HashSet<>();
        for (Task task : historyList) {
            taskNumbers.add(task.getId());
        }

        assertEquals(historyList.size(), taskNumbers.size(), "Провалена проверка на уникальность" +
                " идентификаторов в истории");
    }


    void addManyTasks(TaskManager taskManager) {
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
}