package com.example.designpatterns;

import AdminPOV.Finances.CashBookEntry;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CashBooksController implements Initializable {
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
    private TableView<CashBookEntry> cashBookTableView;

    @FXML
    private TableColumn<CashBookEntry, Integer> entryID;

    @FXML
    private TableColumn<CashBookEntry, String> date;

    @FXML
    private TableColumn<CashBookEntry, String> guestName;

    @FXML
    private TableColumn<CashBookEntry, Integer> payment;

    @FXML
    private void handleSearchButton() {
        String searchText = searching.getText();

        Database database = Database.getInstance();
        ObservableList<CashBookEntry> cashBookEntries = FXCollections.observableArrayList();
        String query = "SELECT * FROM CashBook WHERE id = ? OR date LIKE ? OR guest_name LIKE ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            try {
                int searchID = Integer.parseInt(searchText);
                statement.setInt(1, searchID);
            } catch (NumberFormatException e) {
                statement.setNull(1, java.sql.Types.INTEGER);
            }

            statement.setString(2, "%" + searchText + "%");
            statement.setString(3, "%" + searchText + "%");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    CashBookEntry cashBookEntry = new CashBookEntry(
                            rs.getInt("id"),
                            rs.getString("date"),
                            rs.getString("guest_name"),
                            rs.getInt("payment")
                    );
                    cashBookEntries.add(cashBookEntry);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        entryID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        guestName.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
        payment.setCellValueFactory(cellData -> cellData.getValue().paymentProperty().asObject());

        cashBookTableView.setItems(cashBookEntries);
    }

    public void showCashBooks() {
        Database database = Database.getInstance();
        ObservableList<CashBookEntry> cashBookEntries = FXCollections.observableArrayList();
        String query = "SELECT * FROM CashBook ORDER BY id";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                CashBookEntry cashBookEntry = new CashBookEntry(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("guest_name"),
                        rs.getInt("payment")
                );
                cashBookEntries.add(cashBookEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        entryID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        guestName.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
        payment.setCellValueFactory(cellData -> cellData.getValue().paymentProperty().asObject());

        cashBookTableView.setItems(cashBookEntries);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCashBooks();
        searchButton.setOnAction(actionEvent -> {
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
