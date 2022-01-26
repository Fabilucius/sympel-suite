package de.fabilucius.sympel.configuration.node;

import java.util.UUID;

public class AbstractNode implements Node {

    /* Unique identifier for this node */
    private final UUID uuid = UUID.randomUUID();

    /* the getter and setter of the class */

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
