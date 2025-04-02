package net.hidme.chaoyue.core.world;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Data of a non-player character.
 */
public class Character implements Savable {

    /**
     * Load data of a character from an input stream.
     */
    public Character(DataInputStream in, long version) {}

    /**
     * Save data of a character via an output stream.
     */
    @Override
    public void save(DataOutputStream out) {}

    /*
     * ======== Private fields ========
     */

    private String name;

}
