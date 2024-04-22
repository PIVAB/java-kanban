package service;

import java.util.List;
import model.Epic;
import model.Subtask;
import model.Task;

public interface TaskManager {

  int addNewTask(Task task);

  void updateTask(Task newTask);

  List<Task> getAllTasks();

  Task getTaskById(int taskId);

  void deleteTaskById(int id);

  void deleteAllTasks();

  int addNewEpic(Epic epic);

  void updateEpic(Epic epic);

  void deleteEpicById(int epicId);

  List<Epic> getAllEpics();

  Epic getEpicById(int epicId);

  Integer addNewSubtask(Subtask subtask);

  void getSubtaskById(int subTaskId);

  List<Subtask> getAllTasksByEpic(int epicId);

  void updateSubtask(Subtask subtask);

  void deleteSubtaskById(int subtaskId);

  List<Subtask> getAllSubTasks();

  List<Task> getHistory();
}