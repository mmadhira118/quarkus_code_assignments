package org.codehosue.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.codehosue.repository.WriterRepository;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class WriterService {
    public static final Logger LOGGER = Logger.getLogger(WriterService.class);

    @Inject
    WriterRepository writerRepository;

    private final AtomicInteger lineCount = new AtomicInteger(0);

    public void writeData(String data) {
        LOGGER.info("Entering in method WriterService::writeData -Line: 23");

        writerRepository.writeData(data);
        lineCount.incrementAndGet();

        LOGGER.info("Returning from method WriterService::writeData -Line: 26");
    }

    public List<String> getReport() {
        LOGGER.info("Entering in method WriterService::getReport -Line: 28");

        List<String> report = writerRepository.getReport();

        LOGGER.info("Returning from method WriterService::getReport -Line: 30");
        return report;
    }
}
