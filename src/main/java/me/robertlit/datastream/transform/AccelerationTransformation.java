package me.robertlit.datastream.transform;

import me.robertlit.datastream.calc.Calculations;
import me.robertlit.datastream.model.ExtendedLocationUpdate;

import java.util.ArrayList;
import java.util.List;

public class AccelerationTransformation implements Transformation {

    @Override
    public List<ExtendedLocationUpdate> apply(List<ExtendedLocationUpdate> data) {
        List<ExtendedLocationUpdate> result = new ArrayList<>(data);

        for (int i = 1; i < data.size() - 1; i++) {
            ExtendedLocationUpdate prev = result.get(i - 1);
            ExtendedLocationUpdate curr = result.get(i);
            ExtendedLocationUpdate next = result.get(i + 1);

            if (prev.getVelocity().isEmpty() || next.getVelocity().isEmpty()) {
                continue;
            }

            result.set(i, ExtendedLocationUpdate.newBuilder(curr)
                    .setAcceleration(Calculations.calculateAcceleration(
                            next.getVelocity().get(),
                            next.getTimestamp(),
                            prev.getVelocity().get(),
                            prev.getTimestamp()
                    ))
                    .build());
        }

        return result;
    }
}
