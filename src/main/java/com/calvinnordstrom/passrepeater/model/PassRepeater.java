package com.calvinnordstrom.passrepeater.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PassRepeater {
    private final StringProperty repeatedText = new SimpleStringProperty("");
    private final ModelSerializer modelSerializer;

    public PassRepeater() {
        modelSerializer = new ModelSerializer();
    }

    public void execute(RepeatCommand repeatCommand) {
        StringBuilder repeatedText = new StringBuilder();
        String textBefore = repeatCommand.getTextBefore();
        String textAfter = repeatCommand.getTextAfter();
        Direction direction = repeatCommand.getDirection();
        BigDecimal pos = new BigDecimal(Double.toString(repeatCommand.getInitialPos())).setScale(4, RoundingMode.HALF_UP);
        BigDecimal finalPos = new BigDecimal(Double.toString(repeatCommand.getFinalPos())).setScale(4, RoundingMode.HALF_UP);
        BigDecimal increment = new BigDecimal(Double.toString(repeatCommand.getIncrement())).setScale(4, RoundingMode.HALF_UP);

        if (!textBefore.isEmpty()) {
            repeatedText.append(textBefore).append("\n\n");
        }

        repeatedText.append(direction).append(pos).append("\n\n");

        while (true) {
            repeatedText.append(repeatCommand.getFirstPass());

            BigDecimal nextPos = pos.add(increment).setScale(4, RoundingMode.HALF_UP);
            if ((increment.compareTo(BigDecimal.ZERO) > 0 && nextPos.compareTo(finalPos) <= 0) ||
                    (increment.compareTo(BigDecimal.ZERO) < 0 && nextPos.compareTo(finalPos) >= 0)) {
                pos = nextPos;
                repeatedText.append("\n").append(direction).append(pos).append("\n\n");
            } else {
                break;
            }

            repeatedText.append(repeatCommand.getSecondPass());

            nextPos = pos.add(increment).setScale(4, RoundingMode.HALF_UP);
            if ((increment.compareTo(BigDecimal.ZERO) > 0 && nextPos.compareTo(finalPos) <= 0) ||
                    (increment.compareTo(BigDecimal.ZERO) < 0 && nextPos.compareTo(finalPos) >= 0)) {
                pos = nextPos;
                repeatedText.append("\n").append(direction).append(pos).append("\n\n");
            } else {
                break;
            }
        }

        if (!textAfter.isEmpty()) {
            repeatedText.append("\n\n").append(textAfter);
        }

        repeatedTextProperty().set(repeatedText.toString());
    }

    public String getRepeatedText() {
        return repeatedText.get();
    }

    public StringProperty repeatedTextProperty() {
        return repeatedText;
    }
}
