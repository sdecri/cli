/**
 * FakeParameter.java
 */
package com.sdc.cli.manager.parameter;

import java.util.function.Function;

/**
 * @author Simone De Cristofaro
 * Oct 17, 2019
 */
public class FakeIntegerParameter extends Parameter<Integer>{

    /**
    * {@inheritDoc}
    */
    @Override
    public String getFormattedDefault() {
    
        return Integer.toString(getDefaultValue());
        
    }
    
    /**
    * {@inheritDoc}
    */
    @Override
    public Integer getDefaultValue() {
    
        return 1;
        
    }
    
    /**
    * {@inheritDoc}
    */
    @Override
    public Class<?> getOptionType() {

        return Integer.class;
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getDescription() {

        return "fake integer parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getLongOpt() {

        return "fake-integer-parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], Integer> getParsingFunction() {

        return s -> Integer.parseInt(s[0]);
        
    }

}
