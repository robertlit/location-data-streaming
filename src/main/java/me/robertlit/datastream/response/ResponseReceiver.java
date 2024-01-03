package me.robertlit.datastream.response;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Receives and handles Event responses generated as a result of processing LocationUpdate data.
 */
public interface ResponseReceiver {

    /**
     * Handles the response.
     * @param event the response event
     */
    void onResponse(@NotNull LocationEvent.Event event);
}
