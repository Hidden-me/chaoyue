package net.hidme.chaoyue.core.world;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Player implements Savable {

    /**
     * Load data of a character from an input stream.
     */
    public Player(DataInputStream in, long version) {}

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
