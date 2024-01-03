package me.robertlit.datastream.data;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DataReceiver implements LocationUpdateHandler, SlidingWindow {

    private final Deque<LocationEvent.LocationUpdate> data = new LinkedList<>();

    @Override
    public void onLocationUpdate(@NotNull LocationEvent.LocationUpdate locationUpdate) {
        data.addLast(locationUpdate);
    }

    @Override
    public @NotNull List<LocationEvent.LocationUpdate> take(int windowSize, int slideSize) {
        if (slideSize > windowSize) {
            throw new IllegalArgumentException("window size must be greater than or equal to slide size");
        }

        if (data.size() < windowSize) {
            return Collections.emptyList();
        }

        List<LocationEvent.LocationUpdate> batch = new ArrayList<>(windowSize);

        Iterator<LocationEvent.LocationUpdate> iterator = data.iterator();
        for (int i = 0; i < windowSize; i++) {
            LocationEvent.LocationUpdate locationUpdate = iterator.next();
            batch.add(locationUpdate);

            if (i < slideSize) {
                iterator.remove();
            }
        }

        return batch;
    }
}
