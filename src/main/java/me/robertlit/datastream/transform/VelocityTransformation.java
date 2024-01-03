package me.robertlit.datastream.transform;

import me.robertlit.datastream.calc.Calculations;
import me.robertlit.datastream.model.ExtendedLocationUpdate;

import java.util.ArrayList;
import java.util.List;

public class VelocityTransformation implements Transformation {

    @Override
    public List<ExtendedLocationUpdate> apply(List<ExtendedLocationUpdate> data) {
        List<ExtendedLocationUpdate> result = new ArrayList<>(data);

        for (int i = 1; i < data.size() - 1; i++) {
            ExtendedLocationUpdate prev = result.get(i - 1);
            ExtendedLocationUpdate curr = result.get(i);
            ExtendedLocationUpdate next = result.get(i + 1);

            result.set(i, ExtendedLocationUpdate.newBuilder(curr)
                    .setVelocity(Calculations.calculateVelocity(
                            next.getLatitude(),
                            next.getLongitude(),
                            next.getTimestamp(),
                            prev.getLatitude(),
                            prev.getLongitude(),
                            prev.getTimestamp()))
                    .build());
        }

        return result;
    }
}
