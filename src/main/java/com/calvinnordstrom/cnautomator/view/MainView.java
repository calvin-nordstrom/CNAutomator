package com.calvinnordstrom.cnautomator.view;

import com.calvinnordstrom.cnautomator.model.AutomationTool;
import com.calvinnordstrom.cnautomator.view.control.StringControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.calvinnordstrom.cnautomator.view.ViewUtils.export;

public class MainView {
    private final List<AutomationTool> tools;
    private final BorderPane view = new BorderPane();
    private final Map<Tab, AutomationTool> tabMap;
    private final StringProperty preview = new SimpleStringProperty("");

    public MainView(List<AutomationTool> tools) {
        this.tools = tools;
        tabMap = createTabMap();

        initCenter();
        initRight();
    }

    private Map<Tab, AutomationTool> createTabMap() {
        Map<Tab, AutomationTool> map = new HashMap<>();
        for (AutomationTool tool : tools) {
            Tab tab = new Tab(tool.getTitle(), tool.getView());
            tab.setClosable(false);
            map.put(tab, tool);
        }
        return map;
    }

    private void initCenter() {
        TabPane tabPane = new TabPane(tabMap.keySet().toArray(new Tab[0]));
        tabPane.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            setPreviewModifier(tabMap.get(newValue));
        });

        setPreviewModifier(tools.getFirst());

        // Set default preview modifier
//        setPreviewModifier(passRepeater);

        view.setCenter(tabPane);
    }

    private Tab createTab(AutomationTool tool, Node content) {
        Tab tab = new Tab(tool.getTitle(), content);
        tab.setClosable(false);
        tabMap.put(tab, tool);
        return tab;
    }

    private void setPreviewModifier(AutomationTool tool) {
        if (tool != null) {
            preview.set(tool.stringProperty().get());
            tool.stringProperty().addListener((_, _, newValue) -> {
                preview.set(newValue);
            });
        }
    }

    private void initRight() {
        view.setRight(createPreviewPane());
    }

    private Node createPreviewPane() {
        StringControl previewControl = new StringControl("Preview:");
        previewControl.bind(preview);

        Button exportButton = new Button("Export");
        exportButton.setOnMouseClicked(_ -> {
            export(preview.get(), view.getScene().getWindow());
        });
        HBox bottom = new HBox(exportButton);
        bottom.setAlignment(Pos.CENTER);

        BorderPane previewPane = new BorderPane();
        previewPane.setCenter(previewControl.asNode());
        previewPane.setBottom(bottom);

        previewControl.asNode().getStyleClass().add("width-300");
        previewPane.getStyleClass().add("preview-pane");
        bottom.getStyleClass().add("preview-pane_bottom");

        return new BorderPane(previewPane);
    }

    public Node asNode() {
        return view;
    }
}
