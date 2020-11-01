/**
 * ParameterString.java
 */
package com.sdc.cli.manager.parameter.template;

import java.util.function.Function;

import com.sdc.cli.manager.parameter.Parameter;

/**
 * @author Simone.DeCristofaro
 * Oct 12, 2020
 */
public abstract class ParameterBoolean extends Parameter<Boolean>{

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], Boolean> getParsingFunction() {
    
        return new Function<String[], Boolean>() {

            @Override
            public Boolean apply(String[] input) {

                return Boolean.parseBoolean(input[0]);
                
            }
            
        };
        
    }
    
}
