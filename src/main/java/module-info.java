module com.calvinnordstrom.passrepeater {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calvinnordstrom.passrepeater to javafx.fxml;
    exports com.calvinnordstrom.passrepeater;
}