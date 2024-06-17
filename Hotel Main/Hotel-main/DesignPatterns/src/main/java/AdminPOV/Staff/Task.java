package AdminPOV.Staff;

import javafx.beans.property.*;

public class Task {
    private final IntegerProperty taskID;
    private final IntegerProperty assignedToStaff;
    private final StringProperty taskName;
    private final StringProperty taskDescription;
    private final IntegerProperty roomNumber;

    public Task(int taskID, int assignedToStaff, String taskName, String taskDescription, int roomNumber) {
        this.taskID = new SimpleIntegerProperty(taskID);
        this.assignedToStaff = new SimpleIntegerProperty(assignedToStaff);
        this.taskName = new SimpleStringProperty(taskName);
        this.taskDescription = new SimpleStringProperty(taskDescription);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
    }

    // Add getters for properties
    public IntegerProperty taskIDProperty() {
        return taskID;
    }

    public IntegerProperty assignedToStaffProperty() {
        return assignedToStaff;
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }

    public StringProperty taskDescriptionProperty() {
        return taskDescription;
    }

    public IntegerProperty roomNumberProperty() {
        return roomNumber;
    }
}