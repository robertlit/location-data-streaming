package me.robertlit.datastream.check;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AngleCheckTest {

    private List<Double> list;

    @BeforeEach
    void setUp() {
        list = List.of(1.0, 2.0, 5.0, 7.0, 0.0);
    }

    @Test
    void testMaxDifference() {
        assertEquals(7, findMaxDifference(list));
    }

    private static double findMaxDifference(List<Double> doubleList) {
        if (doubleList == null || doubleList.size() < 2) {
            throw new IllegalArgumentException("List should have at least two elements");
        }

        return doubleList.stream()
                .skip(1)
                .reduce(
                        new double[] {0, doubleList.get(0)},
                        (acc, current) -> {
                            double currentDifference = Math.abs(current - acc[1]);
                            acc[0] = Math.max(acc[0], currentDifference);
                            acc[1] = current;
                            return acc;
                        },
                        (acc1, acc2) -> {
                            acc1[0] = Math.max(acc1[0], acc2[0]);
                            return acc1;
                        }
                )[0];
    }
}
