package com.calvinnordstrom.cnautomator.module;

import javafx.scene.Node;

public abstract class View<M extends Model> {
    protected final M model;

    public View(M model) {
        this.model = model;
    }

    public M getModel() {
        return model;
    }

    public abstract Node asNode();
}
