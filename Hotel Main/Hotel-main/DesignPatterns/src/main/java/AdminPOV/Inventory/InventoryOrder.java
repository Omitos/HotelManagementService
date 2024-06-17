package AdminPOV.Inventory;

import javafx.beans.property.*;

public class InventoryOrder {
    private final IntegerProperty id;
    private final StringProperty nameOrder;
    private final StringProperty type;
    private final IntegerProperty quantity;

    public InventoryOrder(int id, String nameOrder, String type, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.nameOrder = new SimpleStringProperty(nameOrder);
        this.type = new SimpleStringProperty(type);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // Add getters for properties
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameOrderProperty() {
        return nameOrder;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}

