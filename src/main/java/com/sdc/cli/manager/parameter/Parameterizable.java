/**
 * Parameterizable.java
 */
package com.sdc.cli.manager.parameter;

import java.util.function.Function;

import org.apache.commons.cli.Option;

/**
 * @author Simone De Cristofaro
 * Feb 5, 2018
 * @param <T>
 */
public interface Parameterizable<T> {


    public boolean hasArg();

    public Class<?> getOptionType();

    public Option createOption();
    
    public String getDescription();

    public String getLongOpt();
    
    public Function<String[], T> getParsingFunction();
    
    public T getDefaultValue();
 
    public boolean isValid(T value);
    
    public boolean isRequired();
}
