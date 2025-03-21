package com.calvinnordstrom.passrepeater.passrepeater;

import com.calvinnordstrom.passrepeater.model.StringModifier;
import com.calvinnordstrom.passrepeater.util.Axis;
import com.calvinnordstrom.passrepeater.util.FileOperations;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PassRepeater implements StringModifier {
    private final Serializer serializer = new Serializer();
    private final PassRepeaterCommand command;
    private final StringProperty repeatedText = new SimpleStringProperty("");

    public PassRepeater() {
        command = serializer.load();

        attachListeners();
        execute();
    }

    public void execute() {
        StringBuilder repeatedText = new StringBuilder();
        Axis axis = command.getAxis();
        BigDecimal pos = new BigDecimal(Double.toString(command.getInitialPos())).setScale(4, RoundingMode.HALF_UP);
        BigDecimal finalPos = new BigDecimal(Double.toString(command.getFinalPos())).setScale(4, RoundingMode.HALF_UP);
        BigDecimal increment = new BigDecimal(Double.toString(command.getIncrement())).setScale(4, RoundingMode.HALF_UP);

        String textBefore = command.getTextBefore();
        if (!textBefore.isEmpty()) {
            repeatedText.append(textBefore).append("\n\n");
        }

        repeatedText.append(axis).append(pos).append("\n\n");

        while (true) {
            repeatedText.append(command.getFirstPass());

            BigDecimal nextPos = pos.add(increment).setScale(4, RoundingMode.HALF_UP);
            if ((increment.compareTo(BigDecimal.ZERO) > 0 && nextPos.compareTo(finalPos) <= 0) ||
                    (increment.compareTo(BigDecimal.ZERO) < 0 && nextPos.compareTo(finalPos) >= 0)) {
                pos = nextPos;
                repeatedText.append("\n").append(axis).append(pos).append("\n\n");
            } else {
                break;
            }

            repeatedText.append(command.getSecondPass());

            nextPos = pos.add(increment).setScale(4, RoundingMode.HALF_UP);
            if ((increment.compareTo(BigDecimal.ZERO) > 0 && nextPos.compareTo(finalPos) <= 0) ||
                    (increment.compareTo(BigDecimal.ZERO) < 0 && nextPos.compareTo(finalPos) >= 0)) {
                pos = nextPos;
                repeatedText.append("\n").append(axis).append(pos).append("\n\n");
            } else {
                break;
            }
        }

        String textAfter = command.getTextAfter();
        if (!textAfter.isEmpty()) {
            repeatedText.append("\n\n").append(textAfter);
        }

        this.repeatedText.set(repeatedText.toString());
    }

    private void attachListeners() {
        ChangeListener<Object> listener = (_, _, _) -> {
            execute();
            serializer.save();
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
        command.setTextBefore("");
        command.setTextAfter("");
        command.setAxis(Axis.X);
        command.setInitialPos(0.0);
        command.setFinalPos(0.0);
        command.setIncrement(0.0);
        command.setFirstPass("");
        command.setSecondPass("");
    }

    public void setCommandTextBefore(String value) {
        command.setTextBefore(value);
    }

    public void setCommandTextAfter(String value) {
        command.setTextAfter(value);
    }

    public void setCommandAxis(Axis value) {
        command.setAxis(value);
    }

    public void setCommandInitialPos(double value) {
        command.setInitialPos(value);
    }

    public void setCommandFinalPos(double value) {
        command.setFinalPos(value);
    }

    public void setCommandIncrement(double value) {
        command.setIncrement(value);
    }

    public void setCommandFirstPass(String value) {
        command.setFirstPass(value);
    }

    public void setCommandSecondPass(String value) {
        command.setSecondPass(value);
    }

    public PassRepeaterCommand getRepeatCommand() {
        return command;
    }

    @Override
    public StringProperty stringProperty() {
        return repeatedText;
    }

    private class Serializer {
        private final File directory = new File(System.getenv("APPDATA"), "PassRepeater");
        private final File file = new File(directory, "passRepeatCommand.ser");

        public Serializer() {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        public void save() {
            try {
                FileOperations.serialize(command, file);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        public PassRepeaterCommand load() {
            PassRepeaterCommand command;
            try {
                command = FileOperations.deserialize(file, PassRepeaterCommand.class);
            } catch (IOException | ClassNotFoundException e) {
                command = new PassRepeaterCommand();
            }
            return command;
        }
    }
}
