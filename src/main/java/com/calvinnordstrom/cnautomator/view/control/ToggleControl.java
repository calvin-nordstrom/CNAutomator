package com.calvinnordstrom.cnautomator.view.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class ToggleControl {
    private final BooleanProperty value = new SimpleBooleanProperty(false);
    private final HBox view = new HBox(10);
    private final CheckBox checkBox;

    public ToggleControl(String text) {
        checkBox = new CheckBox(text);

        init();
    }

    private void init() {
        checkBox.selectedProperty().bindBidirectional(value);
        view.getChildren().addAll(checkBox);

        checkBox.getStyleClass().add("toggle-control-check-box");
    }

    public boolean getValue() {
        return value.get();
    }

    public void setValue(boolean value) {
        checkBox.setSelected(value);
    }

    public BooleanProperty valueProperty() {
        return value;
    }

    public Node asNode() {
        return view;
    }
}
