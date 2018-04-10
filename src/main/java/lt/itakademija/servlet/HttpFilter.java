package lt.itakademija.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Http filter.
 *
 * @author mariusg
 */
public abstract class HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter(new MultiReadHttpServletRequest((HttpServletRequest) request),
                (HttpServletResponse) response,
                chain);
    }

    protected abstract void doFilter(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {
    }

}
