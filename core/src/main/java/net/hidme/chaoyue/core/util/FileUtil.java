package net.hidme.chaoyue.core.util;

import java.io.*;
import java.nio.file.Path;

public abstract class FileUtil {

    public static DataInputStream getDataInputStream(String rootPath, String... path) throws FileNotFoundException {
        return new DataInputStream(new FileInputStream(Path.of(rootPath, path).toFile()));
    }

    public static DataOutputStream getDataOutputStream(String rootPath, String... path) throws FileNotFoundException {
        return new DataOutputStream(new FileOutputStream(Path.of(rootPath, path).toFile()));
    }

    public static DataInputStream getDataInputStream(Path path) throws FileNotFoundException {
        return new DataInputStream(new FileInputStream(path.toFile()));
    }

    public static DataOutputStream getDataOutputStream(Path path) throws FileNotFoundException {
        return new DataOutputStream(new FileOutputStream(path.toFile()));
    }

}
