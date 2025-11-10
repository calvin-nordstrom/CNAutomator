package com.calvinnordstrom.cnautomator.view;

import com.calvinnordstrom.cnautomator.module.Model;
import com.calvinnordstrom.cnautomator.module.Module;
import com.calvinnordstrom.cnautomator.module.View;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class MainView {
    private final List<Module<? extends Model, ? extends View<? extends Model>>> modules;
    private final BorderPane view = new BorderPane();

    public MainView(List<Module<? extends Model, ? extends View<? extends Model>>> modules) {
        this.modules = modules;

        initCenter();
    }

    private void initCenter() {
        TabPane tabPane = new TabPane();

        for (Module<? extends Model, ? extends View<? extends Model>> module : modules) {
            Tab tab = new Tab(module.getModel().getName(), module.getView().asNode());
            tab.setClosable(false);
            tabPane.getTabs().add(tab);
        }

        view.setCenter(tabPane);
    }

    public Node asNode() {
        return view;
    }
}
