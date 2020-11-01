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
public abstract class ParameterString extends Parameter<String>{

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], String> getParsingFunction() {
    
        return new Function<String[], String>() {

            @Override
            public String apply(String[] t) {

                return t[0];
                
            }
            
        };
        
    }
    
}
