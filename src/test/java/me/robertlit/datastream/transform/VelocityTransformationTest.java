package me.robertlit.datastream.transform;

import me.robertlit.datastream.LocationEvent;
import me.robertlit.datastream.model.ExtendedLocationUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class VelocityTransformationTest {

    private Transformation transformation;

    @BeforeEach
    void setUp() {
        transformation = new VelocityTransformation();
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void apply(List<ExtendedLocationUpdate> data, double[] velocities) {
        double[] transformed = transformation.apply(data).stream()
                .map(ExtendedLocationUpdate::getVelocity)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .toArray();

        assertArrayEquals(velocities, transformed, 0.5);
    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.arguments(
                        List.of(
                                ExtendedLocationUpdate.fromLocationUpdate(LocationEvent.LocationUpdate.newBuilder()
                                        .setLatitude(41.71465)
                                        .setLongitude(-76.47387)
                                        .setTimestamp(0)
                                        .build()),
                                ExtendedLocationUpdate.fromLocationUpdate(LocationEvent.LocationUpdate.newBuilder()
                                        .setLatitude(41.714915)
                                        .setLongitude(-76.473855)
                                        .setTimestamp(1000)
                                        .build()),
                                ExtendedLocationUpdate.fromLocationUpdate(LocationEvent.LocationUpdate.newBuilder()
                                        .setLatitude(41.71518)
                                        .setLongitude(-76.47384)
                                        .setTimestamp(2000)
                                        .build()),
                                ExtendedLocationUpdate.fromLocationUpdate(LocationEvent.LocationUpdate.newBuilder()
                                        .setLatitude(41.715445)
                                        .setLongitude(-76.473825)
                                        .setTimestamp(3000)
                                        .build())
                        ), new double[] { 29.865, 29.865 }
                )
        );
    }
}