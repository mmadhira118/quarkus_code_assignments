package org.codehosue.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.codehosue.resource.WriterResource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WriterRepository {
    public static final Logger LOGGER = Logger.getLogger(WriterResource.class);

    @ConfigProperty(name = "outputFilePath")
    private String outputFilePath;

    @ConfigProperty(name = "reportFilePath")
    private String auditLogFilePath;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void writeData(String data) {
        LOGGER.info("Entering in method WriterRepository::writeData -Line: 28");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            writer.write(data);
            writer.newLine();
            logAuditData(data);
        } catch (IOException e) {
            LOGGER.info("Exception occured in WriterRepository::writeData -Line: 33 Message: " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Returning from method WriterRepository::writeData -Line: 36");
    }

    private void logAuditData(String data) {
        LOGGER.info("Entering in method WriterRepository::logAuditData -Line: 40");

        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("%s: %s", timestamp, data);

        try (BufferedWriter auditWriter = new BufferedWriter(new FileWriter(auditLogFilePath, true))) {
            auditWriter.write(logEntry);
            auditWriter.newLine();
        } catch (IOException e) {
            LOGGER.info("Exception occured in WriterRepository::logAuditData -Line: 49 Message: " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Returning from method WriterRepository::logAuditData -Line: 51");
    }

    public List<String> getReport() {
        LOGGER.info("Entering in method WriterRepository::getReport -Line: 55");

        List<String> auditEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(auditLogFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                auditEntries.add(line);
            }
        } catch (IOException e) {
            LOGGER.info("Exception occured in WriterRepository::getReport -Line: 66 Message: " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Returning from method WriterRepository::getReport -Line: 67");
        return auditEntries;
    }
}
