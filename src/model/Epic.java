package model;

import java.util.List;

public class Epic extends Task {

    private List<Integer> subtaskIdList;

    public Epic(String title) {
        this.title = title;
        this.status = TaskStatus.NEW;
    }

    public Epic(int id, String title) {
        this.id = id;
        this.title = title;
        this.status = TaskStatus.NEW;
    }

    public Epic(int id, String title, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Epic(Epic original) {
        this.id = original.id;
        this.title = original.title;
        this.status = original.status;
        this.subtaskIdList = original.subtaskIdList;
    }

    public void addNewSubtask(int subtaskId) {
        subtaskIdList.add(subtaskId);
    }

    public List<Integer> getSubtaskIdList() {
        return subtaskIdList;
    }

    public void deleteSubtask(int subtaskId) {
        subtaskIdList.remove((Integer) subtaskId);
    }


    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
