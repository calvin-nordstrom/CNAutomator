module com.calvinnordstrom.passrepeater {
    requires javafx.controls;
    requires javafx.fxml;
    requires jnativehook;
    requires java.logging;
    requires javafx.graphics;

    exports com.calvinnordstrom.cnautomator;
    opens com.calvinnordstrom.cnautomator to javafx.fxml;
    exports com.calvinnordstrom.cnautomator.module;
    opens com.calvinnordstrom.cnautomator.module to javafx.fxml;
    exports com.calvinnordstrom.cnautomator.view;
    opens com.calvinnordstrom.cnautomator.view to javafx.fxml;
}