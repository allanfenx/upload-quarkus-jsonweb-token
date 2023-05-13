package br.com.upload.controller;


import br.com.upload.dto.AuthDto;
import br.com.upload.dto.TokenDto;
import br.com.upload.dto.UserDto;
import br.com.upload.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService service;

    @POST
    @Path("users")
    public Response register(UserDto dto){

        try {
            String result = service.register(dto);

            return  Response.ok(result, MediaType.TEXT_PLAIN).status(201).build();
        }catch (RuntimeException e){
            return  Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(400).build();
        }
    }

    @POST
    @Path("auth")
    public Response auth(AuthDto dto){

        try {
            TokenDto result = service.auth(dto);

            return  Response.ok(result).build();
        }catch (RuntimeException e){
            return  Response.ok(e.getMessage(), MediaType.TEXT_PLAIN).status(400).build();

        }
    }
}
