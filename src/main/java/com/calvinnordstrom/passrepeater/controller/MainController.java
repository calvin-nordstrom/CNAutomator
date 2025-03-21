package com.calvinnordstrom.passrepeater.controller;

import com.calvinnordstrom.passrepeater.model.Axis;
import com.calvinnordstrom.passrepeater.model.PassRepeater;

public class MainController {
    private final PassRepeater model;

    public MainController(PassRepeater model) {
        this.model = model;
    }

    public void resetPassRepeater() {
        model.resetPassRepeater();
    }

    public void setCommandTextBefore(String value) {
        model.setCommandTextBefore(value);
    }

    public void setCommandTextAfter(String value) {
        model.setCommandTextAfter(value);
    }

    public void setCommandAxis(Axis value) {
        model.setCommandAxis(value);
    }

    public void setCommandInitialPos(double value) {
        model.setCommandInitialPos(value);
    }

    public void setCommandFinalPos(double value) {
        model.setCommandFinalPos(value);
    }

    public void setCommandIncrement(double value) {
        model.setCommandIncrement(value);
    }

    public void setCommandFirstPass(String value) {
        model.setCommandFirstPass(value);
    }

    public void setCommandSecondPass(String value) {
        model.setCommandSecondPass(value);
    }
}
