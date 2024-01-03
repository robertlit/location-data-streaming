package me.robertlit.datastream.service;

import io.grpc.stub.StreamObserver;
import me.robertlit.datastream.LocationEvent;
import me.robertlit.datastream.LocationEventServiceGrpc;
import me.robertlit.datastream.data.LocationUpdateHandler;
import me.robertlit.datastream.response.ResponseForwarder;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationEventService extends LocationEventServiceGrpc.LocationEventServiceImplBase {

    private final Logger logger = Logger.getLogger("LocationEventService");
    private final LocationUpdateHandler locationUpdateHandler;
    private final ResponseForwarder responseForwarder;

    public LocationEventService(@NotNull LocationUpdateHandler locationUpdateHandler,
                                @NotNull ResponseForwarder responseForwarder) {
        this.locationUpdateHandler = locationUpdateHandler;
        this.responseForwarder = responseForwarder;
    }

    @Override
    public StreamObserver<LocationEvent.LocationUpdate> streamLocationUpdates(StreamObserver<LocationEvent.Event> responseObserver) {
        responseForwarder.forwardTo(responseObserver);
        return new StreamObserver<>() {

            @Override
            public void onNext(LocationEvent.LocationUpdate value) {
                logger.log(Level.INFO, "Received LocationUpdate:\n{0}", value);
                locationUpdateHandler.onLocationUpdate(value);
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "Encountered an error in streamLocationUpdates", t);
                responseForwarder.unbind();
            }

            @Override
            public void onCompleted() {
                logger.log(Level.INFO, "Completed stream");
                responseForwarder.unbind();
                responseObserver.onCompleted();
            }
        };
    }
}
