package com.calvinnordstrom.passrepeater.view.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class StringControl {
    private final StringProperty value = new SimpleStringProperty("");
    private final VBox view = new VBox();
    private final Label label;
    private final TextArea textArea = new TextArea();

    public StringControl(String text) {
        label = new Label(text);

        init();
    }

    private void init() {
        textArea.textProperty().addListener((_, _, newValue) -> {
            value.set(newValue);
        });

        view.getChildren().addAll(label, textArea);
    }

    public void bind(StringProperty value) {
        textArea.textProperty().bind(value);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        textArea.setText(value);
    }

    public StringProperty valueProperty() {
        return value;
    }

    public Node asNode() {
        return view;
    }
}
