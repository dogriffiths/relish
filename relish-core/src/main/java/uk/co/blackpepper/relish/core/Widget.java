package uk.co.blackpepper.relish.core;

/**
 * The type Widget.
 *
 * @param <T> the type parameter
 */
public abstract class Widget<T> extends Component {
    private T peer;

    /**
     * Instantiates a new Widget.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public Widget(T peer, Component parent) {
        super(parent);
        if (parent == null) {
            throw new IllegalArgumentException("Parent cannot be null");
        }
//        parent.assertVisible();
        this.peer = peer;
    }


    /**
     * Get t.
     *
     * @return the t
     */
    public T get() {
        return peer;
    }

    /**
     * Click.
     */
    public abstract void click();

    /**
     * Assert invisible.
     */
    public abstract void assertInvisible();

    /**
     * Assert invisible.
     */
    public abstract void assertVisible();

    /**
     * Assert disabled.
     */
    public abstract void assertDisabled();

    /**
     * Assert enabled.
     */
    public abstract void assertEnabled();

    /**
     * Scroll to widget.
     *
     * @return the widget
     */
    public abstract Widget<T> scrollTo();

    @Override
    public void matches(String s) {
        if (s != null) {
            if ("[INVISIBLE]".equalsIgnoreCase(s)) {
                assertInvisible();
                return;
            }
            if ("[VISIBLE]".equalsIgnoreCase(s)) {
                assertVisible();
                return;
            }
            if ("[ENABLED]".equalsIgnoreCase(s)) {
                assertEnabled();
                return;
            }
            if ("[DISABLED]".equalsIgnoreCase(s)) {
                assertDisabled();
                return;
            }
            if (s.startsWith("[[") && s.endsWith("]]")) {
                s = s.substring(1, s.length() - 1);
            }
        }
        super.matches(s);
    }
}
