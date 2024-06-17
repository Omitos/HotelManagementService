package com.example.designpatterns;

import AdminPOV.Staff.Staff;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class HousekeepingController implements Initializable {
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
    private Button searchButton;

    @FXML
    private TextField searching;

    @FXML
    TableView<Staff> tableView;
    @FXML
    TableColumn<Staff, Integer> staffID;
    @FXML
    TableColumn<Staff, String> staffFullName;
    @FXML
    TableColumn<Staff, String> staffPassword;
    @FXML
    TableColumn<Staff, String> message;
    @FXML
    private void handleSearchButton() throws SQLException {
        String searchText = searching.getText();

        Connection connection = Database.getInstance().getConnection();
        String sql = "SELECT * FROM Staff WHERE StaffID = ? OR StaffFullName LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        try {
            int searchID = Integer.parseInt(searchText);
            statement.setInt(1, searchID);
        } catch (NumberFormatException e) {
            statement.setNull(1, java.sql.Types.INTEGER);
        }

        statement.setString(2, "%" + searchText + "%");

        ResultSet resultSet = statement.executeQuery();
        tableView.getItems().clear();

        while (resultSet.next()) {
            Staff staff = new Staff(
                    resultSet.getInt("StaffID"),
                    resultSet.getString("StaffFullName"),
                    resultSet.getString("StaffPassword"),
                    resultSet.getString("message")
            );
            tableView.getItems().add(staff);
        }
    }

    public void showStaff() {
        Database database = Database.getInstance();
        ObservableList<Staff> staffMembers = FXCollections.observableArrayList();
        String query = "SELECT * FROM Staff ORDER BY StaffID";
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
                staffMembers.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        staffID.setCellValueFactory(cellData -> cellData.getValue().staffIDProperty().asObject());
        staffFullName.setCellValueFactory(cellData -> cellData.getValue().staffFullNameProperty());
        staffPassword.setCellValueFactory(cellData -> cellData.getValue().staffPasswordProperty());
        message.setCellValueFactory(cellData -> cellData.getValue().messageProperty());

        tableView.setItems(staffMembers);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStaff();
        searchButton.setOnAction(actionEvent -> {
            try {
                handleSearchButton();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
