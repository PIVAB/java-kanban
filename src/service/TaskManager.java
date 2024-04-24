package service;


import java.util.List;
import model.Epic;
import model.Subtask;
import model.Task;

public interface TaskManager {
    int addNewTask(Task task);



  Task getTaskById(int taskId);


