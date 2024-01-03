package me.robertlit.datastream.calc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationsTest {

    @ParameterizedTest
    @MethodSource("distanceInfoProvider")
    void calculateDistance2(double lat1,
                            double lon1,
                            double lat2,
                            double lon2,
                            double distance) {
        assertEquals(distance, Calculations.calculateDistance(lat1, lon1, lat2, lon2), 0.5);
    }

    static Stream<Arguments> distanceInfoProvider() {
        return Stream.of(
                Arguments.of(41.69370, -76.46103, 41.69517, -76.46244, 201.26)
        );
    }

    @ParameterizedTest
    @MethodSource("velocityInfoProvider")
    void calculateVelocity(double lat1,
                           double lon1,
                           long time1,
                           double lat2,
                           double lon2,
                           long time2,
                           double velocity) {
        assertEquals(velocity, Calculations.calculateVelocity(lat2, lon2, time2, lat1, lon1, time1), 0.5);
    }

    static Stream<Arguments> velocityInfoProvider() {
        return Stream.of(Arguments.of(
                41.71465,
                -76.47387,
                1703349750805L,
                41.71518,
                -76.47384,
                1703349752805L,
                29.865));
    }

    @ParameterizedTest
    @MethodSource("accelerationInfoProvider")
    void calculateAcceleration(double velocity1,
                               long time1,
                               double velocity2,
                               long time2,
                               double acceleration) {
        assertEquals(acceleration, Calculations.calculateAcceleration(velocity2, time2, velocity1, time1), 0.5);
    }

    static Stream<Arguments> accelerationInfoProvider() {
        return Stream.of(
                Arguments.of(20, 1703349750805L, 30, 1703349752805L, 5)
        );
    }
}