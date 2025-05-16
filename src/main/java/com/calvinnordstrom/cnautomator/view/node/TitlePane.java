package com.calvinnordstrom.cnautomator.view.node;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TitlePane {
    private final BorderPane view;

    public TitlePane(String text) {
        Label label = new Label(text);
        view = new BorderPane(label);

        label.getStyleClass().add("tool-title");
        view.getStyleClass().add("tool-title-pane");
    }

    public Node asNode() {
        return view;
    }
}
