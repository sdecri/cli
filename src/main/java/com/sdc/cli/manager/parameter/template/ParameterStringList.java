/**
 * java
 */
package com.sdc.cli.manager.parameter.template;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.sdc.cli.manager.parameter.Parameter;


public abstract class ParameterStringList extends Parameter<List<String>> {
    
    public static final String SEPARATOR = ",";
    
    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], List<String>> getParsingFunction() {

        return new Function<String[], List<String>>() {

            @Override
            public List<String> apply(String[] input) {

                return Arrays.asList(input[0].split(SEPARATOR));
                
            }};
        
    }
    
}
