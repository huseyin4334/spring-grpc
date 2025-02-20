package org.example.grpcserver.service;

import com.example.proto.clientstreaming.ClientStreaming;
import com.example.proto.clientstreaming.ClientStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@GrpcService
public class ClientStreamingService extends ClientStreamingServiceGrpc.ClientStreamingServiceImplBase {
    // Client will send multiple requests and server will send one response and after that server will close the stream
    @Override
    public StreamObserver<ClientStreaming.ClientStreamingRequest> clientStreamingMethod(StreamObserver<ClientStreaming.ClientStreamingResponse> responseObserver) {
        return new StreamObserver<>() {

            private final AtomicInteger index = new AtomicInteger(0);

            @Override
            public void onNext(ClientStreaming.ClientStreamingRequest request) {
                log.info("Received request: {}, index: {}", request.getName(), index.getAndIncrement());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error occurred");
            }

            @Override
            public void onCompleted() {
                ClientStreaming.ClientStreamingResponse response = ClientStreaming.ClientStreamingResponse.newBuilder()
                        .setName("Process completed. I catch all the requests.")
                        .setIndexedValue(index.get())
                        .build();

                responseObserver.onNext(response); // this will send response to client but not close the stream
                responseObserver.onCompleted(); // this will close the stream
            }
        };
    }
}
