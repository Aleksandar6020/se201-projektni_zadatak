package com.example.se201projektni_zadatakaleksandarrozkov6020.pane;

import com.example.se201projektni_zadatakaleksandarrozkov6020.Main;
import com.example.se201projektni_zadatakaleksandarrozkov6020.server.Client;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class EditPane {

    public VBox getPane() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(400);
        layout.setStyle("-fx-padding: 20");

        layout.getChildren().addAll(
                getClientSection(),
                new Separator(),
                getWishSection(),
                new Separator(),
                getRealEstateSection()
        );

        return layout;
    }

    private VBox getClientSection() {
        Label title = new Label("Client (ID is required)");

        TextField idField = new TextField();
        idField.setPromptText("Client ID");

        TextField name = new TextField();
        name.setPromptText("Name");

        TextField phone = new TextField();
        phone.setPromptText("Phone");

        TextField email = new TextField();
        email.setPromptText("Email");

        Button update = new Button("Update Client");
        update.setOnAction(e -> {
            String id = idField.getText().trim();
            String nameVal = name.getText().trim();
            String phoneVal = phone.getText().trim();
            String emailVal = email.getText().trim();

            if (id.isEmpty()) {
                Main.showAlert("Client ID is required.");
                return;
            }

            if (!nameVal.isEmpty()) {
                nameVal = nameVal.substring(0, 1).toUpperCase() + nameVal.substring(1);
            }
            if (!phoneVal.isEmpty() && !phoneVal.startsWith("+")) {
                Main.showAlert("Phone number must start with '+'.");
                return;
            }
            if (!emailVal.isEmpty() && !emailVal.contains("@")) {
                Main.showAlert("Email must contain '@'.");
                return;
            }

            String condition = id + "|||" + nameVal + "|||" + phoneVal + "|||" + emailVal;
            try {
                Client.Update("client", "client_id", condition);
                Main.showSuccess("Client updated successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button delete = new Button("Delete Client");
        delete.setOnAction(e -> {
            if (idField.getText().isEmpty()) {
                Main.showAlert("Client ID is required to delete.");
                return;
            }
            deleteClientById(idField.getText().trim());
            Main.showSuccess("Client deleted successfully.");
        });

        return new VBox(5, title, idField, name, phone, email, update, delete);
    }

    private VBox getWishSection() {
        Label title = new Label("Wish (ID is required)");

        TextField idField = new TextField();
        idField.setPromptText("Wish ID");

        TextField min = new TextField();
        min.setPromptText("Min Budget");

        TextField max = new TextField();
        max.setPromptText("Max Budget");

        TextField area = new TextField();
        area.setPromptText("Min Area");

        TextField location = new TextField();
        location.setPromptText("Preferred Location");

        ToggleGroup wishGroup = new ToggleGroup();
        RadioButton sale = new RadioButton("Sale");
        RadioButton rent = new RadioButton("Rent");
        sale.setToggleGroup(wishGroup);
        rent.setToggleGroup(wishGroup);
        sale.setSelected(true);

        Button update = new Button("Update Wish");
        update.setOnAction(e -> {
            if (idField.getText().trim().isEmpty()) {
                Main.showAlert("Wish ID is required.");
                return;
            }
            String wishType = sale.isSelected() ? "sale" : "rent";
            String condition = idField.getText().trim() + "|||" + min.getText().trim() + "|||" + max.getText().trim() + "|||" + area.getText().trim() + "|||" + location.getText().trim() + "|||" + wishType;
            try {
                Client.Update("wishes", "wish_id", condition);
                Main.showSuccess("Wish updated successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button delete = new Button("Delete Wish");
        delete.setOnAction(e -> {
            if (idField.getText().trim().isEmpty()) {
                Main.showAlert("Wish ID is required to delete.");
                return;
            }
            try {
                Client.Delete("wishes", "wish_id", idField.getText().trim());
                Main.showSuccess("Wish deleted successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return new VBox(5, title, idField, min, max, area, location, sale, rent, update, delete);
    }

    private VBox getRealEstateSection() {
        Label title = new Label("Real Estate (ID is required)");

        TextField idField = new TextField();
        idField.setPromptText("Real Estate ID");

        TextField address = new TextField();
        address.setPromptText("Address");

        TextField meters = new TextField();
        meters.setPromptText("Square Meters");

        TextField price = new TextField();
        price.setPromptText("Price / Rent");

        ToggleGroup estateGroup = new ToggleGroup();
        RadioButton sale = new RadioButton("Sale");
        RadioButton rent = new RadioButton("Rent");
        sale.setToggleGroup(estateGroup);
        rent.setToggleGroup(estateGroup);
        sale.setSelected(true);

        Button update = new Button("Update Real Estate");
        update.setOnAction(e -> {
            if (idField.getText().trim().isEmpty()) {
                Main.showAlert("Real Estate ID is required.");
                return;
            }
            String table = sale.isSelected() ? "realestate_sale" : "realestate_rent";
            String condition = idField.getText().trim() + "|||" + address.getText().trim() + "|||" + meters.getText().trim() + "|||" + price.getText().trim();
            try {
                Client.Update(table, "real_estate_id", condition);
                Main.showSuccess("Real estate updated successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button delete = new Button("Delete Real Estate");
        delete.setOnAction(e -> {
            if (idField.getText().trim().isEmpty()) {
                Main.showAlert("Real Estate ID is required to delete.");
                return;
            }
            String table = sale.isSelected() ? "realestate_sale" : "realestate_rent";
            String ownership = sale.isSelected() ? "ownership_sale" : "ownership_rent";

            try {
                Client.Delete(ownership, "real_estate_id", idField.getText().trim());
                Client.Delete(table, "real_estate_id", idField.getText().trim());
                Main.showSuccess("Real estate deleted successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return new VBox(5, title, idField, address, meters, price, sale, rent, update, delete);
    }

    private void deleteClientById(String id) {
        try {
            List<String> saleToDelete = new ArrayList<>();
            String saleData = Client.Read("ownership_sale");
            String[] saleLinks = saleData.split("###");
            for (String link : saleLinks) {
                String[] parts = link.split("\\|\\|\\|");
                if (parts.length >= 2 && parts[0].equals(id)) {
                    saleToDelete.add(parts[1]);
                }
            }

            List<String> rentToDelete = new ArrayList<>();
            String rentData = Client.Read("ownership_rent");
            String[] rentLinks = rentData.split("###");
            for (String link : rentLinks) {
                String[] parts = link.split("\\|\\|\\|");
                if (parts.length >= 2 && parts[0].equals(id)) {
                    rentToDelete.add(parts[1]);
                }
            }

            Client.Delete("ownership_sale", "owner_id", id);
            Client.Delete("ownership_rent", "owner_id", id);

            for (String realEstateId : saleToDelete) {
                Client.Delete("realestate_sale", "real_estate_id", realEstateId);
            }
            for (String realEstateId : rentToDelete) {
                Client.Delete("realestate_rent", "real_estate_id", realEstateId);
            }

            Client.Delete("wishes", "client_id", id);
            Client.Delete("client", "client_id", id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


