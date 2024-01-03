package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

public class FastDecelerationCheck implements EventCheck<Double> {

    private final double maxDeceleration;

    public FastDecelerationCheck(double maxDeceleration) {
        this.maxDeceleration = maxDeceleration;
    }

    @Override
    public boolean check(Double acceleration) {
        return acceleration < maxDeceleration;
    }

    @Override
    public @NotNull LocationEvent.Event.Type eventType() {
        return LocationEvent.Event.Type.FAST_DECELERATION;
    }
}
