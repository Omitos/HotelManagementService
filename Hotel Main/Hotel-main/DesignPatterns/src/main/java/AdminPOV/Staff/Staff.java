package AdminPOV.Staff;

import javafx.beans.property.*;
public class Staff {
    private final IntegerProperty staffID;
    private final StringProperty staffFullName;
    private final StringProperty staffPassword;
    private StringProperty message;
    int id;


    public Staff(int staffID, String staffFullName, String staffPassword, String message) {
        id = staffID;
        this.staffID = new SimpleIntegerProperty(staffID);
        this.staffFullName = new SimpleStringProperty(staffFullName);
        this.staffPassword = new SimpleStringProperty(staffPassword);
        this.message = new SimpleStringProperty(message);

    }

    public int getId() {
        return id;
    }

    public IntegerProperty staffIDProperty() {
        return staffID;
    }

    public StringProperty staffFullNameProperty() {
        return staffFullName;
    }

    public StringProperty staffPasswordProperty() {
        return staffPassword;
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void updateMessage(String message){
        this.message = new SimpleStringProperty(message);
    }
}
