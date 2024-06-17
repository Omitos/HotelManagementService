package com.example.designpatterns;

import AdminPOV.Staff.Task;
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

public class TasksController implements Initializable {
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
    private Button SearchButton;
    @FXML
    private TextField Searching;
    @FXML
    TableView<Task> taskTableView;
    @FXML
    TableColumn<Task, Integer> taskIDColumn;
    @FXML
    TableColumn<Task, Integer> assignedToStaffColumn;
    @FXML
    TableColumn<Task, String> taskNameColumn;
    @FXML
    TableColumn<Task, String> taskDescriptionColumn;
    @FXML
    TableColumn<Task, Integer> roomNumberColumn;
    @FXML
    TextField taskName;
    @FXML
    TextField roomNumber;
    @FXML
    TextField StaffName;
    @FXML
    TextField description;
    @FXML
    Button addTask;
    @FXML
    private void handleSearchButton() {
        String searchText = Searching.getText();
        Database database = Database.getInstance();
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        String query = "SELECT * FROM tasks WHERE taskID = ? OR taskName LIKE ? OR roomNumber = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            try {
                int searchID = Integer.parseInt(searchText);
                statement.setInt(1, searchID);
                statement.setNull(3, java.sql.Types.INTEGER);
            } catch (NumberFormatException e) {
                statement.setNull(1, java.sql.Types.INTEGER);
                statement.setString(2, "%" + searchText + "%");
                statement.setNull(3, java.sql.Types.INTEGER);
            }

            try {
                int roomNumberSearch = Integer.parseInt(searchText);
                statement.setInt(3, roomNumberSearch);
            } catch (NumberFormatException e) {
                statement.setNull(3, java.sql.Types.INTEGER);
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(
                            rs.getInt("taskID"),
                            rs.getInt("assignedToStaff"),
                            rs.getString("taskName"),
                            rs.getString("taskDescription"),
                            rs.getInt("roomNumber")
                    );
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        taskIDColumn.setCellValueFactory(cellData -> cellData.getValue().taskIDProperty().asObject());
        assignedToStaffColumn.setCellValueFactory(cellData -> cellData.getValue().assignedToStaffProperty().asObject());
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        taskDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().taskDescriptionProperty());
        roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());

        taskTableView.setItems(tasks);
    }
    public void showTasks() {
        Database database = Database.getInstance();
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        String query = "SELECT * FROM tasks";
        Statement st;
        ResultSet rs;

        try {
            Connection connection = database.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);

            Task task;
            while (rs.next()) {
                task = new Task(rs.getInt("taskID"), rs.getInt("assignedToStaff"),
                        rs.getString("taskName"), rs.getString("taskDescription"), rs.getInt("roomNumber"));
                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        taskIDColumn.setCellValueFactory(cellData -> cellData.getValue().taskIDProperty().asObject());
        assignedToStaffColumn.setCellValueFactory(cellData -> cellData.getValue().assignedToStaffProperty().asObject());
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        taskDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().taskDescriptionProperty());
        roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());

        taskTableView.setItems(tasks);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTasks();
        SearchButton.setOnAction(actionEvent -> {
            handleSearchButton();
        });
        addTask.setOnAction(actionEvent -> {
            int assignedToStaffID = Integer.parseInt(StaffName.getText());
            String name = taskName.getText();
            String desc = description.getText();
            int roomNo = Integer.parseInt(roomNumber.getText());

            Database database = Database.getInstance();

            try {
                String query = "INSERT INTO tasks (assignedToStaff, taskName, taskDescription, roomNumber) " +
                        "VALUES (?, ?, ?, ?)";
                PreparedStatement statement = database.getConnection().prepareStatement(query);

                statement.setInt(1, assignedToStaffID);
                statement.setString(2, name);
                statement.setString(3, desc);
                statement.setInt(4, roomNo);

                statement.executeUpdate();
                System.out.println("Task inserted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
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
