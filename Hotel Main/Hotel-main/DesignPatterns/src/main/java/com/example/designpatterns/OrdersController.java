package com.example.designpatterns;

import AdminPOV.Inventory.InventoryOrder;
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

public class OrdersController implements Initializable {
    @FXML
    private Button CashBooks;

    @FXML
    private Button FrontDesk;

    @FXML
    private Button Housekeeping;

    @FXML
    private Button Orders;

    @FXML
    private Button Reservations;

    @FXML
    private Button Supplies;

    @FXML
    private Button Tasks;

    @FXML
    private TextField searching;

    @FXML
    private Button searchingButton;

    @FXML
    TableView<InventoryOrder> inventoryOrderTableView;
    @FXML
    TableColumn<InventoryOrder, Integer> orderID;
    @FXML
    TableColumn<InventoryOrder, String> orderName;
    @FXML
    TableColumn<InventoryOrder, String> orderType;
    @FXML
    TableColumn<InventoryOrder, Integer> quantity;
    @FXML TextField ordername;
    @FXML TextField type;
    @FXML TextField amount;
    @FXML Button add;
    @FXML
    private void handleSearchButton() {
        String searchText = searching.getText();
        Database database = Database.getInstance();
        ObservableList<InventoryOrder> inventoryOrders = FXCollections.observableArrayList();
        String query = "SELECT * FROM InventoryOrders WHERE orderID = ? OR orderName LIKE ? OR orderType LIKE ?";
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
                    InventoryOrder inventoryOrder = new InventoryOrder(
                            rs.getInt("orderID"),
                            rs.getString("orderName"),
                            rs.getString("orderType"),
                            rs.getInt("quantity")
                    );
                    inventoryOrders.add(inventoryOrder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        orderName.setCellValueFactory(cellData -> cellData.getValue().nameOrderProperty());
        orderType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        inventoryOrderTableView.setItems(inventoryOrders);
    }

    public void showInventoryOrders() {
        Database database = Database.getInstance();
        ObservableList<InventoryOrder> inventoryOrders = FXCollections.observableArrayList();
        String query = "SELECT * FROM InventoryOrders ORDER BY orderID";
        Statement st;
        ResultSet rs;
        try {
            Connection connection = database.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);

            InventoryOrder inventoryOrder;
            while (rs.next()) {
                inventoryOrder = new InventoryOrder(rs.getInt("orderID"), rs.getString("orderName"),
                        rs.getString("orderType"), rs.getInt("quantity"));
                inventoryOrders.add(inventoryOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        orderName.setCellValueFactory(cellData -> cellData.getValue().nameOrderProperty());
        orderType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        inventoryOrderTableView.setItems(inventoryOrders);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add.setOnAction(actionEvent -> {
            String orderName = ordername.getText();
            String orderType = type.getText();
            int quantity = Integer.parseInt(amount.getText());
            Database database = Database.getInstance();
            try {
                String query = "INSERT INTO InventoryOrders (orderName, orderType, quantity) " +
                        "VALUES (?, ?, ?)";
                PreparedStatement statement = database.getConnection().prepareStatement(query);

                statement.setString(1, orderName);
                statement.setString(2, orderType);
                statement.setInt(3, quantity);

                statement.executeUpdate();
                System.out.println("Inventory order inserted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }try {
                String query = "UPDATE inventory SET quantity = quantity +" + quantity  + "WHERE name = '" + ordername + "'";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        showInventoryOrders();
        searchingButton.setOnAction(actionEvent -> {
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
