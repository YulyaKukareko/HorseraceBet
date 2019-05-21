package by.epam.javawebtraining.kukareko.horseracebet.filter;

import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.GeneralConstants;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Yulya Kukareko
 * @version 1.0 16 Apr 2019
 */
public class CharacterSetFilter implements Filter {

    private String encoding;

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
