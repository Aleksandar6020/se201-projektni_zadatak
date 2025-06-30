package com.example.se201projektni_zadatakaleksandarrozkov6020;

import com.example.se201projektni_zadatakaleksandarrozkov6020.pane.AddPane;
import com.example.se201projektni_zadatakaleksandarrozkov6020.pane.EditPane;
import com.example.se201projektni_zadatakaleksandarrozkov6020.pane.SearchPane;
import com.example.se201projektni_zadatakaleksandarrozkov6020.pane.TablePane;
import com.example.se201projektni_zadatakaleksandarrozkov6020.server.Server;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        Server.startServer();
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

        addButton.setOnAction(e -> scroll.setContent(new AddPane().getPane()));
        editButton.setOnAction(e -> scroll.setContent(new EditPane().getPane()));
        searchButton.setOnAction(e -> scroll.setContent(new SearchPane().getPane()));
        tableButton.setOnAction(e -> scroll.setContent(new TablePane().getPane()));

        stage.setOnCloseRequest(e -> {
            try {
                Server.stopServer();
            } catch (Exception ex) {
                System.out.println("Error stopping server: " + ex.getMessage());
            }
        });

        stage.setScene(new Scene(mainPane, 820, 840));
        stage.setTitle("Real Estate App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
