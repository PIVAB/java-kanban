package model;

import java.util.Objects;

public class Task {



  public Task(int id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = TaskStatus.NEW;
  }

  public Task(int id, String title, String description, String status) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = TaskStatus.valueOf(status.toUpperCase());
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


