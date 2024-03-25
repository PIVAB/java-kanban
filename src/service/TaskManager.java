package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void makeNewTask(Task task);

    ArrayList<Task> getAllTasks();

    void deleteTasks();

    Task getTaskById(int id);

    void updateTask(Task task);

    void deleteTaskById(int id);

    void makeNewSubtask(Task subtask);

    ArrayList<Task> getAllSubtasks();

    void delSubtasks();

    void delSubtaskById(int id);

    Task getSubtaskById(int id);

    void updateSubTask(Task task);

    void makeNewEpic(Task epic);

    ArrayList<Task> getAllEpics();

    void deleteEpics();

    Task getEpicById(int id);

    abstract Task getEpicById(Integer id);

    void delEpicById(int id);

    ArrayList<Task> getListOfSubTasksOfEpic(int id);

    List<Task> getHistoryTasks();

}