package org.example.grpcserver.handler;

import io.grpc.Status;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class GrpcExceptionAdvice implements GrpcExceptionHandler {

    @Override
    public Status handleException(Throwable exception) {
        if (exception instanceof IllegalArgumentException) {
            return Status.INVALID_ARGUMENT
                    .withDescription("Invalid argument provided.")
                    .withCause(exception);
        } else if (exception instanceof IllegalStateException) {
            return Status.FAILED_PRECONDITION
                    .withDescription("Operation is not allowed.")
                    .withCause(exception);
        } else if (exception instanceof UnsupportedOperationException) {
            return Status.UNIMPLEMENTED
                    .withDescription("Operation is not implemented.")
                    .withCause(exception);
        } else {
            return Status.UNKNOWN
                    .withDescription("Unknown error occurred.")
                    .withCause(exception);
        }
    }
}
