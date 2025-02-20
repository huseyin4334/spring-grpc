package org.example.grpcclient.config;

import com.example.proto.bidirectional.BidirectionalStreamingServiceGrpc;
import com.example.proto.clientstreaming.ClientStreamingServiceGrpc;
import com.example.proto.serverstreaming.ServerStreamingServiceGrpc;
import com.example.proto.unary.UnaryServiceGrpc;
import io.grpc.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubConfig {

    // My stubs are in same channel. All of them in grpc-server microservice.
    // We include all configurations on application.properties file.
    // it will get them from spring.grpc.client.channels.<channel-name>

    @Bean
    Channel channel(GrpcChannelFactory factory) {
        return factory.createChannel("grpc-server");
    }

    @Bean
    public UnaryServiceGrpc.UnaryServiceBlockingStub unaryServiceBlockingStub(Channel channel) {
        return UnaryServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ServerStreamingServiceGrpc.ServerStreamingServiceStub unaryServiceStub(Channel channel) {
        return ServerStreamingServiceGrpc.newStub(channel);
    }

    @Bean
    public ClientStreamingServiceGrpc.ClientStreamingServiceStub clientStreamingServiceBlockingStub(Channel channel) {
        return ClientStreamingServiceGrpc.newStub(channel);
    }

    @Bean
    public BidirectionalStreamingServiceGrpc.BidirectionalStreamingServiceStub bidirectionalStreamingServiceBlockingStub(Channel channel) {
        return BidirectionalStreamingServiceGrpc.newStub(channel);
    }

}
