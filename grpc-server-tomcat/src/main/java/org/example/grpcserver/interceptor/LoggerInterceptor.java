package org.example.grpcserver.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall, // This is like HttpServletRequest and HttpServletResponse
            Metadata metadata, // Metadata is like HttpHeaders
            ServerCallHandler<ReqT, RespT> serverCallHandler // This is like FilterChain
    ) {
        log.info("Received request: {}", serverCall.getMethodDescriptor().getFullMethodName());
        return serverCallHandler.startCall(serverCall, metadata);
    }
}

// Request come from client -> LoggerInterceptor -> DummyService -> Response to client

// interceptors are like filters in Spring MVC.