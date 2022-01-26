import de.fabilucius.sympel.configuration.types.LocalFileConfiguration;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocalFileConfigurationTest {

    @Test
    public void testLoad() {
        File testFile = new File("src/test/resources/test_config.yml");

        LocalFileConfiguration localFileConfiguration = LocalFileConfiguration.fromFile(testFile);
        assertNotNull(localFileConfiguration);

        String testString = localFileConfiguration.returnFrom("Test-String").getAs(String.class);
        assertEquals(testString, "test");

        Boolean testBoolean = localFileConfiguration.returnFrom("Test-Boolean").getAs(Boolean.class);
        assertEquals(testBoolean, true);

        Number testNumber = localFileConfiguration.returnFrom("Test-Number").getAs(Number.class);
        assertEquals(testNumber, 57);

        List<?> testList = localFileConfiguration
                .returnFrom("Test-List").getAsWithDefault(Collections.emptyList(), List.class);
        assertEquals(testList.size(), 2);
        assertEquals(testList.get(0), "test1");
        assertEquals(testList.get(1), "test2");
    }

}
