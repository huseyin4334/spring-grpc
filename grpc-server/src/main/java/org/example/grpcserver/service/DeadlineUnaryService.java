package org.example.grpcserver.service;

import com.example.proto.deadline.DeadlineRequest;
import com.example.proto.deadline.DeadlineResponse;
import com.example.proto.deadline.DeadlineServiceGrpc;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class DeadlineUnaryService extends DeadlineServiceGrpc.DeadlineServiceImplBase {
    @Override
    public void deadlineMethod(DeadlineRequest request, StreamObserver<DeadlineResponse> responseObserver) {
        Context context = Context.current();
        try {
            // This request will wait for 3 seconds before sending response
            for (int i = 0; i < 3; i++) {
                if (context.isCancelled()) {
                    return;
                }
                Thread.sleep(1000);
            }

            DeadlineResponse response = DeadlineResponse.newBuilder()
                    .setName("Hello " + request.getName() + ". I catch the request.")
                    .build();
            responseObserver.onNext(response); // this will send response to client but not close the stream
        } catch (InterruptedException e) { // if the Thread.sleep is interrupted
            responseObserver.onError(e);
        }

        responseObserver.onCompleted(); // this will close the stream
    }
}
