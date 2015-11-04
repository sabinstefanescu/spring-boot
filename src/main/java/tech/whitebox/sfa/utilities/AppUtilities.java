/**
 * 
 */
package tech.whitebox.sfa.utilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import tech.whitebox.sfa.configuration.HibernateAwareObjectMapper;
import tech.whitebox.sfa.infrastructure.log.AppLogger;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class AppUtilities {

    // avoids instantiation of class AppUtilities
    private AppUtilities() {
        super();
    }

    public static Float newFloatFromInteger(Integer source) {
        if (source == null) {
            return null;
        } else {
            return new Float(source);
        }
    }

    public static Integer newIntegerFromFloat(Float source) {
        if (source == null) {
            return null;
        } else {
            return (int) Math.floor(source);
        }
    }

    public static Date addMonths(Date startDate, Integer months) {

        Date limitDate = (startDate == null) ? new Date() : startDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(limitDate);
        cal.add(Calendar.MONTH, months);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        limitDate = cal.getTime();

        return limitDate;

    }

    public static Date addDays(Date referenceDate, Integer days) {

        Date date = (referenceDate == null) ? new Date() : referenceDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        date = cal.getTime();

        return date;

    }

    public static Date toDate(String strDate, String strFormat) throws ParseException {

        String fmt = (strFormat == null) ? ApplicationConstants.DATE_FORMAT_DEFAULT : strFormat;
        DateFormat formatter = new SimpleDateFormat(fmt, Locale.ENGLISH);
        return formatter.parse(strDate);

    }

    public static Date trunc(Date date) {

        Date truncDate = new Date(date.getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTime(truncDate);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        truncDate = cal.getTime();

        return truncDate;

    }

    public static List<Date> getDateForFormats(String dateString, List<String> formats) {
        if (dateString == null || "".equals(dateString) || formats == null || formats.isEmpty())
            return null;

        Date myDate = null;
        List<Date> result = new ArrayList<Date>(formats.size());
        for (int i = 0; i < formats.size(); i++) {
            String format = formats.get(i);
            DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
            df.setLenient(false);
            try {
                myDate = df.parse(dateString);
            } catch (ParseException e) {
                myDate = null;
            }
            result.add(i, myDate);
        }

        return result;
    }

    public static Date setDateOnGivenTime(Date date, Date time) {
        if (date == null || time == null)
            return time;
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.YEAR, calDate.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, calDate.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, calDate.get(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    public static String getErrorMessage(Throwable ex) {

        String errMsg = "";

        String message = ex.getMessage();
        String toString = ex.toString();

        if (message != null) {
            if (message.length() > toString.length()) {
                errMsg = message;
            } else {
                errMsg = toString;
            }
        }

        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        errMsg = errMsg + " | " + stackTrace;

        return errMsg;

    }

    // rounds to 2 decimal places
    public static Float roundToDecimalPlaces(Float value) {
        return new Float(Math.round(value * 100.0) / 100.0);
    }

    // ceil to nearest 0.5 multiple
    public static Float ceilToNearestMultiple(Float value) {
        return new Float(Math.ceil(value * 2.0) / 2.0);
    }

    public static final Object unwrapProxy(Object bean) {

        /*
         * If the given object is a proxy, set the return value as the object being proxied, otherwise return the given object.
         */
        Object localBean = bean;

        if (AopUtils.isAopProxy(localBean) && localBean instanceof Advised) {

            Advised advised = (Advised) localBean;

            try {
                localBean = advised.getTargetSource().getTarget();
            } catch (Exception e) {
                return localBean;
            }
        }

        return localBean;
    }

    public static boolean shouldSkipRegularAuthentication(String path) {
        for (String s : ApplicationConstants.BASIC_AUTHENTICATION_PATHS){
            if (path.startsWith(s)){
                return true;
            }
        }
        return false;
    }
    
    public static String getStringBodyFromRequest(HttpServletRequest req, AppLogger log){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();

            String line;
            line = reader.readLine();
            
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            
            reader.reset();
            // do NOT close the reader here, or you won't be able to get the post data twice
        } catch(IOException e) {
            log.debug("INVALID_REQUEST_BODY", AppUtilities.getErrorMessage(e));  // This has happened if the request's reader is closed    
        }
        
        return sb.toString();
    }
    
    public static String convertObjectToJson(Object object) {
        HibernateAwareObjectMapper mapper = new HibernateAwareObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String serializedObject = null;

        try {
            serializedObject = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "Could not serialize!";
        }

        return serializedObject;
    }

    public static <T> T convertJsonToObject(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(json, clazz);
    }
    
    public static String trimOrFillStringToFixedSize(String string, Integer size){
        
        if (string == null){
            return String.format("%"+size + "s", "");
        }
        
        if (string.length() < size){
            return String.format("%"+size + "s", string);
        }
        
        if (string.length() > size){
            return string.substring(0, size);
        }
        
        return string;
    }
    
    public static String getStringFromInputStream(InputStream in) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result, line = reader.readLine();
        result = line;
        while((line = reader.readLine()) != null) {
            result += line;
        }
        
        return result;
    }

    public static String getAllHeadersFromRequest(HttpServletRequest req) {

        StringBuilder sb = new StringBuilder();

        Enumeration<String> headerNames = req.getHeaderNames();

        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            sb.append("[");
            sb.append(headerName);
            sb.append(":");

            Enumeration<String> headers = req.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                sb.append(headerValue);
                sb.append(";");
            }
            sb.append("],");

        }

        return sb.toString();
    }

    public static String inputStreamToString(ServletInputStream inputStream) throws IOException {
        Charset utf8Charset = Charset.forName("UTF-8");
        StringBuilder inputStringBuilder = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, utf8Charset));
        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }

        return(inputStringBuilder.toString());

    }

    public static String getUsernameCamelCase(String username, AppLogger log){
        StringBuilder result = new StringBuilder();
        if (username != null && !username.isEmpty()) {
            String[] names = username.split("\\.");
            if (names.length == 2) {
                String firstName = names[0];
                result.append(firstName.substring(0, 1).toUpperCase()).append(firstName.substring(1, firstName.length()));
                result.append(".");
                String lastName = names[1];
                result.append(lastName.substring(0, 1).toUpperCase()).append(lastName.substring(1, lastName.length()));
            } else {
                log.warn("USERNAME_CAMELCASE", "Username " + username + " doesn't respect the format [firstname].[lastname]");
                return username;
            }
        }
        return result.toString();
    }
}
