package com.calvinnordstrom.cnautomator.view;

import com.calvinnordstrom.cnautomator.model.AutomationTool;
import com.calvinnordstrom.cnautomator.passrepeater.PassRepeater;
import com.calvinnordstrom.cnautomator.passrepeater.PassRepeaterView;
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
import java.util.Map;

import static com.calvinnordstrom.cnautomator.view.ViewUtils.export;

public class MainView {
    private final StringProperty preview = new SimpleStringProperty("");
    private final BorderPane view = new BorderPane();
    private final Map<Tab, AutomationTool> tabMap = new HashMap<>();

    public MainView() {
        initCenter();
        initRight();
    }

    private void initCenter() {
        PassRepeater passRepeater = new PassRepeater();
        PassRepeaterView passRepeaterView = new PassRepeaterView(passRepeater);
        Tab passRepeaterTab = createTab(passRepeater, passRepeaterView.asNode());

        TabPane tabPane = new TabPane(passRepeaterTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            setPreviewModifier(tabMap.get(newValue));
        });

        // Set default preview modifier
        setPreviewModifier(passRepeater);

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
