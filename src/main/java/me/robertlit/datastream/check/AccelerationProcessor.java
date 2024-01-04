package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import me.robertlit.datastream.model.ExtendedLocationUpdate;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AccelerationProcessor implements CheckProcessor {

    private final List<EventCheck<Double>> checks;

    public AccelerationProcessor() {
        this.checks = new ArrayList<>();
    }

    public void addCheck(@NotNull EventCheck<Double> check) {
        checks.add(check);
    }

    @Override
    public @NotNull List<LocationEvent.Event> process(@NotNull List<ExtendedLocationUpdate> data) {
        OptionalDouble averageAccelerationOptional = data.stream()
                .map(ExtendedLocationUpdate::getAcceleration)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .average();

        OptionalDouble averageTimestampOptional = data.stream()
                .mapToLong(ExtendedLocationUpdate::getTimestamp)
                .average();

        if (averageAccelerationOptional.isEmpty() || averageTimestampOptional.isEmpty()) {
            return Collections.emptyList();
        }

        double averageAcceleration = averageAccelerationOptional.getAsDouble();
        long averageTimestamp = (long) averageTimestampOptional.getAsDouble();

        return checks.stream()
                .filter(check -> check.check(averageAcceleration))
                .map(EventCheck::eventType)
                .map(type -> LocationEvent.Event.newBuilder()
                        .setEventType(type)
                        .setTimestamp(averageTimestamp)
                        .build())
                .toList();
    }
}
