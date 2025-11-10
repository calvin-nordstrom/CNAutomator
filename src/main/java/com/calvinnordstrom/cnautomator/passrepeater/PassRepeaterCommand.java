package com.calvinnordstrom.cnautomator.passrepeater;

import com.calvinnordstrom.cnautomator.util.Axis;
import javafx.beans.property.*;
import java.io.*;

public class PassRepeaterCommand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient ObjectProperty<Axis> axis = new SimpleObjectProperty<>(Axis.X);
    private transient DoubleProperty initialPos = new SimpleDoubleProperty(0.0);
    private transient DoubleProperty finalPos = new SimpleDoubleProperty(0.0);
    private transient DoubleProperty increment = new SimpleDoubleProperty(0.0);
    private transient StringProperty firstPass = new SimpleStringProperty("");
    private transient StringProperty secondPass = new SimpleStringProperty("");
    private transient StringProperty textBefore = new SimpleStringProperty("");
    private transient StringProperty textAfter = new SimpleStringProperty("");

    public void reset() {
        setAxis(Axis.X);
        setInitialPos(0.0);
        setFinalPos(0.0);
        setIncrement(0.0);
        setFirstPass("");
        setSecondPass("");
        setTextBefore("");
        setTextAfter("");
    }

    public Axis getAxis() {
        return axis.get();
    }

    public void setAxis(Axis axis) {
        this.axis.set(axis);
    }

    public ObjectProperty<Axis> axisProperty() {
        return axis;
    }

    public double getInitialPos() {
        return initialPos.get();
    }

    public void setInitialPos(double initialPos) {
        this.initialPos.set(initialPos);
    }

    public DoubleProperty initialPosProperty() {
        return initialPos;
    }

    public double getFinalPos() {
        return finalPos.get();
    }

    public void setFinalPos(double finalPos) {
        this.finalPos.set(finalPos);
    }

    public DoubleProperty finalPosProperty() {
        return finalPos;
    }

    public double getIncrement() {
        return increment.get();
    }

    public void setIncrement(double increment) {
        this.increment.set(increment);
    }

    public DoubleProperty incrementProperty() {
        return increment;
    }

    public String getFirstPass() {
        return firstPass.get();
    }

    public void setFirstPass(String firstPass) {
        this.firstPass.set(firstPass);
    }

    public StringProperty firstPassProperty() {
        return firstPass;
    }

    public String getSecondPass() {
        return secondPass.get();
    }

    public void setSecondPass(String secondPass) {
        this.secondPass.set(secondPass);
    }

    public StringProperty secondPassProperty() {
        return secondPass;
    }

    public String getTextBefore() {
        return textBefore.get();
    }

    public void setTextBefore(String textBefore) {
        this.textBefore.set(textBefore);
    }

    public StringProperty textBeforeProperty() {
        return textBefore;
    }

    public String getTextAfter() {
        return textAfter.get();
    }

    public void setTextAfter(String textAfter) {
        this.textAfter.set(textAfter);
    }

    public StringProperty textAfterProperty() {
        return textAfter;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(axis.get());
        out.writeDouble(initialPos.get());
        out.writeDouble(finalPos.get());
        out.writeDouble(increment.get());
        out.writeObject(firstPass.get());
        out.writeObject(secondPass.get());
        out.writeObject(textBefore.get());
        out.writeObject(textAfter.get());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        axis = new SimpleObjectProperty<>((Axis) in.readObject());
        initialPos = new SimpleDoubleProperty(in.readDouble());
        finalPos = new SimpleDoubleProperty(in.readDouble());
        increment = new SimpleDoubleProperty(in.readDouble());
        firstPass = new SimpleStringProperty((String) in.readObject());
        secondPass = new SimpleStringProperty((String) in.readObject());
        textBefore = new SimpleStringProperty((String) in.readObject());
        textAfter = new SimpleStringProperty((String) in.readObject());
    }
}
