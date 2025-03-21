package com.calvinnordstrom.passrepeater;

import com.calvinnordstrom.passrepeater.model.PassRepeater;
import com.calvinnordstrom.passrepeater.view.MainView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static final String TITLE = "PassRepeater";
    public static final String VERSION = "0.0";

    @Override
    public void start(Stage stage) {
        PassRepeater model = new PassRepeater();
        MainView view = new MainView(model);

        Node root = view.asNode();
        Scene scene = new Scene((Parent) root);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle(TITLE + " " + VERSION);
        stage.setMinWidth(960);
        stage.setMinHeight(540);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}