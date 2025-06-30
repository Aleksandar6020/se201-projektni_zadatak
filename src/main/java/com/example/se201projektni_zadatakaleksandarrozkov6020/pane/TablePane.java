package com.example.se201projektni_zadatakaleksandarrozkov6020.pane;

import com.example.se201projektni_zadatakaleksandarrozkov6020.Main;
import com.example.se201projektni_zadatakaleksandarrozkov6020.classes.ClientModel;
import com.example.se201projektni_zadatakaleksandarrozkov6020.server.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class TablePane {

    private final ObservableList<ClientModel> clientList = FXCollections.observableArrayList();
    private final TableView<ClientModel> table = new TableView<>();

    public VBox getPane() {
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone number");

        Button searchButton = new Button("Search");

        HBox searchBox = new HBox(10, phoneField, searchButton);
        searchBox.setAlignment(Pos.CENTER);

        TableColumn<ClientModel, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getClient_id()));

        TableColumn<ClientModel, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));

        TableColumn<ClientModel, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPhone()));

        TableColumn<ClientModel, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));

        table.getColumns().addAll(idCol, nameCol, phoneCol, emailCol);
        table.setItems(clientList);

        searchButton.setOnAction(e -> {
            String phone = phoneField.getText().trim();
            if (phone.isEmpty()) {
                loadAllClients();
                Main.showSuccess("Loaded all clients.");
            } else if (!phone.startsWith("+")) {
                Main.showAlert("Phone number must start with '+'.");
            } else {
                loadClientByPhone(phone);
            }
        });

        loadAllClients();

        VBox layout = new VBox(15, searchBox, table);
        layout.setPadding(new Insets(20));
        return layout;
    }

    private void loadAllClients() {
        try {
            clientList.clear();
            String response = Client.Read("client");
            String[] rows = response.split("###");
            for (String row : rows) {
                String[] fields = row.split("\\|\\|\\|");
                if (fields.length >= 4) {
                    ClientModel c = new ClientModel(fields[0], fields[1], fields[2], fields[3]);
                    clientList.add(c);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadClientByPhone(String phone) {
        try {
            clientList.clear();
            String response = Client.Read("client");
            boolean found = false;
            for (String row : response.split("###")) {
                String[] fields = row.split("\\|\\|\\|");
                if (fields.length >= 4 && fields[3].equals(phone)) {
                    clientList.add(new ClientModel(fields[0], fields[1], fields[2], fields[3]));
                    found = true;
                }
            }
            if (found) {
                Main.showSuccess("Client found.");
            } else {
                Main.showAlert("No client found with that phone number.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}