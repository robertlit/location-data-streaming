package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import me.robertlit.datastream.model.ExtendedLocationUpdate;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Checks for events in a series of data points.
 */
public interface CheckProcessor {

    /**
     * Checks for events in a series of data points.
     * @param data the data points
     * @return the events found
     */
    @NotNull List<LocationEvent.Event> process(@NotNull List<ExtendedLocationUpdate> data);
}
