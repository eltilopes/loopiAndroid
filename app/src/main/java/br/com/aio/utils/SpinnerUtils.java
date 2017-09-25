package br.com.aio.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

    public static <T> Class<T> generify(Class<?> cls) {
        return (Class<T>)cls;
    }

    public static List<Object> getListaObjects(List objects, Class aClass ) {
        List lista = new ArrayList<>();
        try {
            lista.add( SpinnerUtils.getObjectDefaultSpinner(aClass));
        }catch (Exception e){
            e.printStackTrace();
        }
        lista.addAll(objects);
        return lista;
    }
}
