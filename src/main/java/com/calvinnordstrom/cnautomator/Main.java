package com.calvinnordstrom.cnautomator;

import com.calvinnordstrom.cnautomator.model.AutomationTool;
import com.calvinnordstrom.cnautomator.passrepeater.PassRepeater;
import com.calvinnordstrom.cnautomator.view.MainView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    public static final String TITLE = "CNAutomator";
    public static final String VERSION = "1.0";

    @Override
    public void start(Stage stage) {
        List<AutomationTool> tools = new ArrayList<>();
        tools.add(new PassRepeater());

        MainView view = new MainView(tools);

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