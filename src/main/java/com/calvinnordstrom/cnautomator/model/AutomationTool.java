package com.calvinnordstrom.cnautomator.model;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public interface AutomationTool {
    String getTitle();

    StringProperty stringProperty();

    Node getView();
}
