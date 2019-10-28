/**
 * CommandLineReaderUT.java
 */
package com.sdc.cli.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author simone
 * Oct 15, 2019
 */
public class CommandLineReaderUT {

    @Test
    public void testRead() throws ParseException {
        
        CommandLineReader<Context> cli = new CommandLineReaderImpl(getOptions(), "test", "help message");
        Optional<Context> contextOp = cli.read(new String[] {
                "--string-par", "foo"
                , "--int-par", "1"
                , "--list-par", "1"
                , "--list-par", "2"
        });
        
        assertThat(contextOp.isPresent(), is(true));
        assertThat(contextOp.get().getStringPar(), is(equalTo("foo")));
        assertThat(contextOp.get().getIntPar(), is(equalTo(1)));        
        assertThat(contextOp.get().getListPar(), hasSize(2));
        assertThat(contextOp.get().getListPar(), contains(1,2));
    }
    
    @Test
    public void testHelp() throws ParseException {
        
        CommandLineReader<Context> cli = new CommandLineReaderImpl(getOptions(), "test", "help message");
        Optional<Context> contextOp = cli.read(new String[] {"-h", "--string-par", "foo"});
        
        assertThat(contextOp.isPresent(), is(false));
    }
    
    
    class CommandLineReaderImpl extends CommandLineReader<Context>{

        public CommandLineReaderImpl(Options options, String commandName, String helpMessage) {
            super(options, commandName, helpMessage);
        }

        /**
        * {@inheritDoc}
        */
        @Override
        public Context createContext(CommandLine cli) {

          return Context.newBuilder()
          .withStringPar(cli.getOptionValue("string-par"))
          .withIntPar(Integer.parseInt(cli.getOptionValue("int-par", "0")))
          .withListPar(Arrays.asList(cli.getOptionValues(("list-par"))).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()))
          .build();
            
        }
        
    }
    
    public Options getOptions(){
        
        List<Option> optionList = new ArrayList<>();
        
        optionList.add(
                Option.builder()
                .longOpt("string-par")
                .hasArg(true)
                .desc("String parameter")
                .type(String.class)
                .required(true)
                .build()
                );
        optionList.add(
                Option.builder()
                .longOpt("int-par")
                .hasArg(true)
                .desc(String.format("Integer parameter"))
                .type(String.class)
                .required(false)
                .build()
                );
        optionList.add(
                Option.builder()
                .longOpt("list-par")
                .hasArg(true)
                .desc(String.format("List parameter"))
                .type(String.class)
                .required(true)
                .build()
                );
        
        
        
        Options options = new Options();
        for (Option option : optionList)
            options.addOption(option);
        
        return options;
        
    }
    
}
