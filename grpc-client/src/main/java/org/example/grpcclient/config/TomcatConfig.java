package org.example.grpcclient.config;

import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
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
