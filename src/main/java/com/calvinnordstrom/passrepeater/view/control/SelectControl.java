package com.calvinnordstrom.passrepeater.view.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SelectControl<T> {
    private final ObjectProperty<T> value = new SimpleObjectProperty<>();
    private final VBox view = new VBox();
    private final Label label;
    private final ComboBox<T> comboBox = new ComboBox<>();

    public SelectControl(String text, ObservableList<T> value) {
        label = new Label(text);
        comboBox.setItems(value);

        init();
    }

    private void init() {
        comboBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            value.set(newValue);
        });

        view.getChildren().addAll(label, comboBox);
    }

    public T getValue() {
        return value.get();
    }

    public void setValue(T value) {
        comboBox.setValue(value);
    }

    public ObjectProperty<T> valueProperty() {
        return value;
    }

    public Node asNode() {
        return view;
    }
}
