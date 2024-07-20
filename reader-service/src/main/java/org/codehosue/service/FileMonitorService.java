package org.codehosue.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.codehosue.restclient.RestClientWriterService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class FileMonitorService {
    public static final Logger LOGGER = Logger.getLogger(FileMonitorService.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Path filePath = Paths.get("C:\\Users\\Prakash\\Desktop\\2024_INTERVIEW\\sid_global\\reader-service\\src\\main\\resources\\data\\input_data.txt"); // Update with your file path
    private final Path positionFilePath = Paths.get("C:\\Users\\Prakash\\Desktop\\2024_INTERVIEW\\sid_global\\reader-service\\src\\main\\resources\\last_position.txt"); // Update with your file path
    private long lastReadPosition = 0;

    @RestClient
    RestClientWriterService restClientWriterService;

    /**
     * Scheduler that will execute monitoring task in every 5 sec
     */
    public void startMonitoring() {
        scheduler.scheduleAtFixedRate(this::checkForNewLines, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Check for new lines if any new line is added then send it to writer service
     */
    private void checkForNewLines() {
        LOGGER.info("Entering in method FileMonitorService::checkForNewLines -Line: 40");
        try {
            List<String> lines = Files.readAllLines(filePath);
            long startPosition = loadLastReadPosition();
            LOGGER.info("FileMonitorService::checkForNewLines -Line: 44 Value of startPosition: " + lastReadPosition);

            for (int i = (int) startPosition; i < lines.size(); i++) {
                String newLine = lines.get(i);
                // Send new line to write service
                restClientWriterService.sendData(newLine);
            }
            saveLastReadPosition(lines.size());
        } catch (IOException e) {
            LOGGER.info("Exception occured in FileMonitorService::checkForNewLines -Line: 51 Message: " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Returning from method FileMonitorService::checkForNewLines -Line: 53");
    }

    /**
     * Save the last read position to the {positionFilePath} file so that when the application is stopped and rerun, it starts picking up from there.
     *
     * @param position The position to save.
     */
    private void saveLastReadPosition(long position) {
        LOGGER.info("Entering in method FileMonitorService::saveLastReadPosition -Line: 63");
        try {
            Files.writeString(positionFilePath, Long.toString(position));
        } catch (IOException e) {
            LOGGER.info("Exception occured in FileMonitorService::saveLastReadPosition -Line: 66 Message: " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Returning from method FileMonitorService::saveLastReadPosition -Line: 70");
    }

    /**
     * load the last read position from the file
     */
    private long loadLastReadPosition() {
        LOGGER.info("Entering in method FileMonitorService::loadLastReadPosition -Line: 77");

        try {
            if (Files.exists(positionFilePath)) {
                String positionStr = Files.readString(positionFilePath);
                LOGGER.info("FileMonitorService::loadLastReadPosition -Line: 84 Value of positionStr: " + positionStr);
                return Long.parseLong(positionStr);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        LOGGER.info("Returning from method FileMonitorService::loadLastReadPosition -Line: 87");
        return 0;
    }
}
