package com.calvinnordstrom.cnautomator.passrepeater;

import com.calvinnordstrom.cnautomator.module.View;
import com.calvinnordstrom.cnautomator.util.Axis;
import com.calvinnordstrom.cnautomator.view.control.DoubleControl;
import com.calvinnordstrom.cnautomator.view.control.SelectControl;
import com.calvinnordstrom.cnautomator.view.control.StringControl;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import static com.calvinnordstrom.cnautomator.view.ViewUtils.addStyleClass;
import static com.calvinnordstrom.cnautomator.view.ViewUtils.exportText;

public class PassRepeaterView extends View<PassRepeaterModel> {
    private final BorderPane view = new BorderPane();
    private final SelectControl<Axis> axisControl = new SelectControl<>("Axis:",
            FXCollections.observableArrayList(Axis.X, Axis.Y, Axis.Z));
    private final DoubleControl initialPosControl = new DoubleControl("Initial position:");
    private final DoubleControl finalPosControl = new DoubleControl("Final position:");
    private final DoubleControl incrementControl = new DoubleControl("Increment:");
    private final StringControl firstPassControl = new StringControl("First pass:");
    private final StringControl secondPassControl = new StringControl("Second pass:");
    private final StringControl textBeforeControl = new StringControl("Text before:");
    private final StringControl textAfterControl = new StringControl("Text after:");
    private final StringControl previewControl = new StringControl("Preview:");

    public PassRepeaterView(PassRepeaterModel model) {
        super(model);

        init();
        initControlPane();
        initPreviewPane();
    }

    private void init() {
        resetInputs();
        setInputListeners();
    }

    private void initControlPane() {
        BorderPane controlPane = new BorderPane();

        BorderPane passControlPane = new BorderPane();
        VBox posControls = new VBox(
                axisControl.asNode(),
                initialPosControl.asNode(),
                finalPosControl.asNode(),
                incrementControl.asNode()
        );
        addStyleClass(posControls, "pr-pos-controls");
        passControlPane.setLeft(posControls);

        HBox.setHgrow(firstPassControl.asNode(), Priority.ALWAYS);
        HBox.setHgrow(secondPassControl.asNode(), Priority.ALWAYS);
        HBox passControls = new HBox(
                firstPassControl.asNode(),
                secondPassControl.asNode()
        );
        addStyleClass(passControls, "pr-pass-controls");
        passControlPane.setCenter(passControls);

        BorderPane staticTextPane = new BorderPane();
        HBox.setHgrow(textBeforeControl.asNode(), Priority.ALWAYS);
        HBox.setHgrow(textAfterControl.asNode(), Priority.ALWAYS);
        HBox staticTextControls = new HBox(
                textBeforeControl.asNode(),
                textAfterControl.asNode()
        );
        addStyleClass(staticTextControls, "pr-static-text-controls");
        staticTextPane.setCenter(staticTextControls);

        Tab passControlTab = new Tab("Pass Controls", passControlPane);
        passControlTab.setClosable(false);
        Tab staticTextTab = new Tab("Static Text", staticTextPane);
        staticTextTab.setClosable(false);
        TabPane tabPane = new TabPane(passControlTab, staticTextTab);
        controlPane.setCenter(tabPane);

        Button clearButton = new Button("Clear");
        clearButton.setOnMouseClicked(_ -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Clear all input?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(_ -> {
                        model.resetCommand();
                        resetInputs();
                    });
        });
        BorderPane controlPaneBottom = new BorderPane(clearButton);
        addStyleClass(controlPaneBottom, "pr-control-pane-bottom");
        controlPane.setBottom(controlPaneBottom);

        view.setCenter(controlPane);
    }

    private void initPreviewPane() {
        BorderPane previewPane = new BorderPane();

        BorderPane previewControlPane = new BorderPane();
        previewControlPane.setCenter(previewControl.asNode());
        addStyleClass(previewControlPane, "pr-preview-control-pane");
        previewPane.setCenter(previewControlPane);

        Button copyButton = new Button("Copy");
        copyButton.setOnMouseClicked(_ -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(model.getOutputText());
            Clipboard.getSystemClipboard().setContent(content);
        });

        Button exportButton = new Button("Export");
        exportButton.setOnMouseClicked(_ -> {
            exportText(model.getOutputText(), view.getScene().getWindow());
        });

        HBox previewPaneBottomControls = new HBox(copyButton, exportButton);
        addStyleClass(previewPaneBottomControls, "pr-preview-pane-bottom-controls");
        BorderPane previewPaneBottom = new BorderPane(previewPaneBottomControls);
        addStyleClass(previewPaneBottom, "pr-preview-pane-bottom");
        previewPane.setBottom(previewPaneBottom);

        view.setRight(previewPane);
    }

    private void resetInputs() {
        PassRepeaterCommand command = model.getCommand();
        axisControl.setValue(command.getAxis());
        initialPosControl.setValue(command.getInitialPos());
        finalPosControl.setValue(command.getFinalPos());
        incrementControl.setValue(command.getIncrement());
        firstPassControl.setValue(command.getFirstPass());
        secondPassControl.setValue(command.getSecondPass());
        textBeforeControl.setValue(command.getTextBefore());
        textAfterControl.setValue(command.getTextAfter());
    }

    private void setInputListeners() {
        PassRepeaterCommand command = model.getCommand();
        axisControl.valueProperty().addListener((_, _, newValue) -> command.setAxis(newValue));
        initialPosControl.valueProperty().addListener((_, _, newValue) -> command.setInitialPos((double) newValue));
        finalPosControl.valueProperty().addListener((_, _, newValue) -> command.setFinalPos((double) newValue));
        incrementControl.valueProperty().addListener((_, _, newValue) -> command.setIncrement((double) newValue));
        firstPassControl.valueProperty().addListener((_, _, newValue) -> command.setFirstPass(newValue));
        secondPassControl.valueProperty().addListener((_, _, newValue) -> command.setSecondPass(newValue));
        textBeforeControl.valueProperty().addListener((_, _, newValue) -> command.setTextBefore(newValue));
        textAfterControl.valueProperty().addListener((_, _, newValue) -> command.setTextAfter(newValue));
        previewControl.bind(model.outputTextProperty());
    }

    @Override
    public Node asNode() {
        return view;
    }
}
