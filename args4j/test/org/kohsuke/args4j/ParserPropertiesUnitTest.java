package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserPropertiesUnitTest {

    @Test
    public void testDefaults() {
        ParserProperties props = ParserProperties.defaults();
        assertEquals(80, props.getUsageWidth());
        assertEquals(ParserProperties.DEFAULT_COMPARATOR, props.getOptionSorter());
    }

    @Test
    public void testSetToSame() {
        ParserProperties props = ParserProperties.defaults().withUsageWidth(80);
        assertEquals(80, props.getUsageWidth());
        assertEquals(ParserProperties.DEFAULT_COMPARATOR, props.getOptionSorter());
    }

    @Test
    public void testSetToDifferent() {
        ParserProperties props =
                ParserProperties.defaults().withUsageWidth(90).withOptionSorter(null);
        assertEquals(90, props.getUsageWidth());
        assertEquals(null, props.getOptionSorter());
    }

    @Test
    public void testSetOnlyOne() {
        ParserProperties props = ParserProperties.defaults().withOptionSorter(null);
        assertEquals(80, props.getUsageWidth());
        assertEquals(null, props.getOptionSorter());
    }

    @Test
    public void testFailOnNegativeWidth() {
        assertThrows(IllegalArgumentException.class,
                () -> ParserProperties.defaults().withUsageWidth(-1));
    }

    @Test
    public void testAcceptPositiveWidth() {
        assertDoesNotThrow(() -> ParserProperties.defaults().withUsageWidth(0));
    }
}
