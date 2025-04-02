package net.hidme.chaoyue.core.world;

import net.hidme.chaoyue.core.util.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Data of a world.
 * For robustness, a world is stored in two copies under the same directory so that either copy is available.
 */
public class World {

    /**
     * Create a new world instance.
     */
    public World() {
        metadata = new Metadata();
    }

    /**
     * Load a world from a directory.
     */
    public World(String path) throws IOException, CorruptedDataException {
        final Path rootPath = Path.of(path);
        // decide which copy to load
        final int copyId = decideCopyToLoad(rootPath);
        System.out.println(copyId);
        // load that copy
        loadSingle(rootPath.resolve(String.valueOf(copyId)));
    }

    /**
     * Save the world as a directory.
     */
    public void save(String path) throws IOException {
        final Path rootPath = Path.of(path);
        // decide which copy to save
        final int copyId = decideCopyToSave(rootPath);
        // override that copy
        saveSingle(rootPath.resolve(String.valueOf(copyId)));
    }

    /*
     * ======== Private fields ========
     */

    private static final String PATH_COMMIT = "commit";
    private static final String PATH_METADATA = "metadata.properties";
    private static final String PATH_PLAYERS = "players.dat";
    private static final String PATH_CHARACTERS = "characters.dat";

    private Metadata metadata;
    private final List<Player> players = new ArrayList<>();
    private final List<Character> characters = new ArrayList<>();

    /*
     * ======== (Private) Savers ========
     */

    /** Pick which world copy to override. */
    private int decideCopyToSave(Path rootPath) {
        Long time0 = null, time1 = null;
        // try to load timestamp of copies
        try {
            time0 = getCopyTimestamp(rootPath.resolve("0"));
        } catch (Throwable ignored) {}
        try {
            time1 = getCopyTimestamp(rootPath.resolve("1"));
        } catch (Throwable ignored) {}
        // decide which copy to save (override)
        if (time0 == null) {
            return 0;
        } else if (time1 == null) {
            return 1;
        } else {
            // override the copy updated earlier
            return time0 < time1 ? 0 : 1;
        }
    }

    /** Get the timestamp of the copy's metadata file. */
    private long getCopyTimestamp(Path copyPath) throws IOException {
        return Files.getLastModifiedTime(copyPath.resolve(PATH_COMMIT)).toMillis();
    }

    /**
     * Save a single world copy.
     * It always conforms to the format of the latest version.
     */
    private void saveSingle(Path copyPath) throws IOException {
        // create directories if necessary
        Files.createDirectories(copyPath);
        // reset the commit file
        Files.deleteIfExists(copyPath.resolve(PATH_COMMIT));
        // save metadata
        metadata.save(copyPath.resolve(PATH_METADATA));
        // save players
        saveList(players, copyPath.resolve(PATH_PLAYERS));
        // save characters
        saveList(characters, copyPath.resolve(PATH_CHARACTERS));
        // commit
        Files.createFile(copyPath.resolve(PATH_COMMIT));
    }

    /** Save a list through a DataOutputStream. It first stores the list size and then list elements. */
    private <T extends Savable> void saveList(List<T> list, Path path) throws IOException {
        try (DataOutputStream out = FileUtil.getDataOutputStream(path)) {
            final int num = list.size();
            out.writeInt(num);
            for (int i = 0; i < num; i++) {
                list.get(i).save(out);
            }
        }
    }

    /*
     * ======== (Private) Loaders ========
     *
     * Notes:
     * These methods should only be invoked once during initialization.
     */

    /** Pick which world copy to load. */
    private int decideCopyToLoad(Path rootPath) throws CorruptedDataException {
        Long time0 = null, time1 = null;
        // try to load timestamp of copies
        try {
            time0 = getCopyTimestamp(rootPath.resolve("0"));
        } catch (Throwable ignored) {}
        try {
            time1 = getCopyTimestamp(rootPath.resolve("1"));
        } catch (Throwable ignored) {}
        // decide which copy to load
        if (time0 == null && time1 == null) {
            throw new CorruptedDataException("no available world copy");
        } else if (time0 == null) {
            return 1;
        } else if (time1 == null) {
            return 0;
        } else {
            // load the copy updated later
            return time0 < time1 ? 1 : 0;
        }
    }

    /** Load a single world copy. */
    private void loadSingle(Path copyPath) throws IOException, CorruptedDataException {
        // load metadata
        final Path metadataPath = copyPath.resolve(PATH_METADATA);
        metadata = new Metadata(metadataPath);
        // HINT: load the world according to the version number
        final long version = metadata.getVersion();
        if (version <= 0) {
            load0(copyPath, version);
        }
    }

    /** Loader version 0. */
    private void load0(Path copyPath, long version) throws IOException, CorruptedDataException {
        // load player data
        try (DataInputStream in = FileUtil.getDataInputStream(copyPath.resolve(PATH_PLAYERS))) {
            final int numPlayers = in.readInt();
            for (int i = 0; i < numPlayers; i++) {
                players.add(new Player(in, version));
            }
        }
        // load character data
        try (DataInputStream in = FileUtil.getDataInputStream(copyPath.resolve(PATH_CHARACTERS))) {
            final int numCharacters = in.readInt();
            for (int i = 0; i < numCharacters; i++) {
                characters.add(new Character(in, version));
            }
        }
    }

}
