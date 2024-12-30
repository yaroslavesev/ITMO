package by.yaroslavesev.backend.controllers;

import by.yaroslavesev.backend.DTO.AuthDTO;
import by.yaroslavesev.backend.services.AuthService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


import jakarta.inject.Inject;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
@ApplicationScoped
public class AuthResource {

    @Inject
    private AuthService authService;

    @POST
    @Path("/register")
    public Response register(AuthDTO authDTO) {
        try {
            authService.register(authDTO);
            return Response.ok("{\"message\": \"User registered successfully\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/login")
    public Response login(AuthDTO authDTO) {
        try {
            String token = authService.login(authDTO);
            return Response.ok("{\"token\": \"" + token + "\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
