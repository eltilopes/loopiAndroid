package br.com.aio.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by elton on 24/09/2017.
 */

public class SpinnerUtils {

    public static Object getObjectDefaultSpinner(Class aClass ) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object o = generify(aClass);
            try {
                Constructor<?> ctor = aClass.getConstructor(Integer.class, String.class);
                o = ctor.newInstance(new Object[] {0,"Selecione"  });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        return o;
    }

    private static <T> Class<T> generify(Class<?> cls) {
        return (Class<T>)cls;
    }
}
