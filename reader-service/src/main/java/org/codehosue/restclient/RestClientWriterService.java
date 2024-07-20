package org.codehosue.restclient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8082") // Ideally we should place in properties file
public interface RestClientWriterService {
    @POST
    @Path("/write")
    @Consumes(MediaType.TEXT_PLAIN)
    void sendData(String data);

    // We should also implement fault tolerance and retries
}
