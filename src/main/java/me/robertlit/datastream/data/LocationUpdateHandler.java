package me.robertlit.datastream.data;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Receives and handles LocationUpdates sent by the client.
 */
public interface LocationUpdateHandler {

    /**
     * Takes a LocationUpdate for further processing.
     * @param locationUpdate the received LocationUpdate
     */
    void onLocationUpdate(@NotNull LocationEvent.LocationUpdate locationUpdate);
}
