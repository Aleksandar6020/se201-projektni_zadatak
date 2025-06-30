package com.example.se201projektni_zadatakaleksandarrozkov6020.pane;

import com.example.se201projektni_zadatakaleksandarrozkov6020.Main;
import com.example.se201projektni_zadatakaleksandarrozkov6020.classes.RealEstateModel;
import com.example.se201projektni_zadatakaleksandarrozkov6020.classes.WishModel;
import com.example.se201projektni_zadatakaleksandarrozkov6020.server.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchPane {

    private final ObservableList<RealEstateModel> realEstateResults = FXCollections.observableArrayList();
    private final ObservableList<WishModel> wishResults = FXCollections.observableArrayList();
    private final TableView<RealEstateModel> realEstateTable = new TableView<>();
    private final TableView<WishModel> wishTable = new TableView<>();

    public VBox getPane() {
        RadioButton buyerRadio = new RadioButton("Buyer");
        RadioButton estateRadio = new RadioButton("Real Estate");
        ToggleGroup group = new ToggleGroup();
        buyerRadio.setToggleGroup(group);
        estateRadio.setToggleGroup(group);
        buyerRadio.setSelected(true);

        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        Button searchButton = new Button("Search");

        HBox topRow = new HBox(10, buyerRadio, estateRadio, idField, searchButton);
        topRow.setAlignment(Pos.CENTER);

        setupRealEstateTable();
        setupWishTable();

        searchButton.setOnAction(e -> {
            realEstateResults.clear();
            wishResults.clear();
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                Main.showAlert("ID field cannot be empty.");
                return;
            }
            if (buyerRadio.isSelected()) {
                searchForBuyer(id);
            } else {
                searchForRealEstate(id);
            }
        });

        VBox layout = new VBox(15, topRow, realEstateTable, wishTable);
        layout.setPadding(new Insets(20));
        return layout;
    }

    private void setupRealEstateTable() {
        TableColumn<RealEstateModel, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getReal_estate_id()));

        TableColumn<RealEstateModel, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAddress()));

        TableColumn<RealEstateModel, String> area = new TableColumn<>("Area");
        area.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSquare_meters()));

        TableColumn<RealEstateModel, String> price = new TableColumn<>("Price/Rent");
        price.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPrice_or_rent()));

        TableColumn<RealEstateModel, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getType()));

        realEstateTable.getColumns().addAll(id, address, area, price, type);
        realEstateTable.setItems(realEstateResults);
    }

    private void setupWishTable() {
        TableColumn<WishModel, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getWish_id()));

        TableColumn<WishModel, String> clientId = new TableColumn<>("Client ID");
        clientId.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getClient_id()));

        TableColumn<WishModel, String> min = new TableColumn<>("Min Budget");
        min.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMin_budget()));

        TableColumn<WishModel, String> max = new TableColumn<>("Max Budget");
        max.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMax_budget()));

        TableColumn<WishModel, String> area = new TableColumn<>("Min Area");
        area.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMin_area()));

        TableColumn<WishModel, String> location = new TableColumn<>("Location");
        location.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPreferred_location()));

        TableColumn<WishModel, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getWish_type()));

        wishTable.getColumns().addAll(id, clientId, min, max, area, location, type);
        wishTable.setItems(wishResults);
    }

    private void searchForBuyer(String clientId) {
        try {
            String wishData = Client.Read("wishes");
            String[] wishes = wishData.split("###");
            for (String wishRow : wishes) {
                String[] w = wishRow.split("\\|\\|\\|");
                if (w.length >= 7 && w[1].equals(clientId)) {
                    String type = w[6];
                    String realEstateTable = type.equals("sale") ? "realestate_sale" : "realestate_rent";

                    String estateData = Client.Read(realEstateTable);
                    String[] estates = estateData.split("###");
                    for (String estateRow : estates) {
                        String[] e = estateRow.split("\\|\\|\\|");
                        if (e.length >= 4) {
                            double price = Double.parseDouble(e[3]);
                            double area = Double.parseDouble(e[2]);
                            double minBudget = Double.parseDouble(w[2]);
                            double maxBudget = Double.parseDouble(w[3]);
                            double minArea = Double.parseDouble(w[4]);
                            String location = w[5];

                            if (price >= minBudget && price <= maxBudget && area >= minArea && e[1].contains(location)) {
                                RealEstateModel model = new RealEstateModel(e[0], e[1], e[2], e[3], type);
                                realEstateResults.add(model);
                            }
                        }
                    }
                }
            }
            if (!realEstateResults.isEmpty()) {
                Main.showSuccess("Matching real estate found.");
            } else {
                Main.showAlert("No real estate matches the buyer's wishes.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchForRealEstate(String estateId) {
        try {
            String estate = null;
            String type = null;

            String saleData = Client.Read("realestate_sale");
            for (String row : saleData.split("###")) {
                if (row.startsWith(estateId + "|||")) {
                    estate = row;
                    type = "sale";
                    break;
                }
            }
            if (estate == null) {
                String rentData = Client.Read("realestate_rent");
                for (String row : rentData.split("###")) {
                    if (row.startsWith(estateId + "|||")) {
                        estate = row;
                        type = "rent";
                        break;
                    }
                }
            }
            if (estate == null) {
                Main.showAlert("Real estate with this ID not found.");
                return;
            }

            String[] e = estate.split("\\|\\|\\|");
            double price = Double.parseDouble(e[3]);
            double area = Double.parseDouble(e[2]);
            String address = e[1];

            String wishData = Client.Read("wishes");
            for (String wishRow : wishData.split("###")) {
                String[] w = wishRow.split("\\|\\|\\|");
                if (w.length >= 7 && w[6].equals(type)) {
                    double minBudget = Double.parseDouble(w[2]);
                    double maxBudget = Double.parseDouble(w[3]);
                    double minArea = Double.parseDouble(w[4]);
                    String location = w[5];

                    if (price >= minBudget && price <= maxBudget && area >= minArea && address.contains(location)) {
                        WishModel model = new WishModel(w[0], w[1], w[2], w[3], w[4], w[5], w[6]);
                        wishResults.add(model);
                    }
                }
            }
            if (!wishResults.isEmpty()) {
                Main.showSuccess("Matching buyers and wishes found.");
            } else {
                Main.showAlert("No buyers match this real estate.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


