package com.atomscat.config.ratpack;

import com.google.inject.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ratpack.func.Function;
import ratpack.guice.Guice;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import ratpack.server.ServerConfigBuilder;
import ratpack.spring.Spring;
import ratpack.spring.config.RatpackProperties;
import ratpack.spring.config.RatpackServerCustomizer;
import ratpack.spring.config.internal.ChainConfigurers;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Configuration
public class StartRatpackConfiguration  implements CommandLineRunner {
    @Autowired
    private RatpackServer server;


    public StartRatpackConfiguration() {}

    public void run(String... args) throws Exception {
        this.server.start();
    }

    @PreDestroy
    public void stop() throws Exception {
        this.server.stop();
    }

    @Configuration
    protected static class ServerConfiguration {
        @Autowired
        private ServerConfig serverConfig;

        @Autowired
        private ChainConfigurers chainConfigurers;

        @Autowired(required = false)
        private List<RatpackServerCustomizer> customizers = Collections.emptyList();

        protected ServerConfiguration() {}

        @Bean
        public RatpackServer ratpackServer(ApplicationContext context) throws Exception {
            return RatpackServer.of((ratpackServerSpec) -> {
                ratpackServerSpec.serverConfig(this.serverConfig).registry(this.joinedRegistry(context)).handlers(this.chainConfigurers);
            });
        }

        private Function<Registry, Registry> joinedRegistry(ApplicationContext context) throws Exception {
            return (baseRegistry) -> {
                return Spring.spring(context).join((Registry) Guice.registry((bindingSpec) -> {
                    context.getBeansOfType(Module.class).values().forEach(bindingSpec::module);
                    Iterator var3 = this.customizers.iterator();

                    while(var3.hasNext()) {
                        RatpackServerCustomizer customizer = (RatpackServerCustomizer)var3.next();
                        customizer.getBindings().execute(bindingSpec);
                    }

                }).apply(baseRegistry));
            };
        }
    }

    @Configuration
    protected static class ServerConfigConfiguration {
        @Autowired
        private RatpackProperties ratpack;

        @Autowired(required = false)
        private List<RatpackServerCustomizer> customizers = Collections.emptyList();


        @Value("${server.port}")
        private Integer port;

        protected ServerConfigConfiguration() {}

        @Bean
        @ConditionalOnMissingBean
        public ServerConfig ratpackServerConfig() throws Exception {
            this.ratpack.setPort(port);

            ServerConfigBuilder serverConfigBuilder = ServerConfig.builder().baseDir(this.ratpack.getBasepath()).address(this.ratpack.getAddress()).threads(this.ratpack.getMaxThreads());
            if(this.ratpack.getPort() != null) {
                serverConfigBuilder.port(this.ratpack.getPort().intValue());
            }

            Iterator var2 = this.customizers.iterator();

            while(var2.hasNext()) {
                RatpackServerCustomizer customizer = (RatpackServerCustomizer)var2.next();
                customizer.getServerConfig().execute(serverConfigBuilder);
            }

            return serverConfigBuilder.build();
        }
    }
}
