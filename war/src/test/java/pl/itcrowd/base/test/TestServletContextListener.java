package pl.itcrowd.base.test;

import org.jboss.seam.transaction.TransactionServletListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TestServletContextListener implements ServletContextListener {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ServletContextListener ---------------------

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        sce.getServletContext().setInitParameter(TransactionServletListener.DISABLE_LISTENER_PARAM, "true");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
    }
}
