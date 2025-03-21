module com.calvinnordstrom.passrepeater {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calvinnordstrom.passrepeater to javafx.fxml;
    exports com.calvinnordstrom.passrepeater;
    exports com.calvinnordstrom.passrepeater.passrepeater;
    opens com.calvinnordstrom.passrepeater.passrepeater to javafx.fxml;
    exports com.calvinnordstrom.passrepeater.util;
    opens com.calvinnordstrom.passrepeater.util to javafx.fxml;
}