/**
 * FakeParameter.java
 */
package com.sdc.cli.manager.parameter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Simone De Cristofaro
 * Oct 17, 2019
 */
public class FakeArrayParameter extends Parameter<List<Integer>>{

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

        return "fake array parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getLongOpt() {

        return "fake-array-parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], List<Integer>> getParsingFunction() {

        return array-> Arrays.asList(array).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        
    }

}
