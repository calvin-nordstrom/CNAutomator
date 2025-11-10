package com.calvinnordstrom.cnautomator.passrepeater;

import com.calvinnordstrom.cnautomator.module.Model;
import com.calvinnordstrom.cnautomator.util.Axis;
import com.calvinnordstrom.cnautomator.util.Serializer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PassRepeaterModel extends Model {
    private final Serializer<PassRepeaterCommand> serializer;
    private final PassRepeaterCommand command;
    private final StringProperty outputText = new SimpleStringProperty("");

    public PassRepeaterModel() {
        serializer = new Serializer<>(
                PassRepeaterCommand.class,
                new File(System.getenv("APPDATA"), "CNAutomator"),
                "passRepeatCommand.ser");
        command = serializer.load(new PassRepeaterCommand());

        attachListeners();
        execute();
    }

    @Override
    public String getName() {
        return "Pass Repeater";
    }

    public void execute() {
        StringBuilder result = new StringBuilder();
        Axis axis = command.getAxis();
        BigDecimal pos = new BigDecimal(Double.toString(command.getInitialPos())).setScale(4, RoundingMode.HALF_UP);
        BigDecimal finalPos = new BigDecimal(Double.toString(command.getFinalPos())).setScale(4, RoundingMode.HALF_UP);
        BigDecimal increment = new BigDecimal(Double.toString(command.getIncrement())).setScale(4, RoundingMode.HALF_UP);

        String textBefore = command.getTextBefore();
        if (!textBefore.isEmpty()) {
            result.append(textBefore).append("\n\n");
        }

        result.append(axis).append(pos).append("\n\n");

        while (true) {
            result.append(command.getFirstPass());

            BigDecimal nextPos = pos.add(increment).setScale(4, RoundingMode.HALF_UP);
            if ((increment.compareTo(BigDecimal.ZERO) > 0 && nextPos.compareTo(finalPos) <= 0) ||
                    (increment.compareTo(BigDecimal.ZERO) < 0 && nextPos.compareTo(finalPos) >= 0)) {
                pos = nextPos;
                result.append("\n").append(axis).append(pos).append("\n\n");
            } else {
                break;
            }

            result.append(command.getSecondPass());

            nextPos = pos.add(increment).setScale(4, RoundingMode.HALF_UP);
            if ((increment.compareTo(BigDecimal.ZERO) > 0 && nextPos.compareTo(finalPos) <= 0) ||
                    (increment.compareTo(BigDecimal.ZERO) < 0 && nextPos.compareTo(finalPos) >= 0)) {
                pos = nextPos;
                result.append("\n").append(axis).append(pos).append("\n\n");
            } else {
                break;
            }
        }

        String textAfter = command.getTextAfter();
        if (!textAfter.isEmpty()) {
            result.append("\n\n").append(textAfter);
        }

        this.outputText.set(result.toString());
    }

    private void attachListeners() {
        ChangeListener<Object> listener = (_, _, _) -> {
            execute();
            serializer.save(command);
        };
        command.textBeforeProperty().addListener(listener);
        command.textAfterProperty().addListener(listener);
        command.axisProperty().addListener(listener);
        command.initialPosProperty().addListener(listener);
        command.finalPosProperty().addListener(listener);
        command.incrementProperty().addListener(listener);
        command.firstPassProperty().addListener(listener);
        command.secondPassProperty().addListener(listener);
    }

    public void resetCommand() {
        command.reset();
    }

    public String getOutputText() {
        return outputText.get();
    }

    public void setOutputText(String text) {
        outputText.set(text);
    }

    public StringProperty outputTextProperty() {
        return outputText;
    }

    public PassRepeaterCommand getCommand() {
        return command;
    }
}
