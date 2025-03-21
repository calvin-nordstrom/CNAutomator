package com.calvinnordstrom.passrepeater.view;

import com.calvinnordstrom.passrepeater.model.StringModifier;
import com.calvinnordstrom.passrepeater.passrepeater.PassRepeater;
import com.calvinnordstrom.passrepeater.passrepeater.PassRepeaterView;
import com.calvinnordstrom.passrepeater.view.control.StringControl;
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

import static com.calvinnordstrom.passrepeater.view.ViewUtils.export;

public class MainView {
    private final StringProperty preview = new SimpleStringProperty("");
    private final BorderPane view = new BorderPane();
    private final Map<Tab, StringModifier> tabMap = new HashMap<>();

    public MainView() {
        init();
        initTop();
        initLeft();
        initCenter();
        initRight();
        initBottom();
    }

    private void init() {

    }

    private void initTop() {

    }

    private void initLeft() {

    }

    private void initCenter() {
        PassRepeater passRepeater = new PassRepeater();
        PassRepeaterView passRepeaterView = new PassRepeaterView(passRepeater);
        Tab passRepeaterTab = createTab("Pass Repeater", passRepeater, passRepeaterView.asNode());

        TabPane tabPane = new TabPane(passRepeaterTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            setPreviewModifier(tabMap.get(newValue));
        });

        // Set default preview modifier
        setPreviewModifier(passRepeater);

        view.setCenter(tabPane);
    }

    private Tab createTab(String text, StringModifier modifier, Node content) {
        Tab tab = new Tab(text, content);
        tab.setClosable(false);
        tabMap.put(tab, modifier);
        return tab;
    }

    private void setPreviewModifier(StringModifier modifier) {
        if (modifier != null) {
            preview.set(modifier.stringProperty().get());
            modifier.stringProperty().addListener((_, _, newValue) -> {
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

    private void initBottom() {

    }

    public Node asNode() {
        return view;
    }
}
