package uk.co.blackpepper.relish.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUtilsTest {
    @Test
    public void canCreateGetterName() {
        assertEquals("getFred", TestUtils.getGetterName("fred"));
        assertEquals("getFred", TestUtils.getGetterName("Fred"));
        assertEquals("getFredSmith", TestUtils.getGetterName("FredSmith"));
        assertEquals("getFredSmith", TestUtils.getGetterName("fred smith"));
    }
}
