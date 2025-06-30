package com.example.se201projektni_zadatakaleksandarrozkov6020.pane;

import com.example.se201projektni_zadatakaleksandarrozkov6020.Main;
import com.example.se201projektni_zadatakaleksandarrozkov6020.server.Client;
import com.example.se201projektni_zadatakaleksandarrozkov6020.system.ValidationHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddPane {

    public VBox getPane() {
        Text titleClient = new Text("Add New Client");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Client ID (auto-filled)");

        Button createClientButton = new Button("Create Client");
        createClientButton.setOnAction(e -> createClient(nameField, phoneField, emailField, clientIdField));

        Separator separator1 = new Separator();

        Text titleRealEstate = new Text("Add Real Estate");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField areaField = new TextField();
        areaField.setPromptText("Square Meters");

        TextField priceField = new TextField();
        priceField.setPromptText("Price / Monthly Rent");

        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton saleRadio = new RadioButton("Sale");
        RadioButton rentRadio = new RadioButton("Rent");
        saleRadio.setToggleGroup(typeGroup);
        rentRadio.setToggleGroup(typeGroup);
        saleRadio.setSelected(true);

        Button createRealEstateButton = new Button("Create Real Estate");
        createRealEstateButton.setOnAction(e -> createRealEstate(clientIdField, addressField, areaField, priceField, saleRadio));

        Separator separator2 = new Separator();

        Text titleWish = new Text("Add Wish");

        TextField minBudgetField = new TextField();
        minBudgetField.setPromptText("Min Budget");

        TextField maxBudgetField = new TextField();
        maxBudgetField.setPromptText("Max Budget");

        TextField minAreaField = new TextField();
        minAreaField.setPromptText("Min Area");

        TextField locationField = new TextField();
        locationField.setPromptText("Preferred Location");

        ToggleGroup wishGroup = new ToggleGroup();
        RadioButton wishSaleRadio = new RadioButton("Sale");
        RadioButton wishRentRadio = new RadioButton("Rent");
        wishSaleRadio.setToggleGroup(wishGroup);
        wishRentRadio.setToggleGroup(wishGroup);
        wishSaleRadio.setSelected(true);

        Button createWishButton = new Button("Create Wish");
        createWishButton.setOnAction(e -> createWish(clientIdField, minBudgetField, maxBudgetField, minAreaField, locationField, wishSaleRadio));

        VBox layout = new VBox(10,
                titleClient, nameField, phoneField, emailField, createClientButton,
                clientIdField, separator1,
                titleRealEstate, addressField, areaField, priceField,
                saleRadio, rentRadio, createRealEstateButton,
                separator2,
                titleWish, minBudgetField, maxBudgetField, minAreaField, locationField,
                wishSaleRadio, wishRentRadio, createWishButton
        );

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    private void createClient(TextField name, TextField phone, TextField email, TextField clientId) {
        String nameValue = name.getText().trim();
        String phoneValue = phone.getText().trim();
        String emailValue = email.getText().trim();

        if (nameValue.isEmpty() || phoneValue.isEmpty() || emailValue.isEmpty()) {
            Main.showAlert("Please fill in all client fields.");
            return;
        }
        if (!ValidationHelper.isValidPhone(phoneValue)) {
            Main.showAlert("Invalid phone number format.");
            return;
        }
        if (!ValidationHelper.isValidEmail(emailValue)) {
            Main.showAlert("Invalid email format.");
            return;
        }


        nameValue = nameValue.substring(0, 1).toUpperCase() + nameValue.substring(1);

        String data = nameValue + "|||" + phoneValue + "|||" + emailValue;
        try {
            String response = Client.Create("client", data);
            if (response.startsWith("ok:")) {
                clientId.setText(response.substring(3).trim());
                Main.showSuccess("Client created successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Main.showAlert("Failed to create client.");
        }
    }

    private void createRealEstate(TextField clientId, TextField address, TextField area, TextField price, RadioButton isSaleRadio) {
        String clientIdValue = clientId.getText().trim();
        String addressValue = address.getText().trim();
        String areaValue = area.getText().trim();
        String priceValue = price.getText().trim();

        if (clientIdValue.isEmpty() || addressValue.isEmpty() || areaValue.isEmpty() || priceValue.isEmpty()) {
            Main.showAlert("Please fill in all real estate fields.");
            return;
        }
        if (!ValidationHelper.isValidDecimal(areaValue) || !ValidationHelper.isValidDecimal(priceValue)) {
            Main.showAlert("Area and price must be numeric.");
            return;
        }

        String table = isSaleRadio.isSelected() ? "realestate_sale" : "realestate_rent";
        String ownershipTable = isSaleRadio.isSelected() ? "ownership_sale" : "ownership_rent";
        String realEstateData = addressValue + "|||" + areaValue + "|||" + priceValue;

        try {
            String response = Client.Create(table, realEstateData);
            if (response.startsWith("ok:")) {
                String realEstateId = response.substring(3).trim();
                String ownershipData = clientIdValue + "|||" + realEstateId;
                Client.Create(ownershipTable, ownershipData);
                Main.showSuccess("Real estate created successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Main.showAlert("Failed to create real estate.");
        }
    }

    private void createWish(TextField clientId, TextField minBudget, TextField maxBudget, TextField minArea, TextField location, RadioButton isSaleWish) {
        String clientIdValue = clientId.getText().trim();
        String minBudgetValue = minBudget.getText().trim();
        String maxBudgetValue = maxBudget.getText().trim();
        String minAreaValue = minArea.getText().trim();
        String locationValue = location.getText().trim();

        if (clientIdValue.isEmpty() || minBudgetValue.isEmpty() || maxBudgetValue.isEmpty() || minAreaValue.isEmpty() || locationValue.isEmpty()) {
            Main.showAlert("Please fill in all wish fields.");
            return;
        }
        if (!ValidationHelper.isValidDecimal(minBudgetValue) || !ValidationHelper.isValidDecimal(maxBudgetValue) || !ValidationHelper.isValidDecimal(minAreaValue)) {
            Main.showAlert("Min/Max budget and area must be numeric.");
            return;
        }


        String wishType = isSaleWish.isSelected() ? "sale" : "rent";
        String data = clientIdValue + "|||" + minBudgetValue + "|||" + maxBudgetValue
                + "|||" + minAreaValue + "|||" + locationValue + "|||" + wishType;

        try {
            String response = Client.Create("wishes", data);
            if (response.startsWith("ok") || response.startsWith("ok:")) {
                Main.showSuccess("Wish created successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Main.showAlert("Failed to create wish.");
        }
    }
}
