package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpeedLimitCheckTest {

    private double maxVelocity;
    private EventCheck<Double> check;

    @BeforeEach
    void setUp() {
        maxVelocity = 5;
        check = new SpeedLimitCheck(maxVelocity);
    }

    @Test
    void check() {
        assertTrue(check.check(maxVelocity + 0.1));
        assertFalse(check.check(maxVelocity));
        assertFalse(check.check(maxVelocity - 0.1));
    }

    @Test
    void eventType() {
        assertEquals(LocationEvent.Event.Type.SPEED_LIMIT_VIOLATION, check.eventType());
    }
}