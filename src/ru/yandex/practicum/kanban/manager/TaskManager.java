package ru.yandex.practicum.kanban.manager;

import ru.yandex.practicum.kanban.entities.Epic;
import ru.yandex.practicum.kanban.entities.Status;
import ru.yandex.practicum.kanban.entities.SubTask;
import ru.yandex.practicum.kanban.entities.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epics;
    private int sequenceTaskId = 0;

    public TaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public void clearAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeAllSubTasks();
        }
    }

    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public int createTask(Task task) {
        task.setId(nextSequenceId());
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public int createSubTask(SubTask subTask) {
        int subTaskId = nextSequenceId();
        int epicId = subTask.getEpicId();
        subTask.setId(subTaskId);
        subTasks.put(subTaskId, subTask);
        epics.get(epicId).addSubTask(subTaskId);
        checkUpdateStatus(epicId);
        return subTaskId;
    }

    public int createEpic(Epic epic) {
        epic.setId(nextSequenceId());
        epics.put(epic.getId(), epic);
           return epic.getId();
    }

    public void modifyTask(Task task) {

        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void modifySubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
        }
        checkUpdateStatus(subTask.getEpicId());
    }

    public void modifyEpic(Epic epic) {
        int epicId = epic.getId();
        if (epics.containsKey(epicId)) {
            Epic currentEpic = epics.get(epicId);
            currentEpic.setName(epic.getName());
            currentEpic.setDescription(epic.getDescription());
        }
    }

    public void deleteTask(Integer taskId) {
        tasks.remove(taskId);
    }

    public void deleteSubTask(Integer subTaskId) {
        SubTask subTask = subTasks.remove(subTaskId);
        if (subTask != null) {
            int epicId = subTask.getEpicId();
            epics.get(epicId).removeSubTask(subTaskId);
            checkUpdateStatus(epicId);
        }
    }

    public void deleteEpic(Integer epicId) {
        Epic epic = epics.remove(epicId);
        if (epic != null) {
            for (Integer subTaskId : epic.getSubTasks()) {
                subTasks.remove(subTaskId);
            }
        }
    }

    public ArrayList<Task> getSubTasksByEpic(Integer epicId) {
        ArrayList<Task> subTasksByEpic = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            for (Integer subTaskId : epics.get(epicId).getSubTasks()) {
                subTasksByEpic.add(subTasks.get(subTaskId));
            }
        }
        return subTasksByEpic;
    }

    private void checkUpdateStatus(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            ArrayList<Integer> epicSubTaskList = epic.getSubTasks();
            int countDone = 0;
            int countNew = 0;
            for (Integer subTaskId : epicSubTaskList) {
                SubTask subtask = subTasks.get(subTaskId);
                switch (subtask.getStatus()) {
                    case NEW:
                        countNew++;
                        break;
                    case DONE:
                        countDone++;
                        break;
                }
            }
            if (epicSubTaskList.isEmpty() || epicSubTaskList.size() == countNew) {
                epic.setStatus(Status.NEW);
            } else if (epicSubTaskList.size() == countDone) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    private int nextSequenceId() {
        return ++sequenceTaskId;
    }

}
