package me.robertlit.datastream.check;

import me.robertlit.datastream.LocationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopCheckTest {

    private double minVelocity;
    private EventCheck<Double> check;

    @BeforeEach
    void setUp() {
        minVelocity = 1;
        check = new StopCheck(minVelocity);
    }

    @Test
    void check() {
        assertTrue(check.check(minVelocity - 0.1));
        assertFalse(check.check(minVelocity));
        assertFalse(check.check(minVelocity + 0.1));
    }

    @Test
    void eventType() {
        assertEquals(LocationEvent.Event.Type.STOPPED, check.eventType());
    }
}