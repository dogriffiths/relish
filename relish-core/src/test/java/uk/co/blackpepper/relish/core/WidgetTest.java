package uk.co.blackpepper.relish.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * The type Widget test.
 */
public class WidgetTest {
    /**
     * The Expected exception.
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * A widget must have a parent.
     */
    @Test
    public void aWidgetMustHaveAParent() {
        expectedException.expect(IllegalArgumentException.class);

        create("A peer", null);
    }

    /**
     * Parent must be visible.
     */
    @Test
    public void parentMustBeVisible() {
        Component parent = mock(Component.class);
        doThrow(new AssertionError("Not visible")).when(parent).assertVisible();
        expectedException.expect(AssertionError.class);
        create("A peer", parent);
    }

    /**
     * Can get the same peer back.
     */
    @Test
    public void canGetTheSamePeerBack() {
        Component parent = mock(Component.class);
        Widget widget = create("A peer", parent);
        assertEquals(widget.get(), "A peer");
    }

    @Test
    public void canMakeAssertionsThroughMatches() {
        Component parent = mock(Component.class);
        Widget widget = new Widget("Hello", parent) {
            @Override
            public void click() {

            }

            @Override
            public void assertInvisible() {
                throw new IllegalStateException("Not invisible!");
            }

            @Override
            public void assertVisible() {
                throw new IllegalStateException("Not visible!");
            }

            @Override
            public void assertDisabled() {
                throw new IllegalStateException("Not disabled!");
            }

            @Override
            public void assertEnabled() {
                throw new IllegalStateException("Not enabled!");
            }

            @Override
            public Widget scrollTo() {
                return null;
            }

            @Override
            public String getStringValue() {
                return "[ENABLED]";
            }
        };

        shouldThrowError(widget, "Not invisible!", "[Invisible]");
        shouldThrowError(widget, "Not visible!", "[Visible]");
        shouldThrowError(widget, "Not enabled!", "[Enabled]");
        shouldThrowError(widget, "Not disabled!", "[Disabled]");

        widget.matches("[[ENABLED]]");
    }

    private void shouldThrowError(Widget widget, String expectedMessage, String s) {
        try {
            widget.matches(s);
            fail("Should have thrown an error!");
        } catch(IllegalStateException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    private Widget create(final String peer, final Component parent) {
        return new Widget<String>(peer, parent) {

            @Override
            public void assertVisible() {

            }

            @Override
            public String getStringValue() {
                return null;
            }

            @Override
            public void click() {
            }

            @Override
            public void assertInvisible() {

            }

            @Override
            public void assertDisabled() {

            }

            @Override
            public void assertEnabled() {

            }

            @Override
            public Widget<String> scrollTo() {
                return null;
            }
        };
    }
}

class SimpleWidget extends Widget {

    /**
     * Instantiates a new Widget.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public SimpleWidget(Object peer, Component parent) {
        super(peer, parent);
    }

    @Override
    public void click() {

    }

    @Override
    public void assertInvisible() {

    }

    @Override
    public void assertVisible() {

    }

    @Override
    public String getStringValue() {
        return null;
    }

    @Override
    public void assertDisabled() {

    }

    @Override
    public void assertEnabled() {

    }

    @Override
    public Widget scrollTo() {
        return null;
    }
}