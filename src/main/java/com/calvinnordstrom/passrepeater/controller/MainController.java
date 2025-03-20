package com.calvinnordstrom.passrepeater.controller;

import com.calvinnordstrom.passrepeater.model.PassRepeater;
import com.calvinnordstrom.passrepeater.model.RepeatCommand;

public class MainController {
    private final PassRepeater model;

    public MainController(PassRepeater model) {
        this.model = model;
    }

    public void execute(RepeatCommand command) {
        model.execute(command);
    }
}
