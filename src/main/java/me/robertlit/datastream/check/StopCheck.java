package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

public class StopCheck implements EventCheck<Double> {

    private final double minVelocity;

    public StopCheck(double minVelocity) {
        this.minVelocity = minVelocity;
    }

    @Override
    public boolean check(Double velocity) {
        return velocity < minVelocity;
    }

    @Override
    public @NotNull LocationEvent.Event.Type eventType() {
        return LocationEvent.Event.Type.STOPPED;
    }
}
