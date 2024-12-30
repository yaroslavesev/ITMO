package by.yaroslavesev.backend.controllers;

import by.yaroslavesev.backend.DTO.PointDTO;
import by.yaroslavesev.backend.models.Point;
import by.yaroslavesev.backend.services.PointService;
import by.yaroslavesev.backend.utils.AuthUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/main")
@ApplicationScoped
public class PointResource {

    @Inject
    private PointService pointService;

    @POST
    @Path("/addPoint")
    public Response addPoint(PointDTO pointDTO, @Context HttpHeaders headers) {
        try {
            String token = headers.getHeaderString("Authorization").substring("Bearer ".length());
            String username = AuthUtils.getUsernameFromToken(token);

            Point savedPoint = pointService.save(pointDTO, username);
            PointDTO responseDTO = new PointDTO(
                    savedPoint.getX(),
                    savedPoint.getY(),
                    savedPoint.getR(),
                    savedPoint.isHitStatus(),
                    savedPoint.getDate().toString()
            );
            return Response.ok(responseDTO).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/getAll")
    public Response getAllPoints(@Context HttpHeaders headers) {
        try {
            String token = headers.getHeaderString("Authorization").substring("Bearer ".length());
            String username = AuthUtils.getUsernameFromToken(token);

            List<Point> points = pointService.getAllPointsForUser(username);
            return Response.ok(points).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }


}