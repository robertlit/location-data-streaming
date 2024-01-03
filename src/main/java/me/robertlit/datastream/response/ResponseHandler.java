package me.robertlit.datastream.response;

import io.grpc.stub.StreamObserver;
import me.robertlit.datastream.LocationEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResponseHandler implements ResponseForwarder, ResponseReceiver {

    private @Nullable StreamObserver<LocationEvent.Event> responseObserver;

    @Override
    public void forwardTo(@NotNull StreamObserver<LocationEvent.Event> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void unbind() {
        this.responseObserver = null;
    }

    @Override
    public void onResponse(@NotNull LocationEvent.Event event) {
        if (responseObserver == null) {
            return;
        }

        responseObserver.onNext(event);
    }
}
