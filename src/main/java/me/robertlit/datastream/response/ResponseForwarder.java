package me.robertlit.datastream.response;

import io.grpc.stub.StreamObserver;
import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Serves as the gateway for sending Event responses to the client.
 */
public interface ResponseForwarder {

    /**
     * Sets the target for responses.
     * @param responseObserver the target
     */
    void forwardTo(@NotNull StreamObserver<LocationEvent.Event> responseObserver);

    /**
     * Unsets the target for responses.
     */
    void unbind();
}
