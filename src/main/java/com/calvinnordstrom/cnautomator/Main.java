package com.calvinnordstrom.cnautomator;

import com.calvinnordstrom.cnautomator.view.MainView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static final String TITLE = "CNAutomator";
    public static final String VERSION = "1.0";

    @Override
    public void start(Stage stage) {
        MainView view = new MainView();

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
}