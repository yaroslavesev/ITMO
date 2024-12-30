package by.yaroslavesev.lab2.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();
        if (path.equals("/controller") || path.endsWith(".css") || path.endsWith(".js")  || path.endsWith(".png")) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/controller").forward(request, response);
        }
    }

    @Override
    public void destroy() {}
}
    