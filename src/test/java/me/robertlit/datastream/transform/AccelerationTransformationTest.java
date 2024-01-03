package me.robertlit.datastream.transform;

import me.robertlit.datastream.model.ExtendedLocationUpdate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class AccelerationTransformationTest {

    private Transformation transformation;

    @BeforeEach
    void setUp() {
        transformation = new AccelerationTransformation();
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void apply(List<ExtendedLocationUpdate> data, double[] accelerations) {
        double[] transformed = transformation.apply(data).stream()
                .map(ExtendedLocationUpdate::getAcceleration)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .toArray();

        Assertions.assertArrayEquals(accelerations, transformed, 0.5);
    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                ExtendedLocationUpdate.newBuilder()
                                        .setVelocity(0D)
                                        .setTimestamp(0)
                                        .build(),
                                ExtendedLocationUpdate.newBuilder()
                                        .setVelocity(3D)
                                        .setTimestamp(1000)
                                        .build(),
                                ExtendedLocationUpdate.newBuilder()
                                        .setVelocity(10D)
                                        .setTimestamp(2000)
                                        .build(),
                                ExtendedLocationUpdate.newBuilder()
                                        .setVelocity(15D)
                                        .setTimestamp(3000)
                                        .build()
                        ), new double[] { 5, 6 }
                )
        );
    }
}