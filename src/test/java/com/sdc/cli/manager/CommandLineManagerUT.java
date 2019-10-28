/**
 * CommandLineReaderManagerTest.java
 */
package com.sdc.cli.manager;

import org.junit.Test;

import com.sdc.cli.manager.parameter.FakeArrayParameter;
import com.sdc.cli.manager.parameter.FakeIntegerParameter;
import com.sdc.cli.manager.parameter.FakeStringParameter;

import static org.junit.Assert.*;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;

/**
 * @author Simone De Cristofaro
 * Oct 17, 2019
 */
public class CommandLineManagerUT {

    @Test
    public void testRead() throws CommandLineManagerException {
        
        CommandLineManager clm = new CommandLineManager("test", "help message", new String[] {
                "--" + new FakeIntegerParameter().getLongOpt(), "10"
                , "--" + new FakeStringParameter().getLongOpt(), "string-param"
                , "--" + new FakeArrayParameter().getLongOpt(), "1"
                , "--" + new FakeArrayParameter().getLongOpt(), "2"
        }
        , "com.sdc.cli.manager.parameter");
        
        System.out.println(clm.getCurrentConfigParameterMessage());
        
        assertThat(clm.getParameterValue(FakeIntegerParameter.class), is(equalTo(10)));
        assertThat(clm.getParameterValue(FakeStringParameter.class), is(equalTo("string-param")));
        assertThat(clm.getParameterValue(FakeArrayParameter.class), is(equalTo(Arrays.asList(1, 2))));
        
        assertThat(clm.getOptionValue(new FakeIntegerParameter().getLongOpt()), is(equalTo("10")));
        
        assertThat(clm.hasParameter(FakeIntegerParameter.class), is(true));
        assertThat(clm.getParameter(FakeIntegerParameter.class), is(not(nullValue())));
        assertThat(clm.getParameter(FakeIntegerParameter.class).getClass(), is(equalTo(FakeIntegerParameter.class)));
    }
    
    @Test
    public void testHelp() throws CommandLineManagerException {
        
        CommandLineManager clm = new CommandLineManager("test", "help message", new String[] {"-h"}
        , "com.sdc.cli.manager.parameter");
        
        assertThat(clm.hasHelp(), is(true));
        
    }
}
