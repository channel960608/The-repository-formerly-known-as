package edu.neu.coe.huskySort.util;

import java.util.concurrent.TimeUnit;

public class ProcessorDependentTimeout extends org.junit.rules.Timeout {
    /**
     * Create a {@code Timeout} instance with the timeout specified
     * at the timeUnit of granularity of the provided {@code TimeUnit}.
     *
     * @param timeout     the maximum time to allow the test to run
     *                    before it should timeout
     * @param timeUnit    the time unit for the {@code timeout}
     * @param speedFactor the speed factor. A fast laptop of 2018 vintage is 1.0. Slower machines have a rating < 1.0.
     * @since 4.12
     */
    public ProcessorDependentTimeout(long timeout, TimeUnit timeUnit, double speedFactor) {
        super(Math.round(timeout * 1000 / speedFactor / 1000), timeUnit);
    }
}
