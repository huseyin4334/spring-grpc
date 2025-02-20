package org.example.grpcserver.config;

import org.example.grpcserver.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.GlobalServerInterceptor;

@Configuration
public class InterceptorConfig {

    @Bean
    @GlobalServerInterceptor
    public LoggerInterceptor globalInterceptor() {
        return new LoggerInterceptor();
    }
}
