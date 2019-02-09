package boassoft.util;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleUtil {

	public static Locale getLocale() {
        return LocaleContextHolder.getLocale(); 
    }  
    
    public static String getLanguage() {
    	String language = "";
    	
    	Locale locale = LocaleContextHolder.getLocale();
    	
    	if (locale != null) {
    		language = locale.getLanguage();
    	}
    	
    	if (language != null) {
    		language = language.toUpperCase();
    	}
    	
    	if ("KO".equals(language) 
    			|| "KR".equals(language) 
    			|| "KOR".equals(language)) {
    		language = "KO";
    	} else if ("EN".equals(language) 
    			|| "ENG".equals(language)) {
    		language = "EN";
    	} else {
    		language = "EN";
    	}
    	
        return language; 
    }
}
