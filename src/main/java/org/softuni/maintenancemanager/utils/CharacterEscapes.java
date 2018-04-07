package org.softuni.maintenancemanager.utils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.Field;

//TODO: make it library so it is not repeating when making microservices
public class CharacterEscapes {

    public static <T> T escapeStringFields(T object) throws IllegalAccessException {
        for(Field field : object.getClass().getFields()){
            if(field.getType() == String.class){
                field.set(String.class,escapeString((String)field.get(String.class)));
            }
        }
        return object;
    }

    public static String escapeString(String string){
        string = StringEscapeUtils.escapeHtml4(string);
        string = StringEscapeUtils.unescapeEcmaScript(string);
        return string;
    }

}
