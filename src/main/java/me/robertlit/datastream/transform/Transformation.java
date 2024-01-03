package me.robertlit.datastream.transform;

import me.robertlit.datastream.model.ExtendedLocationUpdate;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * Represents a transformation that enriches a series of data points.
 */
public interface Transformation extends Function<List<ExtendedLocationUpdate>, List<ExtendedLocationUpdate>> {

    /**
     * Returns a composed transformation that first applies this transformation to
     * the input and then applies the after transformation to the result.
     * @param after the transformation to apply after this transformation is applied
     * @return a composed transformation that first applies this function and then applies the after function
     */
    default @NotNull Transformation chain(@NotNull Transformation after) {
        return value -> after.apply(this.apply(value));
    }

    /**
     * @return a transformation that returns its input
     */
    static @NotNull Transformation identity() {
        return value -> value;
    }
}
