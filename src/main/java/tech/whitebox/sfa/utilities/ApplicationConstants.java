package tech.whitebox.sfa.utilities;

import java.sql.Types;
import java.util.*;

public final class ApplicationConstants {
    
    
    public static final String CONFIGURATION_PATH = "CAMA_CONFIG";
    
    public static final Map<Class<?>, Integer> CLASSES_SQL_TYPES = new HashMap<Class<?>, Integer>();
    static {
        CLASSES_SQL_TYPES.put(Integer.class, Types.INTEGER);
        CLASSES_SQL_TYPES.put(String.class, Types.VARCHAR);
        CLASSES_SQL_TYPES.put(Date.class, Types.DATE);
    }  
    
    private ApplicationConstants(){}

    public static final String DATE_FORMAT_DEFAULT = "dd/MM/yyyy";
    
    public static final String DATE_FORMAT_ALT = "dd/MM/yyyy hh:mm:ss.S";
    
    public static final String DATE_FORMAT_FILES_EXPORT = "ddMMyy";
    
    public static final String AUTHENTICATION_ACTION = "authenticate";
    public static final String PREAUTHENTICATION_ACTION = "preauthenticate";
    
    public static final Collection<String> ACTIONS_ALLOWED = new HashSet<String>();
    static {
        ACTIONS_ALLOWED.add("AuthenticationController.authenticate");
        ACTIONS_ALLOWED.add("AuthenticationController.preauthenticate");
        ACTIONS_ALLOWED.add("AuthenticationController.logout");        
    }
    
    public static final String BROWSER_NOT_SUPPORTED_URL = "/WEB-INF/browserNotSupported.html";
    
    public static final Collection<String> BROWSER_NAMES = new HashSet<String>(3);
    static {
        BROWSER_NAMES.add("firefox");
        BROWSER_NAMES.add("chrome");
        BROWSER_NAMES.add("opera");
        BROWSER_NAMES.add("ie");
    }    
       
    public static final String CLIENT_INFO = "$P_CLIENT_INFO";
    public static final String MULTIAUTH_APPLICATION_NAME = "CAMA";
    public static final String MULTIAUTH_ROLE_SEPARATOR = ";";
    
    public static final String REDIRECT_TOKEN = "/goto";
    public static final String REDIRECT_PATH = "REDIRECT_PATH";
    
    public static final String LOGOUT_ACTION = "/logout";
    public static final String MULTIAUTH_TOKEN = "MULTIAUTH_TOKEN";
    public static final String HOME_ACTION_ACCESSIBLE = "$P_HOME_ACTION_ACCESSIBLE";

    public static final String AUTH_TOKEN_HEADER = "X-AUTH_TOKEN";

    
    public static final float MAP_LOAD_FACTOR = 0.75f;
    
    public static final String DISABLED_CLASS = "disabled";
    
    public static final String BANK_CODE = "BRD";
    
    public static final String ANGULAR_HEADER = "X-angular";
    
    public static final Date MAX_DATE = new Date() {
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 3000);       

        setTime(cal.getTimeInMillis());
    } }; 
    
    // ALERT TYPES
    public static final int ALERT_TYPE_EMAIL = 1;
    public static final int ALERT_TYPE_NOTIFICATION = 2;
    public static final int ALERT_TYPE_EMAIL_AND_NOTIFICATION = 3;

    public static final Integer NO_OF_ALERTS_ON_HOMEPAGE = 6;
    
    public static final int TRANSACTION_RETRY_COUNT = 3;
    
    
    //PriceBox constants
    public static final String PRICEBOX_URL = "http://jboss7dev01pricebox.devcrm.orange.intra";
    public static final String PRICEBOX_PORT = "8080";
    public static final String PRICEBOX_PATH = "PriceBoxSmart/webservice/solution";
    public static final String PRICEBOX_USERNAME= "smart.cama";
    public static final String PRICEBOX_PASSWORD = "smartbox";

    public static final Integer MONTHS_OPTION_IS_ELIGIBLE_SINCE_START_DATE = 3;
    
    //User Profile Constants
    public static final String ADMIN_PROFILE = "ADMINISTRATION";
    public static final String MAJOR_PROFILE = "MAJOR";

    public static final List<String> BASIC_AUTHENTICATION_PATHS = new ArrayList<String>(3);
    static {
        BASIC_AUTHENTICATION_PATHS.add("/api/webhooks");
        BASIC_AUTHENTICATION_PATHS.add("/api/fraud-check");
        BASIC_AUTHENTICATION_PATHS.add("/api/transactions");
    }
    
    public static final Integer DEFAULT_CONNECTION_TIMEOUT = 3000;
}
