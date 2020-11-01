/**
 * ParameterString.java
 */
package com.sdc.cli.manager.parameter.template;

import java.time.Duration;
import java.util.function.Function;

import com.sdc.cli.manager.parameter.Parameter;

/**
 * @author Simone.DeCristofaro
 * Oct 12, 2020
 */
public abstract class ParameterDuration extends Parameter<Duration>{

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], Duration> getParsingFunction() {
    
        return new Function<String[], Duration>() {

            @Override
            public Duration apply(String[] input) {

                return Duration.parse(input[0]);
                
            }
            
        };
        
    }
    
}
