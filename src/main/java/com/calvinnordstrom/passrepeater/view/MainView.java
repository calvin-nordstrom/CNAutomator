package com.calvinnordstrom.passrepeater.view;

import com.calvinnordstrom.passrepeater.controller.MainController;
import com.calvinnordstrom.passrepeater.model.Direction;
import com.calvinnordstrom.passrepeater.model.PassRepeater;
import com.calvinnordstrom.passrepeater.model.RepeatCommand;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private final StringControl passOneControl = new StringControl("First pass");
    private final StringControl passTwoControl = new StringControl("Second pass");
//    private PreviewWindow previewWindow;

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

        Button previewButton = new Button("Preview");
        previewButton.setOnMouseClicked(_ -> {
            controller.execute(buildCommand());
//            if (previewWindow == null) {
//                previewWindow = new PreviewWindow(view.getScene().getWindow());
//            }
//            previewWindow.show();
        });
        Button exportButton = new Button("Export");
        HBox repeaterControls = new HBox(previewButton, exportButton);

        view.setCenter(new VBox(tabPane, repeaterControls));
    }

    private Node createRepeatedTextPane() {
        VBox posControls = new VBox(
                directionControl.asNode(),
                initialPosControl.asNode(),
                finalPosControl.asNode(),
                incrementControl.asNode()
        );
        HBox passControls = new HBox(
                passOneControl.asNode(),
                passTwoControl.asNode()
        );

        return new VBox(new HBox(posControls, passControls));
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
//        model.repeatedTextProperty().addListener((_, _, newValue) -> {
//            previewControl.setText(newValue);
//        });

        return new VBox(previewControl.asNode());
    }

    private void initBottom() {

    }

    private RepeatCommand buildCommand() {
        RepeatCommand.Builder builder = new RepeatCommand.Builder();
        builder.textBefore(textBeforeControl.getValue());
        builder.textAfter(textAfterControl.getValue());
        builder.direction(directionControl.getValue());
        builder.initialPos(initialPosControl.getValue());
        builder.finalPos(finalPosControl.getValue());
        builder.increment(incrementControl.getValue());
        builder.firstPass(passOneControl.getValue());
        builder.secondPass(passTwoControl.getValue());
        return builder.build();
    }

    public Node asNode() {
        return view;
    }

//    private class PreviewWindow {
//        private static final String TITLE = "Preview";
//        private final Window owner;
//        private final Stage stage = new Stage();
//        private final Scene scene;
//
//        public PreviewWindow(Window owner) {
//            this.owner = owner;
//
//            TextArea textArea = new TextArea();
//            textArea.textProperty().bind(model.repeatedTextProperty());
//
//            scene = new Scene(new Pane(textArea));
//
//            init();
//        }
//
//        private void init() {
//            stage.initOwner(owner);
//            stage.initModality(Modality.NONE);
//            stage.setTitle(TITLE);
//            stage.setResizable(false);
//            stage.setMinWidth(320);
//            stage.setMinHeight(540);
//        }
//
//        public void show() {
//            if (scene != null) {
//                stage.setScene(scene);
//                stage.sizeToScene();
//            }
//            stage.show();
//        }
//
//        public void hide() {
//            stage.hide();
//            stage.setScene(null);
//        }
//    }
}
