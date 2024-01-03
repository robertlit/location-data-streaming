package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FastAccelerationCheckTest {

    private double maxAcceleration;
    private EventCheck<Double> check;

    @BeforeEach
    void setUp() {
        maxAcceleration = 5;
        check = new FastAccelerationCheck(maxAcceleration);
    }

    @Test
    void check() {
        assertTrue(check.check(maxAcceleration + 0.1));
        assertFalse(check.check(maxAcceleration));
        assertFalse(check.check(maxAcceleration - 0.1));
    }

    @Test
    void eventType() {
        assertEquals(LocationEvent.Event.Type.FAST_ACCELERATION, check.eventType());
    }
}