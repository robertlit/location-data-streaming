package me.robertlit.datastream.data;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides a sliding-window fashioned way to handle batches of data.
 */
public interface SlidingWindow {

    /**
     * Returns the data in the current window and slides the window,
     * or an empty list if not enough data is available.
     * @param windowSize size of the sliding window
     * @param slideSize the amount which the window slides
     * @return data in the current window, or an empty list if not enough data is available
     * @throws IllegalArgumentException if slideSize > windowSize
     */
    @NotNull List<LocationEvent.LocationUpdate> take(int windowSize, int slideSize);
}
