package com.example.designpatterns;

import AdminPOV.Rooms.*;
import AdminPOV.Services.AdditionalService;
import AdminPOV.Services.BuffetDecorator;
import AdminPOV.Services.SPADecorator;
import AdminPOV.Services.ServiceDecorator;
import AdminPOV.Staff.Staff;
import AdminPOV.Staff.StaffObserver;
import DatabaseFunc.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

public class FrontDeskController implements Initializable {
    @FXML
    Button FrontDesk;
    @FXML
    Button Reservations;
    @FXML
    Button Tasks;
    @FXML
    Button Housekeeping;
    @FXML
    Button CashBooks;
    @FXML
    Button Supplies;
    @FXML
    Button Orders;
    @FXML
    ComboBox<Integer> comboBox;
    @FXML
    TextField guestName;
    @FXML
    TextField roomType;
    @FXML
    CheckBox spa;
    @FXML
    CheckBox buffet;
    @FXML
    Button searchRooms;
    @FXML
    Button addReservation;
    @FXML
    TextField startDate;
    @FXML
    TextField endDate;
    @FXML
    Button send;
    @FXML TextArea mess;
    StaffObserver staffObserver = new StaffObserver();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        send.setOnAction(actionEvent -> {
            Database database = Database.getInstance();
            String query = "SELECT * FROM Staff";
            Statement st;
            ResultSet rs;
            try {
                Connection connection = database.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(query);
                Staff staff;
                while (rs.next()) {
                    staff = new Staff(rs.getInt("StaffID"), rs.getString("StaffFullName"),
                            rs.getString("StaffPassword"), rs.getString("message"));
                    staffObserver.addStaff(staff);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            staffObserver.notifySubscribers(mess);
        });
        addReservation.setOnAction(actionEvent -> {
            boolean spa = this.spa.isSelected();
            boolean buffet = this.buffet.isSelected();
            AdditionalService additionalService = new ServiceDecorator();
            if(spa) additionalService = new SPADecorator(additionalService);
            if(buffet) additionalService = new BuffetDecorator(additionalService);
            LocalDate start = LocalDate.parse(startDate.getText());
            LocalDate end = LocalDate.parse(endDate.getText());
            long duration = ChronoUnit.DAYS.between(start, end);
            RoomFactory roomFactory = null;
            Room room;
            if(roomType.getText().equals("Standard")){
                roomFactory = new StandardRoomFactory();
            }else if(roomType.getText().equals("Deluxe")){
                roomFactory = new DeluxeRoomFactory();
            }else if(roomType.getText().equals("Executive")){
                roomFactory = new ExecutiveRoomFactory();
            }
            room = roomFactory.createRoom();
            int price = (int) (additionalService.calculatePrice() + duration * room.getRoomPrice());
            int roomNo = comboBox.getSelectionModel().getSelectedItem();
            Database database = Database.getInstance();
            try {
                String query = "INSERT INTO reservations (guestName, roomType, roomNumber, startDate, endDate) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = database.getConnection().prepareStatement(query);

                statement.setString(1, guestName.getText());
                statement.setString(2, roomType.getText());
                statement.setInt(3, roomNo);
                statement.setDate(4, Date.valueOf(start));
                statement.setDate(5, Date.valueOf(end));

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                String query = "INSERT INTO CashBook (date, guest_name, payment) VALUES (?, ?, ?)";
                PreparedStatement statement = database.getConnection().prepareStatement(query);

                statement.setDate(1, Date.valueOf(start));
                statement.setString(2, guestName.getText());
                statement.setInt(3, price);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                String query = "UPDATE Rooms SET roomAvailability = 'Not Available' WHERE roomNumber = ?";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setInt(1, roomNo); // roomNumber is the specific room number you want to update
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }try {
                String query = "UPDATE inventory SET quantity = quantity - 1 WHERE type = 'Renewable'";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }try {
                String query = "DELETE FROM reservations WHERE enddate < ?";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setDate(1, java.sql.Date.valueOf(LocalDate.now())); // Using java.sql.Date for SQL DATE
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        searchRooms.setOnAction(actionEvent -> {
            String roomType = this.roomType.getText().toString();
            Database database = Database.getInstance();
            ObservableList<Integer> list = FXCollections.observableArrayList();
            String query = "SELECT roomNumber FROM Rooms WHERE roomType = '" + roomType +"' AND roomAvailability = 'Available'";
            try (Connection connection = database.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("roomNumber"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            comboBox.setItems(list);
        });
        FrontDesk.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("frontdesk.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        Reservations.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reservations.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        Tasks.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("tasks.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        Housekeeping.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("housekeeping.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        CashBooks.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("cashbooks.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        Supplies.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("supplies.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        Orders.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("orders.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
    }
}
