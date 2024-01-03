package me.robertlit.datastream.model;

import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class ExtendedLocationUpdate {

    public static @NotNull ExtendedLocationUpdate.Builder newBuilder() {
        return new ExtendedLocationUpdate.Builder();
    }

    public static @NotNull ExtendedLocationUpdate.Builder newBuilder(@NotNull ExtendedLocationUpdate extendedLocationUpdate) {
        return new ExtendedLocationUpdate.Builder(extendedLocationUpdate);
    }

    public static @NotNull ExtendedLocationUpdate fromLocationUpdate(@NotNull LocationEvent.LocationUpdate locationUpdate) {
        return new ExtendedLocationUpdate.Builder(locationUpdate).build();
    }

    private final double latitude;
    private final double longitude;
    private final long timestamp;
    private final @Nullable Double velocity;
    private final @Nullable Double acceleration;

    private ExtendedLocationUpdate(double latitude,
                                   double longitude,
                                   long timestamp,
                                   @Nullable Double velocity,
                                   @Nullable Double acceleration) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Optional<Double> getVelocity() {
        return Optional.ofNullable(this.velocity);
    }

    public Optional<Double> getAcceleration() {
        return Optional.ofNullable(this.acceleration);
    }

    public static class Builder {

        private double latitude;
        private double longitude;
        private long timestamp;
        private @Nullable Double velocity;
        private @Nullable Double acceleration;

        private Builder() {

        }

        private Builder(LocationEvent.LocationUpdate locationUpdate) {
            this.latitude = locationUpdate.getLatitude();
            this.longitude = locationUpdate.getLongitude();
            this.timestamp = locationUpdate.getTimestamp();
        }

        private Builder(ExtendedLocationUpdate extendedLocationUpdate) {
            this.latitude = extendedLocationUpdate.getLatitude();
            this.longitude = extendedLocationUpdate.getLongitude();
            this.timestamp = extendedLocationUpdate.getTimestamp();
            this.velocity = extendedLocationUpdate.getVelocity().orElse(null);
            this.acceleration = extendedLocationUpdate.getAcceleration().orElse(null);
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setVelocity(Double velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder setAcceleration(Double acceleration) {
            this.acceleration = acceleration;
            return this;
        }

        public ExtendedLocationUpdate build() {
            return new ExtendedLocationUpdate(
                    latitude,
                    longitude,
                    timestamp,
                    velocity,
                    acceleration
            );
        }
    }
}
