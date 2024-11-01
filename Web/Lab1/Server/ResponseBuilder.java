package by.yaroslavesev;

import java.nio.charset.StandardCharsets;

public class ResponseBuilder {

    private static final String RESULT_JSON = """
            {
                "answer": %b,
                "executionTime": "%s"
            }
            """;
    private static final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
    private static final String HTTP_ERROR = """
            HTTP/1.1 %s
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
    private static final String ERROR_JSON = """
            {
                "reason": "%s"
            }
            """;

    public String buildSuccessResponse(boolean isInside, long executionTime) {
        String jsonResponse = String.format(RESULT_JSON, isInside, executionTime);
        return String.format(HTTP_RESPONSE, jsonResponse.getBytes(StandardCharsets.UTF_8).length, jsonResponse);
    }

    public String buildErrorResponse(String status, String message) {
        String errorResponse = String.format(ERROR_JSON, message);
        return String.format(HTTP_ERROR, status, errorResponse.getBytes(StandardCharsets.UTF_8).length, errorResponse);
    }
}
