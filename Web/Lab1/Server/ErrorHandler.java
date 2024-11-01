package by.yaroslavesev;

public class ErrorHandler {

    private final ResponseBuilder responseBuilder = new ResponseBuilder();

    public String handleInvalidParameters() {
        return responseBuilder.buildErrorResponse("400 Bad Request", "Invalid parameters");
    }

    public String handleMissingParameters() {
        return responseBuilder.buildErrorResponse("400 Bad Request", "Missing parameters");
    }

    public String handleServerError() {
        return responseBuilder.buildErrorResponse("500 Internal Server Error", "Server error");
    }
}
