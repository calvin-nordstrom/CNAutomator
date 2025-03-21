package com.calvinnordstrom.passrepeater.view;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewUtils {
    public static void export(String value, Window owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialFileName("export.txt");

        File fileToSave = fileChooser.showSaveDialog(owner);
        if (fileToSave != null) {
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }

            Path path = Paths.get(filePath);
            try {
                Files.createDirectories(path.getParent());
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    writer.write(value);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static Pane createHorizontalDivider() {
        Pane divider = new Pane();
        divider.getStyleClass().add("horizontal-divider");
        return divider;
    }

    public static Pane createVerticalDivider() {
        Pane divider = new Pane();
        divider.getStyleClass().add("vertical-divider");
        return divider;
    }
}
