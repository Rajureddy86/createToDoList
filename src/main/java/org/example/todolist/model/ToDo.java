package org.example.todolist.model;

public class ToDo {
    private Long id;
    private String description;
    private boolean completionStatus;

    public ToDo() {
    }

    public ToDo(Long id, String description, boolean completionStatus) {
        this.id = id;
        this.description = description;
        this.completionStatus = completionStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus = completionStatus;
    }
}
