module com.calvinnordstrom.passrepeater {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calvinnordstrom.passrepeater to javafx.fxml;
    exports com.calvinnordstrom.passrepeater;
    exports com.calvinnordstrom.passrepeater.model;
    opens com.calvinnordstrom.passrepeater.model to javafx.fxml;
}