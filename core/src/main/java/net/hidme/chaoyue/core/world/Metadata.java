package net.hidme.chaoyue.core.world;

import net.hidme.chaoyue.core.util.PropertiesUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import static net.hidme.chaoyue.core.GlobalInfo.CURRENT_VERSION;

/**
 * Metadata of a world. It is saved in a separate file.
 */
public class Metadata {

    /**
     * Create a new metadata instance.
     */
    public Metadata() {
        version = CURRENT_VERSION;
    }

    /**
     * Load metadata from a file.
     */
    public Metadata(Path path) throws IOException, CorruptedDataException {
        final Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(path.toFile())) {
            properties.load(in);
        }
        try {
            version = PropertiesUtil.getLongProperty(properties, KEY_VERSION);
        } catch (Throwable e) {
            throw new CorruptedDataException("corrupted metadata: " + e.getMessage());
        }
    }

    /**
     * Save metadata to a file.
     */
    public void save(Path path) throws IOException {
        try (FileOutputStream out = new FileOutputStream(path.toFile())) {
            toProperties().store(out, "World metadata");
        }
    }

    public long getVersion() {
        return version;
    }

    /*
     * ======== Private fields ========
     */

    private static final String KEY_VERSION = "chaoyue.version";

    private final long version;

    private Properties toProperties() {
        // HINT: generate properties
        final Properties properties = new Properties();
        properties.setProperty(KEY_VERSION, String.valueOf(version));
        return properties;
    }

}
