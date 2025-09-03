package app.verimly.integration;

import org.testcontainers.containers.PostgreSQLContainer;

public class SingletonPostgresContainer extends PostgreSQLContainer<SingletonPostgresContainer> {
    private static final String IMAGE_VERSION = "postgres:latest";
    private static SingletonPostgresContainer container;

    private SingletonPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static synchronized SingletonPostgresContainer getInstance() {
        if (container == null) {
            container = new SingletonPostgresContainer()
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test")
                    .withReuse(true);
            container.start();
        }
        return container;
    }
}