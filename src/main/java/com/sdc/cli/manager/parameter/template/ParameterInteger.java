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
public abstract class ParameterInteger extends Parameter<Integer>{

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], Integer> getParsingFunction() {
    
        return new Function<String[], Integer>() {

            @Override
            public Integer apply(String[] t) {

                return Integer.parseInt(t[0]);
                
            }
            
        };
        
    }
    
}
