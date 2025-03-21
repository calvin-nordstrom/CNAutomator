package com.calvinnordstrom.passrepeater.view;

import com.calvinnordstrom.passrepeater.controller.MainController;
import com.calvinnordstrom.passrepeater.model.Direction;
import com.calvinnordstrom.passrepeater.model.PassRepeater;
import com.calvinnordstrom.passrepeater.model.PassRepeaterCommand;
import com.calvinnordstrom.passrepeater.view.control.DoubleControl;
import com.calvinnordstrom.passrepeater.view.control.SelectControl;
import com.calvinnordstrom.passrepeater.view.control.StringControl;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
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
    private final StringControl firstPassControl = new StringControl("First pass:");
    private final StringControl secondPassControl = new StringControl("Second pass:");

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
        textBeforeControl.valueProperty().addListener((_, _, newValue) -> {
            command.setTextBefore(newValue);
        });
        textAfterControl.setValue(command.getTextAfter());
        textAfterControl.valueProperty().addListener((_, _, newValue) -> {
            command.setTextAfter(newValue);
        });
        directionControl.setValue(command.getDirection());
        directionControl.valueProperty().addListener((_, _, newValue) -> {
            command.setDirection(newValue);
        });
        initialPosControl.setValue(command.getInitialPos());
        initialPosControl.valueProperty().addListener((_, _, newValue) -> {
            command.setInitialPos((double) newValue);
        });
        finalPosControl.setValue(command.getFinalPos());
        finalPosControl.valueProperty().addListener((_, _, newValue) -> {
            command.setFinalPos((double) newValue);
        });
        incrementControl.setValue(command.getIncrement());
        incrementControl.valueProperty().addListener((_, _, newValue) -> {
            command.setIncrement((double) newValue);
        });
        firstPassControl.setValue(command.getFirstPass());
        firstPassControl.valueProperty().addListener((_, _, newValue) -> {
            command.setFirstPass(newValue);
        });
        secondPassControl.setValue(command.getSecondPass());
        secondPassControl.valueProperty().addListener((_, _, newValue) -> {
            command.setSecondPass(newValue);
        });
//        command.textBeforeProperty().bind(textBeforeControl.valueProperty());
//        command.textAfterProperty().bind(textAfterControl.valueProperty());
//        command.directionProperty().bind(directionControl.valueProperty());
//        command.initialPosProperty().bind(initialPosControl.valueProperty());
//        command.finalPosProperty().bind(finalPosControl.valueProperty());
//        command.incrementProperty().bind(incrementControl.valueProperty());
//        command.firstPassProperty().bind(firstPassControl.valueProperty());
//        command.secondPassProperty().bind(secondPassControl.valueProperty());
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

        Button clearButton = new Button("Clear");
        clearButton.setOnMouseClicked(_ -> {
            resetInputs();
        });
        HBox controlPane = new HBox(clearButton);
        controlPane.setAlignment(Pos.CENTER);

        BorderPane center = new BorderPane();
        center.setCenter(tabPane);
        center.setBottom(controlPane);

        view.setCenter(center);

        tabPane.getStyleClass().add("pass-repeater_tab-pane");
        controlPane.getStyleClass().add("pass-repeater_control-pane");
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

        posControls.getStyleClass().add("pos-controls");
        passControls.getStyleClass().add("pass-controls");

        return new HBox(posControls, passControls);
    }

    private Node createStaticTextPane() {
        HBox staticTextControls = new HBox(
                textBeforeControl.asNode(),
                textAfterControl.asNode()
        );

        staticTextControls.getStyleClass().add("static-text-controls");

        return staticTextControls;
    }

    private void initRight() {
        view.setRight(createPreviewPane());
    }

    private Node createPreviewPane() {
        StringControl previewControl = new StringControl("Preview:");
        previewControl.bind(model.repeatedTextProperty());

        Button exportButton = new Button("Export");
        exportButton.setOnMouseClicked(_ -> {
            export(model.getRepeatedText(), view.getScene().getWindow());
        });
        HBox bottom = new HBox(exportButton);
        bottom.setAlignment(Pos.CENTER);

        BorderPane previewPane = new BorderPane();
        previewPane.setCenter(previewControl.asNode());
        previewPane.setBottom(bottom);

        previewControl.asNode().getStyleClass().add("width-300");
        previewPane.getStyleClass().add("preview-pane");
        bottom.getStyleClass().add("preview-pane_bottom");

        return new BorderPane(previewPane);
    }

    private void resetInputs() {
        textBeforeControl.setValue("");
        textAfterControl.setValue("");
        directionControl.setValue(Direction.X);
        initialPosControl.setValue(0.0);
        finalPosControl.setValue(0.0);
        incrementControl.setValue(0.0);
        firstPassControl.setValue("");
        secondPassControl.setValue("");
        controller.resetPassRepeater();
    }

    private static void export(String value, Window owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialFileName("export.txt");

        File fileToSave = fileChooser.showSaveDialog(owner);
        if (fileToSave != null) {
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }

            Path path = Paths.get(filePath);
            try {
                Files.createDirectories(path.getParent());
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    writer.write(value);
                }
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

    private static Pane createHorizontalDivider() {
        Pane divider = new Pane();
        divider.getStyleClass().add("horizontal-divider");
        return divider;
    }

    private static Pane createVerticalDivider() {
        Pane divider = new Pane();
        divider.getStyleClass().add("vertical-divider");
        return divider;
    }
}
