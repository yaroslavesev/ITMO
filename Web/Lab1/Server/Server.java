import com.fastcgi.FCGIInterface;
import com.fastcgi.FCGIInputStream;
import com.fastcgi.FCGIOutputStream;
import com.fastcgi.FCGIRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        System.err.println("FastCGI server started");

        FCGIInterface fcgi = new FCGIInterface();
        ResponseBuilder responseBuilder = new ResponseBuilder();
        ErrorHandler errorHandler = new ErrorHandler();

        while (fcgi.FCGIaccept() >= 0) {
            long startTime = System.nanoTime();
            try {
                System.err.println("Received a new request");
                FCGIRequest request = FCGIInterface.request;

                FCGIInputStream fcgiIn = request.inStream;
                FCGIOutputStream fcgiOut = request.outStream;

                BufferedReader br = new BufferedReader(new InputStreamReader(fcgiIn, StandardCharsets.UTF_8));
                PrintStream out = new PrintStream(fcgiOut, true, StandardCharsets.UTF_8);

                String requestURI = request.params.getProperty("REQUEST_URI");
                if (requestURI == null || !requestURI.equals("/fcgi-bin/Server.jar")) {
                    String response = errorHandler.handleInvalidPath();
                    out.print(response);
                    out.flush();
                    br.close();
                    out.close();
                    continue;
                }

                String contentLengthStr = request.params.getProperty("CONTENT_LENGTH");
                int contentLength = 0;
                if (contentLengthStr != null && !contentLengthStr.isEmpty()) {
                    contentLength = Integer.parseInt(contentLengthStr);
                }

                StringBuilder requestBodyBuilder = new StringBuilder();
                for (int i = 0; i < contentLength; i++) {
                    int c = br.read();
                    if (c == -1) {
                        break;
                    }
                    requestBodyBuilder.append((char) c);
                }
                String requestBody = requestBodyBuilder.toString();
                Map<String, String> params = RequestParser.parseQueryString(requestBody);
                String xParam = params.get("x");
                String yParam = params.get("y");
                String rParam = params.get("r");
                if (xParam != null && yParam != null && rParam != null) {
                    try {
                        String x = (xParam.trim());
                        int y = Integer.parseInt(yParam.trim());
                        double r = Double.parseDouble(rParam.trim());
                        if ((!Validation.isValidX(x))) {
                            String response = errorHandler.handleInvalidParameters();
                            out.print(response);
                            out.flush();
                            continue;
                        }
                        double x1 = Double.parseDouble(x);
                        boolean isInside = Validation.checkPoint(x1, y, r);
                        String response = responseBuilder.buildSuccessResponse(isInside, System.nanoTime() - startTime);
                        out.print(response);
                        out.flush();

                    } catch (NumberFormatException e) {
                        String response = errorHandler.handleInvalidParameters();
                        out.print(response);
                        out.flush();
                    }
                } else {
                    String response = errorHandler.handleMissingParameters();
                    out.print(response);
                    out.flush();
                }

                br.close();
                out.close();

            } catch (Exception e) {
                System.err.println("Server error: " + e.getMessage());
                try {
                    FCGIOutputStream fcgiOut = FCGIInterface.request.outStream;
                    PrintStream out = new PrintStream(fcgiOut, true, StandardCharsets.UTF_8);
                    String response = errorHandler.handleServerError();
                    out.print(response);
                    out.flush();
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
            }
        }
    }
}
