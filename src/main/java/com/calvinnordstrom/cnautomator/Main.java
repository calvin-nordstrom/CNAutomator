package com.calvinnordstrom.cnautomator;

import com.calvinnordstrom.cnautomator.module.Model;
import com.calvinnordstrom.cnautomator.module.Module;
import com.calvinnordstrom.cnautomator.module.View;
import com.calvinnordstrom.cnautomator.passrepeater.PassRepeater;
import com.calvinnordstrom.cnautomator.passrepeater.PassRepeaterModel;
import com.calvinnordstrom.cnautomator.passrepeater.PassRepeaterView;
import com.calvinnordstrom.cnautomator.view.MainView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getPackageName());
    private static final String TITLE = "CNAutomator";
    private static final String VERSION = "1.1.1";
    private static final double WIDTH = 1200;
    private static final double HEIGHT = 720;
    private static final double MIN_WIDTH = 600;
    private static final double MIN_HEIGHT = 360;
    private final List<Module<? extends Model, ? extends View<? extends Model>>> modules = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        initGlobalScreen();

        Module<PassRepeaterModel, PassRepeaterView> passRepeater = new PassRepeater();
        modules.add(passRepeater);

        MainView view = new MainView(modules);
        Node root = view.asNode();
        Scene scene = new Scene((Parent) root);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle(TITLE + " " + VERSION);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setOnHidden(_ -> Platform.exit());
        stage.show();
    }

    @Override
    public void stop() {
        stopGlobalScreen();

        System.exit(0);
    }

    private void initGlobalScreen() {
        Logger.getLogger(GlobalScreen.class.getPackageName()).setLevel(Level.OFF);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private void stopGlobalScreen() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            LOGGER.severe(e.getMessage());
        }
    }
}