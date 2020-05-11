/**
 * java
 */
package com.sdc.cli.manager.parameter;

import org.apache.commons.cli.Option;

/**
 * @author Simone De Cristofaro
 * Feb 5, 2018
 * @param <T>
 */
public abstract class Parameter<T> implements Parameterizable<T> {

    public Parameter() {}


    public Option createOption() {
        
            return Option.builder()
                    .longOpt(getLongOpt())
                    .hasArg(hasArg())
                    .desc(getDescsriptionWithDefaultValue())
                    .type(getOptionType())
                    .required(isRequired())
                    .build();
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isRequired() {
    
        return false;
        
    }
    
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isValid(T value) {
    
        return true;
        
    }
    
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean hasArg() {
    
        return true;
        
    }
    
    public String getDescsriptionWithDefaultValue() {
        
        String description = getDescription();
        StringBuilder sb = new StringBuilder(description);
        if(getFormattedDefault() != null) {
            if(!description.endsWith("."))
                sb.append(".");
            sb.append(" Default: ").append(getFormattedDefault());
        }
        else if(getDefaultValue() != null) {
            if(!description.endsWith("."))
                sb.append(".");
            sb.append(" Default: ").append(getDefaultValue());
        }
        return sb.toString();
    }
    
    public String getFormattedDefault() {
        return null;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public T getDefaultValue() {
    
        return null;
        
    }
    
    
    
}
