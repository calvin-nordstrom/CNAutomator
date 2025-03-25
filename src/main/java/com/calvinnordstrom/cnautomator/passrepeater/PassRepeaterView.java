package com.calvinnordstrom.cnautomator.passrepeater;

import com.calvinnordstrom.cnautomator.util.Axis;
import com.calvinnordstrom.cnautomator.view.control.DoubleControl;
import com.calvinnordstrom.cnautomator.view.control.SelectControl;
import com.calvinnordstrom.cnautomator.view.control.StringControl;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PassRepeaterView {
    private final PassRepeater passRepeater;
    private final BorderPane view = new BorderPane();
    private final StringControl textBeforeControl = new StringControl("Text before:");
    private final StringControl textAfterControl = new StringControl("Text after:");
    private final SelectControl<Axis> axisControl = new SelectControl<>(
            "Axis:", FXCollections.observableArrayList(
            Axis.X,
            Axis.Y,
            Axis.Z
    ));
    private final DoubleControl initialPosControl = new DoubleControl("Initial position:");
    private final DoubleControl finalPosControl = new DoubleControl("Final position:");
    private final DoubleControl incrementControl = new DoubleControl("Increment:");
    private final StringControl firstPassControl = new StringControl("First pass:");
    private final StringControl secondPassControl = new StringControl("Second pass:");

    public PassRepeaterView(PassRepeater passRepeater) {
        this.passRepeater = passRepeater;

        init();
    }

    private void init() {
        resetInputs();
        setInputListeners();

        Tab passControlsTab = new Tab("Pass Controls", createPassControlsPane());
        passControlsTab.setClosable(false);
        Tab staticTextTab = new Tab("Static Text", createStaticTextPane());
        staticTextTab.setClosable(false);
        TabPane tabPane = new TabPane(passControlsTab, staticTextTab);

        Button clearButton = new Button("Clear");
        clearButton.setOnMouseClicked(_ -> {
            passRepeater.resetCommand();
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

    private Node createPassControlsPane() {
        VBox posControls = new VBox(
                axisControl.asNode(),
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

    private void resetInputs() {
        PassRepeaterCommand command = passRepeater.getRepeatCommand();
        textBeforeControl.setValue(command.getTextBefore());
        textAfterControl.setValue(command.getTextAfter());
        axisControl.setValue(command.getAxis());
        initialPosControl.setValue(command.getInitialPos());
        finalPosControl.setValue(command.getFinalPos());
        incrementControl.setValue(command.getIncrement());
        firstPassControl.setValue(command.getFirstPass());
        secondPassControl.setValue(command.getSecondPass());
    }

    private void setInputListeners() {
        PassRepeaterCommand command = passRepeater.getRepeatCommand();
        textBeforeControl.valueProperty().addListener((_, _, newValue) -> command.setTextBefore(newValue));
        textAfterControl.valueProperty().addListener((_, _, newValue) -> command.setTextAfter(newValue));
        axisControl.valueProperty().addListener((_, _, newValue) -> command.setAxis(newValue));
        initialPosControl.valueProperty().addListener((_, _, newValue) -> command.setInitialPos((double) newValue));
        finalPosControl.valueProperty().addListener((_, _, newValue) -> command.setFinalPos((double) newValue));
        incrementControl.valueProperty().addListener((_, _, newValue) -> command.setIncrement((double) newValue));
        firstPassControl.valueProperty().addListener((_, _, newValue) -> command.setFirstPass(newValue));
        secondPassControl.valueProperty().addListener((_, _, newValue) -> command.setSecondPass(newValue));
    }

    public Node asNode() {
        return view;
    }
}
