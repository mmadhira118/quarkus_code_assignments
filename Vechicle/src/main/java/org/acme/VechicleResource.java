package org.acme;




import io.smallrye.reactive.messaging.annotations.Channel;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.VechicleData;
import org.acme.entity.Vehicle;
import org.acme.service.VechicleService;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VechicleResource {

    @Inject
    @Channel("vehicle-data-out")
    Emitter<VechicleData> emitter;

    @Inject
    VechicleService vechicleService;
    @POST
    public Response addVehicle(VechicleData request) {
        emitter.send(request);
        return Response.ok(Vehicle.findByMobileNumber(request.getMobileNumber())).status(Response.Status.CREATED).build();
    }
}

