package tech.whitebox.sfa.infrastructure.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import tech.whitebox.sfa.configuration.RequestData;
import tech.whitebox.sfa.utilities.AppUtilities;

@Configurable
public class AppLogger {

    private Logger log;
    private String className = "unknown";

    private Integer userId;

    @Autowired
    private RequestData requestData;

    public AppLogger(Object clasa) {

        className = clasa.getClass().getName();
        log = LoggerFactory.getLogger(clasa.getClass());

        userId = 0;
    }

    public void debug(String logAction, String logComments) {
        prepareSessionParams();

        debug(userId, logAction, logComments, className);
    }

    public void debug(String logAction, String logComments, String className) {

        prepareSessionParams();

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.debug(message.logComments);
    }

    public void debug(Integer userId, String logAction, String logComments, String className) {

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.debug(message.logComments);
    }

    public void info(String logAction, String logComments) {
        prepareSessionParams();

        info(userId, logAction, logComments, className);
    }

    public void info(String logAction, String logComments, String className) {
        prepareSessionParams();

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.info(message.logComments);
    }

    public void info(Integer userId, String logAction, String logComments, String className) {

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.info(message.logComments);
    }

    public void error(String logAction, String logComments) {
        prepareSessionParams();

        error(userId, logAction, logComments, className);
    }

    public void error(String logAction, String logComments, String className) {
        prepareSessionParams();

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.error(message.logComments);
    }

    public void error(Integer userId, String logAction, String logComments, String className) {        prepareSessionParams();

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.error(message.logComments);
    }

    public void warn(String logAction, String logComments) {
        prepareSessionParams();

        warn(userId, logAction, logComments, className);
    }

    public void warn(String logAction, String logComments, String className) {
        prepareSessionParams();

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.warn(message.logComments);
    }

    public void warn(Integer userId, String logAction, String logComments, String className) {

        LogMessage message = new LogMessage(userId, logAction, logComments, className);
        log.warn(message.logComments);
    }
    
    public void errorWithCause(String logAction, Throwable e) {
        
        this.error(logAction, AppUtilities.getErrorMessage(e));
        this.error("CAUSED_BY_4_" + logAction, AppUtilities.getErrorMessage(getOriginalCausedBy(e)));        
    
    }


    private void prepareSessionParams() { // Needs to be retrieved at every invocation
        if (requestData != null && requestData.getUser() != null) {
            userId = requestData.getUser().getId();
        } else {
            userId = 0;
        }
    }

    private Throwable getOriginalCausedBy(Throwable ex) {

        Throwable causedBy = ex.getCause();
        if (causedBy != null) {
            return getOriginalCausedBy(causedBy); // search further down
        } else {
            return ex; // no cause, return original excption (own cause)
        }

    }

}
