package local.tin.examples.jetty.embedded.logging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import local.tin.examples.jetty.embedded.logging.web.JettyLoggingServer;
import org.apache.log4j.Logger;

public class Application {

    public static final Logger LOGGER = Logger.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        JettyLoggingServer productServer = new JettyLoggingServer(new Configuration());
        Thread thread = new Thread(productServer);
        showInitParameters(productServer);
        try {
            thread.start();
            while (!Thread.currentThread().isInterrupted()) {}
        } finally {
            productServer.stopServer();
            productServer.destroyServer();
        }

    }

    public static void showInitParameters(JettyLoggingServer productServer) throws ClassNotFoundException, SecurityException {
        Map<String, String> initParameters = productServer.getInitParameters();

        LOGGER.info("Init parameters:");
        for (Map.Entry<String, String> entry : initParameters.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
            for (String currentClass : entry.getValue().split(";")) {
                Class klazz = Class.forName(currentClass);
                LOGGER.info("For class: " + currentClass + " we have the methods:");
                for (Method method : klazz.getDeclaredMethods()) {
                    LOGGER.info("\t" + method.getName() + ":");
                    LOGGER.info("\t\tAnnotations:");
                    for (Annotation annotation : method.getDeclaredAnnotations()) {
                        LOGGER.info("\t\t\t" + annotation.toString());
                    }
                    LOGGER.info("\t\tParameters by getParameterTypes():");
                    for (Class parameterType : method.getParameterTypes()) {
                        LOGGER.info("\t\t\t" + parameterType.getCanonicalName());
                    }
                    LOGGER.info("\t\tParameters by getParameters():");
                    for (Parameter parameterType : method.getParameters()) {
                        LOGGER.info("\t\t\t" + parameterType.getName() + ", " + parameterType.getType().getName() + ", " + parameterType.isNamePresent());
                    }
                }
            }

        }
    }
}
