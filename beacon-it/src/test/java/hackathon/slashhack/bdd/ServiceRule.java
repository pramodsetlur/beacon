/**
 * Copyright (C) 1999 - 2014 Ticketmaster
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package hackathon.slashhack.bdd;

import hackathon.slashhack.Application;

import com.google.common.base.Strings;
import com.google.common.base.Joiner;
import com.jayway.restassured.RestAssured;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;


public class ServiceRule extends ExternalResource {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private static final String SCHEMA = "http://";
    private static final String URL_TEMPLATE = SCHEMA + "%s:%d/";

    private static final String PROPERTY_CLASS_ID = "classId";
    private static final String PROPERTY_PRODUCT_ID = "productId";
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_TARGET_CLUSTER = "targetCluster";
    private static final String PROPERTY_BUSINESS_UNIT = "businessUnit";
    private static final String ENV_RUNDECK_TARGET_CLUSTER = "RDECK_EXEC_ARG_TargetCluster";
    private static final String ENV_RUNDECK_CLUSTER_BU = "RDECK_EXEC_ARG_BusinessUnit";

    private static final String DEFAULT_HOST = "localhost";

    private ConfigurableApplicationContext lifecycle;

    /**
     * Starts the service on a random port and configures the base URL for RestAssured.
     */
    @Override
    protected void before() throws Throwable {
        LOG.info("Starting service");

        try {
            //If the host details are set in the Environment Properties, use them; otherwise, creating a local SpringBoot instance.

            String host;
            int port;

            String classId = System.getProperty(PROPERTY_CLASS_ID);
            String productId = System.getProperty(PROPERTY_PRODUCT_ID);
            String targetCluster = System.getProperty(PROPERTY_TARGET_CLUSTER, System.getenv(ENV_RUNDECK_TARGET_CLUSTER));
            String businessUnit = System.getProperty(PROPERTY_BUSINESS_UNIT, System.getenv(ENV_RUNDECK_CLUSTER_BU));
            String portEnv = System.getProperty(PROPERTY_PORT);

            if (validateEnvironment(classId, productId, targetCluster, businessUnit, portEnv)) {
                host = Joiner.on(".").join(classId, productId, targetCluster, businessUnit, "tmcs");
                port = Integer.parseInt(portEnv);
            } else {
                host = DEFAULT_HOST;
                final AtomicInteger refPort = new AtomicInteger();
                SpringApplication application = new SpringApplication(Application.class);

                // When the container initializes, grab the port number.
                application.addListeners(new ApplicationListener<EmbeddedServletContainerInitializedEvent>() {
                    @Override
                    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
                        EmbeddedServletContainer container = event.getEmbeddedServletContainer();
                        int port = container.getPort();
                        refPort.set(port);
                    }
                });

                // Start the service. Passing "--server.port=0" as a parameter tells Spring Boot to choose a random
                // available port. This avoids problems in Jenkins with services trying to bind to the same port as Jenkins
                // itself or colliding with other services that are being built and tested at the same time.
                lifecycle = application.run("--server.port=0");

                // When application.run(..) returns, we expect that the application listener has executed.
                port = refPort.get();
            }

            String url = String.format(URL_TEMPLATE, host, port);

            LOG.info("Service started on host url: " + url);

            RestAssured.basePath = url;
        } catch (Throwable t) {
            LOG.info("Service failed to start.");

            throw t;
        }
    }

    private boolean validateEnvironment(final String classId, final String productId, final String targetCluster,
        final String businessUnit, final String portEnv) {

        boolean result = true;

        if (Strings.isNullOrEmpty(classId) || Strings.isNullOrEmpty(productId) || Strings.isNullOrEmpty(targetCluster)
            || Strings.isNullOrEmpty(businessUnit) || Strings.isNullOrEmpty(portEnv)) {
            result = false;
        }

        return result;
    }

    @Override
    protected void after() {
        LOG.info("Stopping service");

        if (null != lifecycle) {
            lifecycle.stop();
        }
    }
}
