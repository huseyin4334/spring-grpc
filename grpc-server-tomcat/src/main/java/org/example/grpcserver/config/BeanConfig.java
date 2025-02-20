package org.example.grpcserver.config;

import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http2.Http2Protocol;
import org.example.grpcserver.model.ScopedBean;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    ScopedBean scopedBean() {
        return new ScopedBean();
    }

    // singleton
    @Bean
    ScopedBean singletonBean() {
        return new ScopedBean();
    }

    @Bean
    public TomcatConnectorCustomizer customizer() {
        return (connector) -> {
            for (UpgradeProtocol protocol : connector.findUpgradeProtocols()) {
                if (protocol instanceof Http2Protocol http2Protocol) {
                    http2Protocol.setOverheadWindowUpdateThreshold(0);
                }
            }
        };
    }
}
