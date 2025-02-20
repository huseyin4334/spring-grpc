package org.example.grpcserver.listener;

import org.springframework.context.event.EventListener;
import org.springframework.grpc.server.lifecycle.GrpcServerLifecycleEvent;
import org.springframework.grpc.server.lifecycle.GrpcServerShutdownEvent;
import org.springframework.grpc.server.lifecycle.GrpcServerStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class DummyEventListener {

    @EventListener
    public void handleEvent(GrpcServerStartedEvent event) {
        System.out.println("Server started event: " + event);
    }

    @EventListener
    public void handleEvent(GrpcServerShutdownEvent event) {
        System.out.println("Server shutting down event: " + event);
    }

    @EventListener
    public void handleEvent(GrpcServerLifecycleEvent event) {
        System.out.printf("Event is %s, server is %s%n", event, event.getServer());
    }
}
