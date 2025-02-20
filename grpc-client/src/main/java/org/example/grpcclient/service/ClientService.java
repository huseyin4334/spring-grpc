package org.example.grpcclient.service;

import com.example.proto.bidirectional.BidirectionalStreaming;
import com.example.proto.bidirectional.BidirectionalStreamingServiceGrpc;
import com.example.proto.clientstreaming.ClientStreaming;
import com.example.proto.clientstreaming.ClientStreamingServiceGrpc;
import com.example.proto.serverstreaming.ServerStreaming;
import com.example.proto.serverstreaming.ServerStreamingServiceGrpc;
import com.example.proto.unary.UnaryResponse;
import com.example.proto.unary.UnaryServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClientService {

    private final UnaryServiceGrpc.UnaryServiceBlockingStub unaryServiceBlockingStub;
    private final ServerStreamingServiceGrpc.ServerStreamingServiceStub serverStreamingServiceBlockingStub;
    private final BidirectionalStreamingServiceGrpc.BidirectionalStreamingServiceStub bidirectionalStreamingServiceBlockingStub;
    private final ClientStreamingServiceGrpc.ClientStreamingServiceStub clientStreamingServiceBlockingStub;

    public void callUnaryService(String name) {
        try {
            log.info("Calling unary service");
            UnaryResponse response = unaryServiceBlockingStub.dummyMethod(
                    com.example.proto.unary.UnaryRequest.newBuilder()
                            .setName(name)
                            .build()
            );

            log.info("Response from server: {}", response.getName());
        } catch (RuntimeException e) {
            log.error("Error occurred from server", e);
        }
    }


    public void callServerStreamingService(String name, int index) {
        log.info("Calling server streaming service");
        serverStreamingServiceBlockingStub.serverStreamingMethod(
                ServerStreaming.ServerStreamingRequest.newBuilder()
                        .setName(name)
                        .setIndex(index)
                        .build(),
                new StreamObserver<>() {
                    @Override
                    public void onNext(ServerStreaming.ServerStreamingResponse value) {
                        log.info("Response from server for server streaming: {}, {}", value.getName(), value.getIndexedValue());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("Error occurred", t);
                    }

                    @Override
                    public void onCompleted() {
                        log.info("Server streaming completed");
                    }
                }
        );
    }

    public void callClientStreamingService(String name, int index) {
        log.info("Calling client streaming service");
        StreamObserver<ClientStreaming.ClientStreamingRequest> requestObserver = clientStreamingServiceBlockingStub.clientStreamingMethod(
                new StreamObserver<>() {
                    @Override
                    public void onNext(ClientStreaming.ClientStreamingResponse value) {
                        log.info("Response from server for client streaming: {}, {}", value.getName(), value.getIndexedValue());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("Error occurred", t);
                    }

                    @Override
                    public void onCompleted() {
                        log.info("Client streaming completed");
                    }
                }
        );
        
        for (int i = index; i < index + 3; i++) {
            requestObserver.onNext(ClientStreaming.ClientStreamingRequest.newBuilder()
                    .setName(name)
                    .setIndex(i)
                    .build());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        requestObserver.onCompleted();
    }
    
    public void bidirectionalStreamingService(String name, int index) {
        log.info("Calling bidirectional streaming service");
        StreamObserver<BidirectionalStreaming.BidirectionalStreamingRequest> requestObserver = bidirectionalStreamingServiceBlockingStub.bidirectionalStreamingMethod(
                new StreamObserver<>() {
                    @Override
                    public void onNext(BidirectionalStreaming.BidirectionalStreamingResponse value) {
                        log.info("Response from server for bidirectional streaming: {}, {}", value.getName(), value.getIndexedValue());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("Error occurred", t);
                    }

                    @Override
                    public void onCompleted() {
                        log.info("Bidirectional streaming completed");
                    }
                }
        );

        for (int i = index; i < index + 3; i++) {
            requestObserver.onNext(BidirectionalStreaming.BidirectionalStreamingRequest.newBuilder()
                    .setName(name)
                    .setIndex(i)
                    .build());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // When we are done with sending requests, we need to call onCompleted to close the stream
        // Server will continue to send responses until it calls onCompleted
        requestObserver.onCompleted();
    }


}
