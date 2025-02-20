package org.example.grpcserver.service;

import com.example.proto.bidirectional.BidirectionalStreaming;
import com.example.proto.bidirectional.BidirectionalStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.stream.IntStream;

@GrpcService
public class BiDirectionalStreamingService extends BidirectionalStreamingServiceGrpc.BidirectionalStreamingServiceImplBase {
    // Client and server will send multiple requests and responses.
    @Override
    public StreamObserver<BidirectionalStreaming.BidirectionalStreamingRequest> bidirectionalStreamingMethod(StreamObserver<BidirectionalStreaming.BidirectionalStreamingResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(BidirectionalStreaming.BidirectionalStreamingRequest request) {
                IntStream.range(1, 4).forEach(i -> {
                    BidirectionalStreaming.BidirectionalStreamingResponse response = BidirectionalStreaming.BidirectionalStreamingResponse.newBuilder()
                            .setName("Hello " + request.getName() + ". I catch the request. Your index is " + (request.getIndex() * i))
                            .build();
                    responseObserver.onNext(response); // this will send response to client but not close the stream
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error occurred");
            }

            @Override
            public void onCompleted() {
                BidirectionalStreaming.BidirectionalStreamingResponse response = BidirectionalStreaming.BidirectionalStreamingResponse.newBuilder()
                        .setName("Process completed. I catch all the requests.")
                        .build();
                responseObserver.onNext(response); // this will send response to client but not close the stream
                responseObserver.onCompleted(); // this will close the stream
            }
        };
    }
}
