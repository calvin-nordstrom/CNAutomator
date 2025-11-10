package com.calvinnordstrom.cnautomator.module;

public interface Module<M extends Model, V extends View<M>> {
    M getModel();

    V getView();
}
