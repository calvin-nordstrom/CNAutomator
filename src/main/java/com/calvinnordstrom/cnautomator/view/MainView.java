package com.calvinnordstrom.cnautomator.view;

import com.calvinnordstrom.cnautomator.interfaces.GCodeTool;
import com.calvinnordstrom.cnautomator.passrepeater.PassRepeater;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class MainView {
    private final BorderPane view = new BorderPane();

    public MainView() {
        List<GCodeTool> tools = new ArrayList<>();
        tools.add(new PassRepeater());
        GCodeToolView gCodeToolView = new GCodeToolView(tools);



        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("G-code Tools", gCodeToolView.asNode()));

        view.setCenter(tabPane);
    }

    public Node asNode() {
        return view;
    }
}
