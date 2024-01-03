package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FastDecelerationCheckTest {

    private double maxDeceleration;
    private EventCheck<Double> check;

    @BeforeEach
    void setUp() {
        maxDeceleration = -5;
        check = new FastDecelerationCheck(maxDeceleration);
    }

    @Test
    void check() {
        assertTrue(check.check(maxDeceleration - 0.1));
        assertFalse(check.check(maxDeceleration));
        assertFalse(check.check(maxDeceleration + 0.1));
    }

    @Test
    void eventType() {
        assertEquals(LocationEvent.Event.Type.FAST_DECELERATION, check.eventType());
    }
}