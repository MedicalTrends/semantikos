package cl.minsal.semantikos.kernel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 11-07-16.
 */
public class StringUtils {

    public static String lowerCaseToCamelCaseJSON(String json){

        Pattern p = Pattern.compile( "_([a-zA-Z])" );
        Matcher m = p.matcher( json );
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        return m.appendTail(sb).toString();
    }
}
