package org.kohsuke.args4j.spi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;


/**
 * @author dantuch
 */
@SuppressWarnings({"unused"})
public class SettersTest {

    private final String finalField = "thisValueMakesItFinal";
    private String mutableField;

    @Test
    public void testSNotCreateSetterForFinalField() throws Exception {
        // given
        Field f = SettersTest.class.getDeclaredField("finalField");
        // when
        assertThrows(IllegalStateException.class, () -> Setters.create(f, null));
    }

    @Test
    public void testSCreateSetterForMutableField() throws Exception {
        // given
        Field f = SettersTest.class.getDeclaredField("mutableField");
        // when
        @SuppressWarnings("rawtypes")
        Setter created = Setters.create(f, null);
        // then
        assertNotNull(created);
    }
}
