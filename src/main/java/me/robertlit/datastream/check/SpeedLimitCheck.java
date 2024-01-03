package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

public class SpeedLimitCheck implements EventCheck<Double> {

    private final double maxVelocity;

    public SpeedLimitCheck(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    @Override
    public boolean check(Double velocity) {
        return velocity > maxVelocity;
    }

    @Override
    public @NotNull LocationEvent.Event.Type eventType() {
        return LocationEvent.Event.Type.SPEED_LIMIT_VIOLATION;
    }
}
