package AdminPOV.Inventory;


import javafx.beans.property.*;

public class InventoryItem {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty type;
    private final IntegerProperty quantity;

    public InventoryItem(int id, String name, String type, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // Add getters for properties
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
