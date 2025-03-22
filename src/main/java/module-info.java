module com.calvinnordstrom.passrepeater {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calvinnordstrom.cnautomator to javafx.fxml;
    exports com.calvinnordstrom.cnautomator;
    exports com.calvinnordstrom.cnautomator.passrepeater;
    opens com.calvinnordstrom.cnautomator.passrepeater to javafx.fxml;
    exports com.calvinnordstrom.cnautomator.util;
    opens com.calvinnordstrom.cnautomator.util to javafx.fxml;
}