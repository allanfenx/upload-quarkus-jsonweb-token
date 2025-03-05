package br.com.upload.controller;

import java.io.IOException;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.security.RolesAllowed;

import br.com.upload.entity.FormData;
import br.com.upload.entity.Profile;
import br.com.upload.service.ProfileService;

@Path("uploads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ProfileController {

    @Inject
    ProfileService service;

    @GET
    @RolesAllowed("User")
    public Response listUploads() {

        List<Profile> profiles = service.listUploads();

        return Response.ok(profiles).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed("User")
    public Response findOne(@PathParam("id") Long id) {

        try {
            Profile profile = service.findOne(id);

            return Response.ok(profile).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(404).build();
        }
    }

    @POST
    @RolesAllowed("User")
    public Response sendUpload(@BeanParam FormData data) {

        try {
            Profile profile = service.sendUpload(data);

            return Response.ok(profile).status(201).build();
        } catch (IOException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(401).build();
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("User")
    public Response removeUpload(@PathParam("id") Long id) {

        try {
            service.removeUpload(id);

            return Response.status(204).build();
        } catch (IOException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(404).build();
        }
    }
}
