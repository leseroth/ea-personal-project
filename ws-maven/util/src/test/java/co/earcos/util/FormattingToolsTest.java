package co.earcos.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author erikarcos
 */
public class FormattingToolsTest {

    @Test
    public void testFormatDecimal() {
        assertEquals("0001", FormattingTools.formatDecimal("0000", 1));
        assertEquals("0123", FormattingTools.formatDecimal("0000", 123 % 10000));
        assertEquals("3456", FormattingTools.formatDecimal("0000", 123456 % 10000));
        assertEquals("-0001", FormattingTools.formatDecimal("0000", -1));
    }
}
