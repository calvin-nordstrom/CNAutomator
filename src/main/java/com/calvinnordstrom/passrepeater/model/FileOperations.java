package com.calvinnordstrom.passrepeater.model;

import java.io.*;

public class FileOperations {
    public static <T extends Serializable> void serialize(T obj, File file) throws IOException {
        File parentDir = file.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        }
    }

    public static <T extends Serializable> T deserialize(File file, Class<T> type) throws IOException, ClassNotFoundException {
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            return type.cast(obj);
        }
    }
}
