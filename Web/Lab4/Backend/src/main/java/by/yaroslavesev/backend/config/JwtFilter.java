//package by.yaroslavesev.backend.config;
//
//import io.jsonwebtoken.Jwts;
//
//import jakarta.annotation.Priority;
//import jakarta.ws.rs.container.ContainerRequestContext;
//import jakarta.ws.rs.container.ContainerRequestFilter;
//import jakarta.ws.rs.ext.Provider;
//import jakarta.ws.rs.core.Response;
//import java.io.IOException;
//import java.util.ResourceBundle;
//
//@Provider
//@Priority(1)
//public class JwtFilter implements ContainerRequestFilter {
//
//    private static final String SECRET_KEY;
//
//    static {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
//        SECRET_KEY = resourceBundle.getString("jwt.secret");
//    }
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String authHeader = requestContext.getHeaderString("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            requestContext.abortWith(
//                    Response.status(Response.Status.UNAUTHORIZED)
//                            .entity("{\"message\": \"Authorization token is required\"}")
//                            .build()
//            );
//            return;
//        }
//
//        String token = authHeader.substring("Bearer".length()).trim();
//
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//        } catch (Exception e) {
//            requestContext.abortWith(
//                    Response.status(Response.Status.UNAUTHORIZED)
//                            .entity("{\"message\": \"Invalid token\"}")
//                            .build()
//            );
//        }
//    }
//}
package by.yaroslavesev.backend.config;

import io.jsonwebtoken.Jwts;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Provider
@Priority(1)
public class JwtFilter implements ContainerRequestFilter {

    private static final String SECRET_KEY;
    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/auth/login", "/auth/register");

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        SECRET_KEY = resourceBundle.getString("jwt.secret");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (EXCLUDED_PATHS.contains(path)) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"message\": \"Authorization token is required\"}")
                            .build()
            );
            return;
        }

        String token = authHeader.substring("Bearer".length()).trim();

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"message\": \"Invalid token\"}")
                            .build()
            );
        }
    }
}
