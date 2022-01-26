import de.fabilucius.sympel.multiversion.ServerVersion;
import org.bukkit.Bukkit;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ServerVersionTest {

    @Test
    public void testIsMethod() {
        /* This will mimic as if the Bukkit.getBukkitVersion was used on a 1.10 Server */
        mockStatic(Bukkit.class);
        when(Bukkit.getBukkitVersion()).thenReturn("1.10");

        assertTrue(ServerVersion.is(ServerVersion.v1_11, ServerVersion.ComparisonType.HIGHER));
        assertTrue(ServerVersion.is(ServerVersion.v1_10, ServerVersion.ComparisonType.HIGHER_OR_EQUAL));
        assertTrue(ServerVersion.is(ServerVersion.v1_10, ServerVersion.ComparisonType.EQUAL));
        assertTrue(ServerVersion.is(ServerVersion.v1_10, ServerVersion.ComparisonType.LOWER_OR_EQUAL));
        assertTrue(ServerVersion.is(ServerVersion.v1_9, ServerVersion.ComparisonType.LOWER));
    }

}
