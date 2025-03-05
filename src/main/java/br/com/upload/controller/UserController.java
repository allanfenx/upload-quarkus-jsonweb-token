package br.com.upload.controller;

import java.util.HashMap;

import br.com.upload.dto.AuthDto;
import br.com.upload.dto.UserDto;
import br.com.upload.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService service;

    @POST
    @Path("users")
    public Response register(UserDto dto) {

        try {
            String result = service.register(dto);

            return Response.ok(result, MediaType.TEXT_PLAIN).status(201).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(400).build();
        }
    }

    @POST
    @Path("auth")
    public Response auth(AuthDto dto) {

        try {
            HashMap<String, String> result = service.auth(dto);

            return Response.ok(result).build();
        } catch (RuntimeException e) {
            return Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(400).build();

        }
    }
}
