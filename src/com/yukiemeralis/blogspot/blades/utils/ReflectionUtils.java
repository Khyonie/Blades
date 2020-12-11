package com.yukiemeralis.blogspot.blades.utils;

import java.lang.reflect.Field;

public class ReflectionUtils 
{
    @SuppressWarnings("rawtypes")
    public static Object getPrivateField(String fieldName, Class class_, Object object)
    {
        Field field;
        Object o = null;

        try {
            field = class_.getField(fieldName);
            field.setAccessible(true);

            o = field.get(object);
        } catch (NoSuchFieldException error) {
            error.printStackTrace();
        } catch (IllegalAccessException error) {
            error.printStackTrace();
        }

        if (o == null)
            System.out.println("[ WARN ] Field retrieved \"" + fieldName + "\" in class \"" + class_.getName() + "\" returned null!");
        return o;
    }
}
