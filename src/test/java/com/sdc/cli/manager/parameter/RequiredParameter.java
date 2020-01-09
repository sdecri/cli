/**
 * FakeParameter.java
 */
package com.sdc.cli.manager.parameter;

import java.util.function.Function;

/**
 * @author Simone De Cristofaro
 * Oct 17, 2019
 */
public class RequiredParameter extends Parameter<String>{

    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isRequired() {
    
        return true;
        
    }
    
    /**
    * {@inheritDoc}
    */
    @Override
    public Class<?> getOptionType() {

        return String.class;
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getDescription() {

        return "required parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getLongOpt() {

        return "required-parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], String> getParsingFunction() {

        return s -> s[0];
        
    }

}
