/**
 * ClassUtils.java
 */
package com.sdc.cli.manager;

import java.util.Set;

import org.reflections.Reflections;

/**
 * @author simone
 * Oct 15, 2019
 */
public class ClassUtils {


    /**
     * 
     * @param packageName
     * @param clazz
     * @return all the {@link Class classes} in the specified package that are sub type of the given {@link Class}
     */

    public static <T> Set<Class<? extends T>> getClasses(String packageName, Class<T> clazz) {
        
        return new Reflections(packageName).getSubTypesOf(clazz);
    
    }
    
}
