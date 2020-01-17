package com.winston;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName WebApplication
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/2 11:19
 * @Version：
 */
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> customizer(){
//        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
//            @Override
//            public void customize(ConfigurableWebServerFactory factory) {
//                ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
//                    @Override
//                    public void customize(Connector connector) {
//                        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
////                        protocol.setMaxConnections(200);
////                        protocol.setMaxThreads(200);
////                        protocol.setSelectorTimeout(3000);
//                        protocol.setSessionTimeout(30);
////                        protocol.setConnectionTimeout(3000);
//                    }
//                });
//            }
//        };
//    }

}
