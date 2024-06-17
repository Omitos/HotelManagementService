package AdminPOV.Staff;

import DatabaseFunc.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffObserver {
    ObservableList<Staff> staff =  FXCollections.observableArrayList();;
    public void addStaff(Staff staff){
        this.staff.add(staff);
    }
    public void removeStaff(Staff staff){
        this.staff.remove(staff);
    }
    public void notifySubscribers(TextArea area) {
        for (Staff value : this.staff) {
            try {
                Database database = Database.getInstance();
                String updateQuery = "UPDATE Staff SET message = ? WHERE StaffID = ?";
                PreparedStatement preparedStatement = database.getConnection().prepareStatement(updateQuery);
                preparedStatement.setString(1, area.getText());
                preparedStatement.setInt(2, value.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
