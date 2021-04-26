package com.finances.invoices.helpers;

import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTestSuite {
    protected Connection session;
    protected static String initScript;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Rule
    public PostgreSQLContainer postgres = (PostgreSQLContainer) new PostgreSQLContainer()
            .withInitScript(initScript);

    @Before
    public void setUp() throws Exception {
        String url = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();
        session = DriverManager.getConnection(url, username, password);
    }

    public void insertFrom(String pathToScript) throws IOException {
        String scriptContent = IOUtils.toString(ClassLoader.getSystemResourceAsStream(pathToScript), Charset.defaultCharset());
        jdbcTemplate.execute(scriptContent);
    }

}
