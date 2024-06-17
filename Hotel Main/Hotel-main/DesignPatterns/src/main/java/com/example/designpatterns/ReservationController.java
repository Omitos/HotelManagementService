package com.example.designpatterns;

import AdminPOV.Rooms.Reservation;
import AdminPOV.Rooms.Room;
import DatabaseFunc.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
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
    TableView<Reservation> tableView;
    @FXML
    TableColumn<Reservation, Integer> ReservationID;
    @FXML
    TableColumn<Reservation, String> GuestName;
    @FXML
    TableColumn<Reservation, String> RoomType;
    @FXML
    TableColumn<Reservation, Integer> RoomNumber;
    @FXML
    TableColumn<Reservation, Date> StartDate;
    @FXML
    TableColumn<Reservation, Date> EndDate;
    @FXML
    private Button SearchButton;
    @FXML
    private TextField searching;

    @FXML
    private void handleSearchButton() {
        String searchText = searching.getText();

        Database database = Database.getInstance();
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservations WHERE reservationID = ? OR guestName LIKE ? OR roomType LIKE ? OR roomNumber = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try {
                int searchID = Integer.parseInt(searchText);
                statement.setInt(1, searchID);
                statement.setNull(3, java.sql.Types.VARCHAR); // Setting roomType to NULL for number-only searches
            } catch (NumberFormatException e) {
                statement.setNull(1, java.sql.Types.INTEGER);
                statement.setString(3, "%" + searchText + "%"); // Setting roomType for non-number searches
            }
            statement.setString(2, "%" + searchText + "%");
            try {
                int roomNumberSearch = Integer.parseInt(searchText);
                statement.setInt(4, roomNumberSearch);
            } catch (NumberFormatException e) {
                statement.setNull(4, java.sql.Types.INTEGER);
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getInt("reservationID"),
                            rs.getString("guestName"),
                            rs.getString("roomType"),
                            rs.getInt("roomNumber"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate")
                    );
                    reservations.add(reservation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ReservationID.setCellValueFactory(cellData -> cellData.getValue().reservationIDProperty().asObject());
        GuestName.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
        RoomType.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());
        RoomNumber.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());
        StartDate.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        EndDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        tableView.setItems(reservations);
    }
    public void showReservations(){
        Database database = Database.getInstance();
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservations";
        Statement st;
        ResultSet rs;
        try {
            Connection connection = database.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Reservation reservation;
            while(rs.next()){
                reservation = new Reservation(rs.getInt("reservationID"), rs.getString("guestName"), rs.getString("roomType"),
                        rs.getInt("roomNumber"), rs.getDate("startDate"), rs.getDate("endDate"));
                reservations.add(reservation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ReservationID.setCellValueFactory(cellData -> cellData.getValue().reservationIDProperty().asObject());
        GuestName.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
        RoomType.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());
        RoomNumber.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());
        StartDate.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        EndDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        tableView.setItems(reservations);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showReservations();
        SearchButton.setOnAction(actionEvent -> {
            handleSearchButton();
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
