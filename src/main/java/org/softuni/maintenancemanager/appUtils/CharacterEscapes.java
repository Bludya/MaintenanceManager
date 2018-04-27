package org.softuni.maintenancemanager.appUtils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.Field;

//TODO: make it library so it is not repeating when making microservices
public class CharacterEscapes {

    public static <T> T escapeStringFields(T object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getType() == String.class && !java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                field.set(object, escapeString((String) field.get(object)));
                field.setAccessible(false);
            }
        }
        return object;
    }

    public static String escapeString(String string) {
        string = StringEscapeUtils.escapeHtml4(string);
        string = StringEscapeUtils.unescapeEcmaScript(string);
        return string;
    }

}
