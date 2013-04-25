package pl.itcrowd.base.framework.view;

import org.jboss.solder.logging.Logger;

import javax.annotation.Nonnull;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionFilter implements Filter {
// ------------------------------ FIELDS ------------------------------

    private Logger logger = Logger.getLogger(ExceptionFilter.class);

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(@Nonnull ServletRequest request, @Nonnull ServletResponse response, @Nonnull FilterChain chain) throws IOException, ServletException
    {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Cought unhandled exception", e);
            if (!response.isCommitted()) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                String url = httpRequest.getContextPath().endsWith("/") ? httpRequest.getContextPath() + "error" : httpRequest.getContextPath() + "/error";
                logger.errorv("Redirecting to {0}", url);
                response.reset();
                httpResponse.sendRedirect(url);
            } else {
                logger.error("Response already committed so we can't do anything more");
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
