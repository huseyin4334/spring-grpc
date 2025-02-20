package org.example.grpcserver.service;

import com.example.proto.unary.UnaryRequest;
import com.example.proto.unary.UnaryResponse;
import com.example.proto.unary.UnaryServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class UnaryService extends UnaryServiceGrpc.UnaryServiceImplBase {

    @Override
    public void dummyMethod(UnaryRequest request, StreamObserver<UnaryResponse> responseObserver) {
        try {
            UnaryResponse response = UnaryResponse.newBuilder()
                    .setName("Hello " + request.getName() + ". I catch the request.")
                    .build();
            responseObserver.onNext(response); // this will send response to client but not close the stream
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Internal server error. WE did something wrong.")
                            .withCause(e)
                            .asRuntimeException()
            );
        }

        responseObserver.onCompleted(); // this will close the stream
    }
}
