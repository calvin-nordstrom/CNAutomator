package com.calvinnordstrom.passrepeater.view.control;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

import java.util.function.UnaryOperator;

public class DoubleControl {
    private final DoubleProperty value = new SimpleDoubleProperty();
    private final VBox view = new VBox();
    private final Label label;
    private final TextField textField = new TextField();
    private static final UnaryOperator<TextFormatter.Change> FILTER = t -> {
        String newText = t.getControlNewText();

        if (newText.isEmpty()) {
            return t;
        }

        if (newText.matches("-?\\d*\\.?\\d*")) {
            return t;
        }

        return null;
    };


    public DoubleControl(String text) {
        label = new Label(text);

        init();
    }

    private void init() {
        textField.setTextFormatter(new TextFormatter<>(FILTER));
        textField.textProperty().addListener((_, _, newValue) -> {
            if (newValue.isEmpty() || newValue.equals("-") || newValue.equals(".")) {
                value.set(0);
            } else {
                try {
                    value.set(Double.parseDouble(newValue));
                } catch (NumberFormatException e) {
                    value.set(0);
                }
            }
        });
        view.getChildren().addAll(label, textField);

        textField.getStyleClass().add("double-control_text-field");
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(double value) {
        textField.setText(String.valueOf(value));
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public Node asNode() {
        return view;
    }
}
