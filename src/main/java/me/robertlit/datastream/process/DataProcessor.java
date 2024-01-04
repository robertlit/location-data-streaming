package me.robertlit.datastream.process;

import me.robertlit.datastream.check.CheckProcessor;
import me.robertlit.datastream.data.SlidingWindow;
import me.robertlit.datastream.model.ExtendedLocationUpdate;
import me.robertlit.datastream.response.ResponseReceiver;
import me.robertlit.datastream.transform.Transformation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DataProcessor {

    private final SlidingWindow slidingWindow;

    private final List<Transformation> transformations;
    private final List<CheckProcessor> checkProcessors;
    private final ResponseReceiver responseReceiver;

    private final ScheduledExecutorService executorService;

    private final int windowSize, slideSize;

    public DataProcessor(@NotNull SlidingWindow slidingWindow,
                         @NotNull ResponseReceiver responseReceiver,
                         @NotNull ScheduledExecutorService executorService,
                         int windowSize,
                         int slideSize) {
        this.slidingWindow = slidingWindow;

        this.transformations = new ArrayList<>();
        this.checkProcessors = new ArrayList<>();
        this.responseReceiver = responseReceiver;

        this.executorService = executorService;

        this.windowSize = windowSize;
        this.slideSize = slideSize;
    }

    public void addTransformation(@NotNull Transformation transformation) {
        transformations.add(transformation);
    }

    public void addCheckProcessor(@NotNull CheckProcessor checkProcessor) {
        checkProcessors.add(checkProcessor);
    }

    public @NotNull ScheduledFuture<?> start() {
        Transformation combinedTransformation =
                transformations.stream().reduce(Transformation.identity(), Transformation::chain);

        return executorService.scheduleWithFixedDelay(() -> {
            List<ExtendedLocationUpdate> data = slidingWindow.take(windowSize, slideSize)
                    .stream()
                    .map(ExtendedLocationUpdate::fromLocationUpdate)
                    .toList();

            if (data.isEmpty()) {
                return;
            }

            List<ExtendedLocationUpdate> transformedData = combinedTransformation.apply(data);

            checkProcessors.stream()
                    .map(checkProcessor -> checkProcessor.process(transformedData))
                    .flatMap(List::stream)
                    .forEach(responseReceiver::onResponse);

        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        executorService.shutdownNow();
    }
}
