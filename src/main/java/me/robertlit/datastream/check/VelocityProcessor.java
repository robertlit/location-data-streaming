package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import me.robertlit.datastream.model.ExtendedLocationUpdate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VelocityProcessor implements CheckProcessor {

    private final List<EventCheck<Double>> checks;

    public VelocityProcessor() {
        this.checks = new ArrayList<>();
    }

    public void addCheck(@NotNull EventCheck<Double> check) {
        checks.add(check);
    }

    @Override
    public @NotNull List<LocationEvent.Event> process(@NotNull List<ExtendedLocationUpdate> data) {
        double averageVelocity = data.stream()
                .map(ExtendedLocationUpdate::getVelocity)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("velocity stream must not be empty"));

        long averageTimestamp = (long) data.stream()
                .mapToLong(ExtendedLocationUpdate::getTimestamp)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("timestamp stream must not be empty"));

        return checks.stream()
                .filter(check -> check.check(averageVelocity))
                .map(EventCheck::eventType)
                .map(type -> LocationEvent.Event.newBuilder()
                        .setEventType(type)
                        .setTimestamp(averageTimestamp)
                        .build())
                .toList();
    }

}
