package com.calvinnordstrom.passrepeater.controller;

import com.calvinnordstrom.passrepeater.model.PassRepeater;

public class MainController {
    private final PassRepeater model;

    public MainController(PassRepeater model) {
        this.model = model;
    }

    public void resetPassRepeater() {
        model.resetPassRepeater();
    }
}
