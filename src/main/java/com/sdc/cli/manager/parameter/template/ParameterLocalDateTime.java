/**
 * ParameterString.java
 */
package com.sdc.cli.manager.parameter.template;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import com.sdc.cli.manager.parameter.Parameter;

/**
 * @author Simone.DeCristofaro
 * Oct 12, 2020
 */
public abstract class ParameterLocalDateTime extends Parameter<LocalDateTime>{

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    
    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], LocalDateTime> getParsingFunction() {
    
        return new Function<String[], LocalDateTime>() {

            @Override
            public LocalDateTime apply(String[] input) {

                return LocalDateTime.parse(input[0], DATE_TIME_FORMATTER);
                
            }
            
        };
        
    }
    
}
