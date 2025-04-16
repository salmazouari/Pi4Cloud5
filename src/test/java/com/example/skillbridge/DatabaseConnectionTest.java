package com.example.skillbridge;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DatabaseConnectionTest {

	@Autowired
	private DataSource dataSource; // Injects the configured DataSource

	@Test
	void testDatabaseConnection() throws SQLException {
		// Check if DataSource is injected
		assertNotNull(dataSource, "DataSource should not be null");

		// Test the connection
		try (Connection connection = dataSource.getConnection()) {
			assertTrue(connection.isValid(5), "Connection should be valid");
			System.out.println("Database connected: " + connection.getMetaData().getURL());
		}
	}
}