package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import tools.AppContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Nick on 13/03/2015.
 */
public class MmiAplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatherContext = new AnnotationConfigWebApplicationContext();
        dispatherContext.register(ServletConfig.class );

        ServletRegistration.Dynamic dispatcher= servletContext.addServlet("dispatcher", new DispatcherServlet(dispatherContext));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);


    }
}
