package net.hidme.chaoyue.core.world;

import java.io.DataOutputStream;

/**
 * An interface for classes whose instances can be saved through a <code>DataOutputStream</code> object.
 */
public interface Savable {
    void save(DataOutputStream out);
}
