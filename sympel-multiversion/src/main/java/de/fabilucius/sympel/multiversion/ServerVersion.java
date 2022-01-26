package de.fabilucius.sympel.multiversion;

import org.bukkit.Bukkit;

import java.util.Arrays;

public enum ServerVersion {

    v1_8(47, "1.8"),
    v1_9(110, "1.9"),
    v1_10(210, "1.10"),
    v1_11(316, "1.11"),
    v1_12(340, "1.12"),
    v1_13(404, "1.13"),
    v1_14(498, "1.14"),
    v1_15(578, "1.15"),
    v1_16(754, "1.16"),
    v1_17(756, "1.17"),
    v1_18(757, "1.18"),
    UNKNOWN(Integer.MAX_VALUE, "UNKNOWN");

    ServerVersion(int protocolVersion, String version) {
        this.protocolVersion = protocolVersion;
        this.version = version;
    }

    public static final ServerVersion SERVER_VERSION = fetchServerVersion();

    /**
     * The highest official protocol version number based on info from https://wiki.vg/Protocol_version_numbers.
     * Primarily used for ordering the ServerVersion's.
     */
    private final int protocolVersion;

    private final String version;

    public static ServerVersion fetchServerVersion() {
        String[] versionParts = Bukkit.getBukkitVersion().split("-");
        if (versionParts.length >= 1) {
            return Arrays.stream(values()).filter(serverVersion -> versionParts[0]
                    .startsWith(serverVersion.getVersion())).findFirst().orElse(UNKNOWN);
        }
        return UNKNOWN;
    }

    /**
     * This method takes two parameter and returns whether the serverVersion parameter is comparable to the passed
     * comparisonType in comparison to the current fetched serverVersion.
     *
     * @param serverVersion  the version to compare against the current serverVersion
     * @param comparisonType the type of comparison that should take place
     * @return the boolean resulting from the passed parameter
     */
    public static boolean is(ServerVersion serverVersion, ComparisonType comparisonType) {
        if (serverVersion.getProtocolVersion() > SERVER_VERSION.getProtocolVersion()) {
            return comparisonType.isHigher();
        }
        if (serverVersion.getProtocolVersion() == SERVER_VERSION.getProtocolVersion()) {
            return comparisonType.isEqual();
        }
        return comparisonType.isLower();
    }

    /* the getter and setter of the class */

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getVersion() {
        return version;
    }

    public enum ComparisonType {

        HIGHER,
        HIGHER_OR_EQUAL,
        EQUAL,
        LOWER,
        LOWER_OR_EQUAL;

        public boolean isHigher() {
            return this.name().contains("HIGHER");
        }

        public boolean isLower() {
            return this.name().contains("LOWER");
        }

        public boolean isEqual() {
            return this.name().contains("EQUAL");
        }

    }
}
