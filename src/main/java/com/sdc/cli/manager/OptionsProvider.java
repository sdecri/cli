/**
 * ServiceOptionsProvider.java
 */
package com.sdc.cli.manager;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sdc.cli.manager.parameter.Parameter;
import com.sdc.cli.manager.parameter.Parameterizable;

/**
 * @author Simone De Cristofaro
 * Feb 9, 2018
 */
public class OptionsProvider {

    
    private String[] packages;
    
    /**
     * @param packages
     */
    public OptionsProvider(String...packages) {

        super();
        this.packages = packages;
    }

    

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Parameterizable<?>> getOptions() {

        Set<Class<? extends Parameterizable>> classes = new HashSet<>();
        for (String packageName : packages) {
            classes.addAll(ClassUtils.getClasses(packageName, Parameterizable.class));
        }
        
        return classes.stream()
                .filter(c -> Parameterizable.class.isAssignableFrom(c) && !c.equals(Parameter.class) && !Modifier.isAbstract(c.getModifiers()))
                .map(c -> {
                    try {
                        return ((Class<? extends Parameterizable<?>>) c).newInstance();
                    }
                    catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException("Error defining list of available command line parameters", e);
                    }
                })
                .collect(Collectors.toList());

        
    }
}
