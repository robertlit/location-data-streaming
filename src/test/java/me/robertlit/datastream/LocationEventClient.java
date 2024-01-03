package me.robertlit.datastream;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationEventClient {

    private static final Logger log = Logger.getLogger(LocationEventClient.class.getName());

    private final LocationEventServiceGrpc.LocationEventServiceStub asyncStub;

    public LocationEventClient(Channel channel) {
        this.asyncStub = LocationEventServiceGrpc.newStub(channel);
    }

    public void streamLocationUpdates() throws InterruptedException {
        AtomicBoolean finished = new AtomicBoolean(false);

        StreamObserver<LocationEvent.LocationUpdate> requestObserver =
                asyncStub.streamLocationUpdates(new StreamObserver<>() {

                    @Override
                    public void onNext(LocationEvent.Event event) {
                        info("Got Event of type {0} at {1}",
                                event.getEventType().name(),
                                event.getTimestamp());
                    }

                    @Override
                    public void onError(Throwable t) {
                        warning("streamLocationUpdate failed: {0}", Status.fromThrowable(t));
                        t.printStackTrace(System.err);
                    }

                    @Override
                    public void onCompleted() {
                        info("Finished receiving events");
                        finished.set(true);
                    }
                });

        try {
            for (int i = 0; i < 100; i++) {
                LocationEvent.LocationUpdate request = LocationEvent.LocationUpdate.newBuilder()
                        .setLatitude(54.001 + i * i * 0.0)
                        .setLongitude(23.001)
                        .setTimestamp(System.currentTimeMillis())
                        .build();
                requestObserver.onNext(request);
                log.info("Sent location update");
                Thread.sleep(1000);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            requestObserver.onError(e);
            throw e;
        }

        requestObserver.onCompleted();

        while (!finished.get()) {
            log.info("Awaiting events");
        }
    }

    private void info(String msg, Object... params) {
        log.log(Level.INFO, msg, params);
    }

    private void warning(String msg, Object... params) {
        log.log(Level.WARNING, msg, params);
    }

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8980";

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();

        LocationEventClient client = new LocationEventClient(channel);
        client.streamLocationUpdates();

        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
}
