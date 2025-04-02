package net.hidme.chaoyue.core.world;

/**
 * Thrown to indicate that data of a saved world is corrupted.
 */
public class CorruptedDataException extends Exception {
    public CorruptedDataException(String message) {
        super(message);
    }
}
