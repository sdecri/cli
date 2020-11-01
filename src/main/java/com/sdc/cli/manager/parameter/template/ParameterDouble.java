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
public abstract class ParameterDouble extends Parameter<Double>{

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], Double> getParsingFunction() {
    
        return new Function<String[], Double>() {

            @Override
            public Double apply(String[] t) {

                return Double.parseDouble(t[0]);
                
            }
            
        };
        
    }
    
}
