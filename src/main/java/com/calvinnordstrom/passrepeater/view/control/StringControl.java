package com.calvinnordstrom.passrepeater.view.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class StringControl {
    private final StringProperty value = new SimpleStringProperty("");
    private final BorderPane view = new BorderPane();
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
        view.setTop(label);
        view.setCenter(textArea);

        textArea.getStyleClass().add("string-control_text-area");
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
