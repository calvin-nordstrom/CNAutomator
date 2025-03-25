package com.calvinnordstrom.cnautomator.model;

import javafx.beans.property.StringProperty;

public interface AutomationTool {
    String getTitle();

    StringProperty stringProperty();
}
