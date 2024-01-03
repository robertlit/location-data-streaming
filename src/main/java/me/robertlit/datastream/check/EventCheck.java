package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Checks for an event in a single data point
 * @param <T> the type of data
 */
public interface EventCheck<T> {

    /**
     * Returns whether the event occurred in the given data point
     * @param t the data
     * @return true if the event occurred, otherwise false
     */
    boolean check(T t);

    /**
     * Returns the type of event this check checks for
     * @return the type of event this check checks for
     */
    @NotNull LocationEvent.Event.Type eventType();
}
