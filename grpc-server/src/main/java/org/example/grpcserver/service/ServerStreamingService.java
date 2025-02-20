package org.example.grpcserver.service;

import com.example.proto.serverstreaming.ServerStreaming;
import com.example.proto.serverstreaming.ServerStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.util.stream.IntStream;

@Slf4j
@GrpcService
public class ServerStreamingService extends ServerStreamingServiceGrpc.ServerStreamingServiceImplBase {

    @Override
    public void serverStreamingMethod(ServerStreaming.ServerStreamingRequest request, StreamObserver<ServerStreaming.ServerStreamingResponse> responseObserver) {
        for (int i = request.getIndex(); i < request.getIndex() + 11; i++) {
            ServerStreaming.ServerStreamingResponse.Builder response = ServerStreaming.ServerStreamingResponse.newBuilder()
                    .setName("Hello " + request.getName() + ". I catch the request.")
                    .setIndexedValue(i);

            IntStream.range(0, i).forEach(j -> {
                response.addMessages("Message " + j);
                response.putMatches(String.valueOf(j), "Match " + j);
            });

            response.setNestedMessage(
                    ServerStreaming.NestedMessage.newBuilder()
                            .setNestedName("Nested Name %d".formatted(i))
                            .build()
            );

            responseObserver.onNext(response.build()); // this will send response to client but not close the stream
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Error occurred", e);
            }
        }
        responseObserver.onCompleted(); // this will close the stream
    }
}
