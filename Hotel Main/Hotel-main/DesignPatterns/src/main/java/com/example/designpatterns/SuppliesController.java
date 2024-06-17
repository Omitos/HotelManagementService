package com.example.designpatterns;

import AdminPOV.Inventory.InventoryItem;
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
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class SuppliesController implements Initializable {
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
    TableView<InventoryItem> inventoryTableView;
    @FXML
    TableColumn<InventoryItem, Integer> supplyID;
    @FXML
    TableColumn<InventoryItem, String> supplyName;
    @FXML
    TableColumn<InventoryItem, String> supplyType;
    @FXML
    TableColumn<InventoryItem, Integer> quantity;

    @FXML
    private void handleSearchButton() {
        String searchText = searching.getText();

        Database database = Database.getInstance();
        ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList();
        String query = "SELECT * FROM Inventory WHERE ID = ? OR name LIKE ? OR type LIKE ?";
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
                    InventoryItem inventoryItem = new InventoryItem(
                            rs.getInt("ID"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("quantity")
                    );
                    inventoryItems.add(inventoryItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        supplyID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        supplyName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        supplyType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        inventoryTableView.setItems(inventoryItems);
    }

    public void showInventory() {
        Database database = Database.getInstance();
        ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList();
        String query = "SELECT * FROM Inventory ORDER BY ID";
        Statement st;
        ResultSet rs;

        try {
            Connection connection = database.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);

            InventoryItem inventoryItem;
            while (rs.next()) {
                inventoryItem = new InventoryItem(rs.getInt("ID"), rs.getString("name"),
                        rs.getString("type"), rs.getInt("quantity"));
                inventoryItems.add(inventoryItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        supplyID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        supplyName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        supplyType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        inventoryTableView.setItems(inventoryItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showInventory();
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
