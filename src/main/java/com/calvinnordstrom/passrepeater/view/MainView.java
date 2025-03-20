package com.calvinnordstrom.passrepeater.view;

import com.calvinnordstrom.passrepeater.controller.MainController;
import com.calvinnordstrom.passrepeater.model.Direction;
import com.calvinnordstrom.passrepeater.model.PassRepeaterCommand;
import com.calvinnordstrom.passrepeater.model.PassRepeater;
import com.calvinnordstrom.passrepeater.view.control.DoubleControl;
import com.calvinnordstrom.passrepeater.view.control.SelectControl;
import com.calvinnordstrom.passrepeater.view.control.StringControl;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainView {
    private final PassRepeater model;
    private final MainController controller;
    private final BorderPane view = new BorderPane();
    private final StringControl textBeforeControl = new StringControl("Text before:");
    private final StringControl textAfterControl = new StringControl("Text after:");
    private final SelectControl<Direction> directionControl = new SelectControl<>(
            "Direction:", FXCollections.observableArrayList(
                    Direction.X,
                    Direction.Y,
                    Direction.Z
            )
    );
    private final DoubleControl initialPosControl = new DoubleControl("Initial position:");
    private final DoubleControl finalPosControl = new DoubleControl("Final position:");
    private final DoubleControl incrementControl = new DoubleControl("Increment:");
    private final StringControl firstPassControl = new StringControl("First pass");
    private final StringControl secondPassControl = new StringControl("Second pass");

    public MainView(PassRepeater model, MainController controller) {
        this.model = model;
        this.controller = controller;

        init();
        initTop();
        initLeft();
        initCenter();
        initRight();
        initBottom();
    }

    private void init() {
        PassRepeaterCommand command = model.getRepeatCommand();
        textBeforeControl.setValue(command.getTextBefore());
        textAfterControl.setValue(command.getTextAfter());
        directionControl.setValue(command.getDirection());
        initialPosControl.setValue(command.getInitialPos());
        finalPosControl.setValue(command.getFinalPos());
        incrementControl.setValue(command.getIncrement());
        firstPassControl.setValue(command.getFirstPass());
        secondPassControl.setValue(command.getSecondPass());
        command.textBeforeProperty().bind(textBeforeControl.valueProperty());
        command.textAfterProperty().bind(textAfterControl.valueProperty());
        command.directionProperty().bind(directionControl.valueProperty());
        command.initialPosProperty().bind(initialPosControl.valueProperty());
        command.finalPosProperty().bind(finalPosControl.valueProperty());
        command.incrementProperty().bind(incrementControl.valueProperty());
        command.firstPassProperty().bind(firstPassControl.valueProperty());
        command.secondPassProperty().bind(secondPassControl.valueProperty());
    }

    private void initTop() {

    }

    private void initLeft() {

    }

    private void initCenter() {
        Tab repeatedTextTab = new Tab("Repeated Text", createRepeatedTextPane());
        repeatedTextTab.setClosable(false);
        Tab staticTextTab = new Tab("Static Text", createStaticTextPane());
        staticTextTab.setClosable(false);
        TabPane tabPane = new TabPane(repeatedTextTab, staticTextTab);

        view.setCenter(tabPane);
    }

    private Node createRepeatedTextPane() {
        VBox posControls = new VBox(
                directionControl.asNode(),
                initialPosControl.asNode(),
                finalPosControl.asNode(),
                incrementControl.asNode()
        );
        HBox passControls = new HBox(
                firstPassControl.asNode(),
                secondPassControl.asNode()
        );

        return new HBox(posControls, passControls);
    }

    private Node createStaticTextPane() {
        return new HBox(
                textBeforeControl.asNode(),
                textAfterControl.asNode()
        );
    }

    private void initRight() {
        view.setRight(createPreviewPane());
    }

    private Node createPreviewPane() {
        StringControl previewControl = new StringControl("Preview");
        previewControl.bind(model.repeatedTextProperty());

        Button exportButton = new Button("Export");
        exportButton.setOnMouseClicked(_ -> {
            export(model.getRepeatedText(), view.getScene().getWindow());
        });

        return new VBox(previewControl.asNode(), exportButton);
    }

    private static void export(String value, Window owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File fileToSave = fileChooser.showSaveDialog(owner);
        if (fileToSave != null) {
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }

            Path path = Paths.get(filePath);
            try {
                Files.createDirectories(path.getParent());
                FileWriter writer = new FileWriter(filePath);
                writer.write(value);
                writer.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void initBottom() {

    }

    public Node asNode() {
        return view;
    }
}
