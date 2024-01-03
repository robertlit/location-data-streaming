package me.robertlit.datastream;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import me.robertlit.datastream.check.*;
import me.robertlit.datastream.data.DataReceiver;
import me.robertlit.datastream.process.DataProcessor;
import me.robertlit.datastream.response.ResponseHandler;
import me.robertlit.datastream.service.LocationEventService;
import me.robertlit.datastream.transform.AccelerationTransformation;
import me.robertlit.datastream.transform.VelocityTransformation;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationEventServer {

    private static final Logger log = Logger.getLogger(LocationEventServer.class.getName());

    private final int port;
    private final DataProcessor dataProcessor;
    private final Server server;

    public LocationEventServer(int port,
                               int windowSize,
                               int slideSize,
                               double maxVelocity,
                               double minVelocity,
                               double maxAcceleration,
                               double maxDeceleration) {
        this.port = port;

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        DataReceiver dataReceiver = new DataReceiver();
        ResponseHandler responseHandler = new ResponseHandler();
        this.dataProcessor = new DataProcessor(dataReceiver, responseHandler, executorService, windowSize, slideSize);

        dataProcessor.addTransformation(new VelocityTransformation());
        dataProcessor.addTransformation(new AccelerationTransformation());

        VelocityProcessor velocityProcessor = new VelocityProcessor();
        velocityProcessor.addCheck(new SpeedLimitCheck(maxVelocity));
        velocityProcessor.addCheck(new StopCheck(minVelocity));
        AccelerationProcessor accelerationProcessor = new AccelerationProcessor();
        accelerationProcessor.addCheck(new FastAccelerationCheck(maxAcceleration));
        accelerationProcessor.addCheck(new FastDecelerationCheck(maxDeceleration));

        dataProcessor.addEventCheck(velocityProcessor);
        dataProcessor.addEventCheck(accelerationProcessor);

        LocationEventService locationEventService = new LocationEventService(dataReceiver, responseHandler);

        this.server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(locationEventService)
                .build();
    }

    public void start() throws IOException {
        server.start();
        log.info("Server started on port " + port);

        startDataProcessingWithRetry();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server");
            try {
                stop();
            } catch (InterruptedException e) {
                System.err.println("Encountered an error while shutting down");
                e.printStackTrace(System.err);
            }
            System.err.println("Server shut down");
        }));
    }

    private void startDataProcessingWithRetry() {
        ScheduledFuture<?> future = dataProcessor.start();
        try {
            future.get();
        } catch (ExecutionException e) {
            log.log(Level.WARNING, "Encountered an exception while processing data", e.getCause());
            log.info("Restarting data processing task");
            startDataProcessingWithRetry();
        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Encountered an exception while processing data", e);
        }
    }

    public void stop() throws InterruptedException {
        dataProcessor.stop();
        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }

    private void blockUntilShutdown() throws InterruptedException {
        server.awaitTermination();
    }

    public static void main(String[] args) throws Exception {
        LocationEventServer server = new LocationEventServer(
                8980,
                10,
                2,
                25,
                1,
                5,
                -5
        );
        server.start();
        server.blockUntilShutdown();
    }
}
