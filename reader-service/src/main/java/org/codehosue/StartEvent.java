package org.codehosue;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.codehosue.service.FileMonitorService;

@ApplicationScoped
public class StartEvent {

    @Inject
    FileMonitorService readerService;

    /**
     * Method called when the application starts.
     * Starts the file monitoring task.
     *
     * @param startupEvent The event triggered on startup.
     */
    public void startFileEvent(@Observes StartupEvent startupEvent) {
        readerService.startMonitoring();
    }
}
