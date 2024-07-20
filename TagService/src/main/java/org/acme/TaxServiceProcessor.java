package org.acme;


import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dto.TaxServiceResponse;
import org.acme.dto.VechicleData;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.HashMap;

@ApplicationScoped
public class TaxServiceProcessor {

    HashMap<String,HashMap<String,String> > data = new HashMap<>();

    TaxServiceProcessor(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("model","ns-200");
        hashMap.put("make","bajaj");
        hashMap.put("licensePlate","MH12 VE 6451");
        hashMap.put("taxAmount","2000");
        data.put("7895433045",hashMap);
    }
    @Incoming("vehicle-data-in")
    @Outgoing("tax-service-response")
    @Blocking
    public TaxServiceResponse process(VechicleData request) {
        TaxServiceResponse response = new TaxServiceResponse();
        response.model = data.get(request.getMobileNumber()).get("model");
        response.make = data.get(request.getMobileNumber()).get("make");
        response.licensePlate = data.get(request.getMobileNumber()).get("licensePlate");
        response.ownerName = request.getName();
        response.ownerMobileNumber = request.getMobileNumber();
        response.taxAmount = Integer.parseInt(data.get(request.getMobileNumber()).get("model"));
        return response;
    }
}


