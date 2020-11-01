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
public abstract class ParameterFloat extends Parameter<Float>{

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], Float> getParsingFunction() {
    
        return new Function<String[], Float>() {

            @Override
            public Float apply(String[] t) {

                return Float.parseFloat(t[0]);
                
            }
            
        };
        
    }
    
}
