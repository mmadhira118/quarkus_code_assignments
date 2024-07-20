package org.codehosue.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.codehosue.service.WriterService;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/write")
public class WriterResource {
    public static final Logger LOGGER = Logger.getLogger(WriterResource.class);

    @Inject
    WriterService writerService;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void writeData(String data) {
        LOGGER.info("Entering in method WriterResource::writeData -Line: 19");

        writerService.writeData(data);

        LOGGER.info("Returning from method WriterResource::writeData -Line: 21");
    }

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReport() {
        LOGGER.info("Entering in method WriterResource::getReport -Line: 31");

        List<String> auditEntries = writerService.getReport();

        LOGGER.info("Returning from method WriterResource::getReport -Line: 33");
        return Response.ok(auditEntries).build();
    }
}