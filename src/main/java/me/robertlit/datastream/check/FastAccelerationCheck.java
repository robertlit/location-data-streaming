package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

public class FastAccelerationCheck implements EventCheck<Double> {

    private final double maxAcceleration;

    public FastAccelerationCheck(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    @Override
    public boolean check(Double acceleration) {
        return acceleration > maxAcceleration;
    }

    @Override
    public @NotNull LocationEvent.Event.Type eventType() {
        return LocationEvent.Event.Type.FAST_ACCELERATION;
    }
}
