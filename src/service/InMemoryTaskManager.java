package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int taskId = 0;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Task> subTasks = new HashMap<>();
    private final Map<Integer, Task> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private int makeId() {
        taskId++;
        return taskId;
    }

    @Override
    public void makeNewTask(Task task) {
        if (!tasks.containsValue(task)) {
            int id = makeId();
            task.setId(id);
            tasks.put(id, task);
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void makeNewSubtask(Task subtask) {
        if (!(subtask instanceof Subtask newSubtask)) {
            return;
        }

        int epicId = newSubtask.getEpicId();

        int id = makeId();
        newSubtask.setId(id);
        subTasks.put(id, newSubtask);

        Epic epic = (Epic) epics.get(epicId);
        epic.getSubtasksIdInEpic().add(id);

        updateEpicStatus(newSubtask);
    }

    @Override
    public ArrayList<Task> getAllSubtasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void delSubtasks() {
        subTasks.clear();
        for (Task epic : epics.values()) {
            Epic currentEpic = (Epic) epic;
            currentEpic.getSubtasksIdInEpic().clear();
            currentEpic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public void delSubtaskById(int id) {
        Task subtask = subTasks.get(id);
        Subtask subtask1 = (Subtask) subtask;
        int epicId = subtask1.getEpicId();
        Task epic = epics.get(epicId);
        Epic epic1 = (Epic) epic;
        epic1.getSubtasksIdInEpic().remove(subtask.getId());
        subTasks.remove(subtask.getId());
        updateEpicStatus(subtask);
    }

    @Override
    public Task getSubtaskById(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void updateSubTask(Task subtask) {
        if (subTasks.containsKey(subtask.getId())) {
            subTasks.put(subtask.getId(), subtask);
            updateEpicStatus(subtask);
        }
    }

    @Override
    public void makeNewEpic(Task epic) {
        if (epics.containsKey(epic.getId())) {
            return;
        }
        int id = makeId();
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public ArrayList<Task> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Task getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task getEpicById(Integer id) {
        return null;
    }

    @Override
    public void delEpicById(int id) {
        Task epic = epics.get(id);
        Epic epic1 = (Epic) epic;
        for (int val : epic1.getSubtasksIdInEpic()) {
            subTasks.remove(val);
        }
        epics.remove(id);
    }

    @Override
    public ArrayList<Task> getListOfSubTasksOfEpic(int id) {
        Task epic = getEpicById(id);
        Epic epic1 = (Epic) epic;
        ArrayList<Task> listOfSubTasksOfEpic = new ArrayList<>();
        for (int val : epic1.getSubtasksIdInEpic()) {
            listOfSubTasksOfEpic.add(subTasks.get(val));
        }
        return listOfSubTasksOfEpic;
    }

    private void updateEpicStatus(Task subtask) {
        Subtask newSubtask = (Subtask) subtask;
        int epicId = newSubtask.getEpicId();
        Epic epic = (Epic) epics.get(epicId);

        boolean allDone = true;
        boolean allNew = true;
        for (Integer subtaskId : epic.getSubtasksIdInEpic()) {
            Task sub = subTasks.get(subtaskId);
            if (sub.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (sub.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistoryTasks() {
        return historyManager.getHistory();
    }
}