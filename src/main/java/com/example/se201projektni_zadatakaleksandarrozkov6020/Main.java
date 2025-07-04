package com.example.se201projektni_zadatakaleksandarrozkov6020;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private static ScrollPane scroll = new ScrollPane();

    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new BorderPane();

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button searchButton = new Button("Search");
        Button tableButton = new Button("Table");

        Button[] buttons = {addButton, editButton, searchButton, tableButton};
        for (Button b : buttons) {
            b.setMinWidth(100);
            b.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        HBox topMenu = new HBox(20, addButton, editButton, searchButton, tableButton);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.setPadding(new Insets(10));
        mainPane.setTop(topMenu);

        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        mainPane.setCenter(scroll);

        VBox welcomePane = new VBox(new Text("Welcome!"));
        welcomePane.setAlignment(Pos.CENTER);
        scroll.setContent(welcomePane);

        addButton.setOnAction(e -> System.out.println("Add button clicked"));
        editButton.setOnAction(e -> System.out.println("Edit button clicked"));
        searchButton.setOnAction(e -> System.out.println("Search button clicked"));
        tableButton.setOnAction(e -> System.out.println("Table button clicked"));

        stage.setScene(new Scene(mainPane, 820, 840));
        stage.setTitle("Real Estate App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
