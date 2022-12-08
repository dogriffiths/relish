package uk.co.blackpepper.relish.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Test utils.
 */
public class TestUtils {
    private final static double slowDown = Double.valueOf(System.getProperty("relish.slowDown", "1.0"));

    private TestUtils() {
        // Should not be instantiated
    }

    /**
     * Attempt.
     *
     * @param runnable the runnable
     * @param pause    the pause
     * @param times    the times
     */
    public static void attempt(Runnable runnable, int pause, int times) {
        boolean succeeded = false;
        Throwable e = null;
        for (int i = 0; (i < times) && !succeeded; i++) {
            try {
                runnable.run();
                succeeded = true;
            } catch (Error | Exception e1) {
                try {
                    Thread.sleep((long)(pause * slowDown));
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
                e = e1;
            }
        }
        if (!succeeded) {
            assert e != null;
            throw new IllegalStateException("Failed after several retries: " + e.getMessage(), e);
        }
    }

    public static String getGetterName(String methodName) {
        String getterName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        Pattern pattern = Pattern.compile("\\s(\\S)");
        Matcher matcher = pattern.matcher(getterName);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, matcher.group().toUpperCase().trim());
        }

        matcher.appendTail(result);
        getterName = result.toString();
        return getterName;
    }
}
