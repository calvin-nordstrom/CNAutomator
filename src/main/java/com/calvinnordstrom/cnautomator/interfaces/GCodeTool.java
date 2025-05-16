package com.calvinnordstrom.cnautomator.interfaces;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public interface GCodeTool {
    String getTitle();

    StringProperty stringProperty();

    Node getView();
}
