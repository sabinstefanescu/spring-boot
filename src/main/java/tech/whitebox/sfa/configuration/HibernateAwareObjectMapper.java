package tech.whitebox.sfa.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class HibernateAwareObjectMapper extends ObjectMapper {
    public HibernateAwareObjectMapper() {
        super();
        Hibernate4Module hm = new Hibernate4Module();
        setDateFormat(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH));
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        configure(SerializationFeature.INDENT_OUTPUT, true);
        registerModule(hm);
    }
}