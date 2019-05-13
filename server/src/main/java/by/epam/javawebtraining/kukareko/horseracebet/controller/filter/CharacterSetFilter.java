package by.epam.javawebtraining.kukareko.horseracebet.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Yulya Kukareko
 * @version 1.0 16 Apr 2019
 */
public class CharacterSetFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }
}
