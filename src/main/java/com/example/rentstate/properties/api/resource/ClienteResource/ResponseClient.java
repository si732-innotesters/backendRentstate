package com.example.rentstate.properties.api.resource.ClienteResource;

import com.example.rentstate.properties.api.resource.propertyResource.ResponsePropertyResource;
import com.example.rentstate.properties.domain.model.entities.Client;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseClient {
    private String name;
    private String lastName;
    private String gender;
    private Boolean isAsset;
    private ResponsePropertyResource property;
    private Date createdAt;

    public ResponseClient(Client client){
        this.name = client.getTenant().getName();
        this.lastName = client.getTenant().getLastName();
        this.gender = client.getTenant().getGender();
        this.isAsset = client.getIsAsset();
        this.property = new ResponsePropertyResource(client.getRentedProperty());
        this.createdAt = client.getCreatedAt();
    }
}
