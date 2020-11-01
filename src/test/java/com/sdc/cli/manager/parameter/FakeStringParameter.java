/**
 * FakeParameter.java
 */
package com.sdc.cli.manager.parameter;

import com.sdc.cli.manager.parameter.template.ParameterString;

/**
 * @author Simone De Cristofaro
 * Oct 17, 2019
 */
public class FakeStringParameter extends ParameterString{

    /**
    * {@inheritDoc}
    */
    @Override
    public String getDefaultValue() {
    
        return "default";
        
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

        return "fake parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getLongOpt() {

        return "fake-parameter";
        
    }


}
