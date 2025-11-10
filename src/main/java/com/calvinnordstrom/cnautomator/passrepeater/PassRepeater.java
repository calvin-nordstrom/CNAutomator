package com.calvinnordstrom.cnautomator.passrepeater;

import com.calvinnordstrom.cnautomator.module.Module;

public class PassRepeater implements Module<PassRepeaterModel, PassRepeaterView> {
    private final PassRepeaterModel model;
    private final PassRepeaterView view;

    public PassRepeater() {
        model = new PassRepeaterModel();
        view = new PassRepeaterView(model);
    }

    @Override
    public PassRepeaterModel getModel() {
        return model;
    }

    @Override
    public PassRepeaterView getView() {
        return view;
    }
}
