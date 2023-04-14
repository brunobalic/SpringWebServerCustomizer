package com.brunobalic.demo.webservercustomizer;

import org.eclipse.jetty.http.CookieCompliance;
import org.eclipse.jetty.http.HttpCompliance;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * This is example of how to programmatically customize embedded web server.
 * It can be used to set properties that are not available through Spring Common Application Properties
 * <p>
 * This specific example shows how to customize {@link JettyServletWebServerFactory}.
 * In case other server like Tomcat or Undertow is used there are dedicated factories for them.
 */
@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<JettyServletWebServerFactory> {

    @Override
    public void customize(JettyServletWebServerFactory serverFactory) {
        serverFactory.setPort(9090);
        serverFactory.setContextPath("/v1");
        serverFactory.addServerCustomizers(server -> Arrays.stream(server.getConnectors()).forEach(this::customize));
    }

    private void customize(Connector connector) {
        connector.getConnectionFactories().forEach(this::customize);
    }

    private void customize(ConnectionFactory connectionFactory) {
        if (connectionFactory instanceof HttpConfiguration.ConnectionFactory) {
            customize(((HttpConfiguration.ConnectionFactory) connectionFactory).getHttpConfiguration());
        }
    }

    private void customize(HttpConfiguration httpConfiguration) {
        httpConfiguration.setHttpCompliance(HttpCompliance.RFC7230);
        httpConfiguration.setRequestCookieCompliance(CookieCompliance.RFC6265_STRICT);
        httpConfiguration.setResponseCookieCompliance(CookieCompliance.RFC6265_STRICT);
        // additional customization ...
    }

}
