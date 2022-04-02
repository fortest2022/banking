package com.bis.banking;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The type Postgres test container.
 */
public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static PostgresTestContainer container;

    private PostgresTestContainer() {
        super(IMAGE_VERSION);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PostgresTestContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainer()
                    .withDatabaseName("banking")
                    .withUsername("user")
                    .withPassword("password")
                    .withInitScript("init_script.sql")
                    .withExposedPorts(5435);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
