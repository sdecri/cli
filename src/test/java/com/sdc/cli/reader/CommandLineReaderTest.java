/**
 * CommandLineReaderTest.java
 */
package com.sdc.cli.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class CommandLineReaderTest {

    private static final String INTERNAL_OUTPUT = "internal-output";
    private static final String VISUM_PATH = "visum-path";
    private static final String OUTPUT_DIR = "output-dir";
    private static final String PARAM_PREFIX = "--";
    
    @Test
    public void testRead() throws ParseException {
        
        CommandLineReader<Context> cli = new CommandLineReaderImpl(getOptions(), "test", "help message");
        Optional<Context> contextOp = cli.read(new String[] {
                PARAM_PREFIX + INTERNAL_OUTPUT, "io"
                , PARAM_PREFIX + VISUM_PATH, "vp"
                , PARAM_PREFIX + OUTPUT_DIR, "od" 
        });
        
        assertThat(contextOp.isPresent(), is(true));
        assertThat(contextOp.get().getInternalOutputPath(), is(equalTo("io")));
        assertThat(contextOp.get().getVisumBasePath(), is(equalTo("vp")));
        assertThat(contextOp.get().getOutputDir(), is(equalTo("od")));
    }
    
    @Test
    public void testHelp() throws ParseException {
        
        CommandLineReader<Context> cli = new CommandLineReaderImpl(getOptions(), "test", "help message");
        Optional<Context> contextOp = cli.read(new String[] {"-h", PARAM_PREFIX + INTERNAL_OUTPUT, "io"});
        
        assertThat(contextOp.isPresent(), is(false));
    }
    
    
    class CommandLineReaderImpl extends CommandLineReader<Context>{



        /**
         * @param options
         * @param commandName
         * @param helpMessage
         */
        public CommandLineReaderImpl(Options options, String commandName, String helpMessage) {
            super(options, commandName, helpMessage);
        }

        /**
        * {@inheritDoc}
        */
        @Override
        public Context createContext(CommandLine cli) {

          return Context.newBuilder()
          .withInternalOutputPath(cli.getOptionValue(INTERNAL_OUTPUT))
          .withVisumBasePath(cli.getOptionValue(VISUM_PATH))
          .withOutputDir(cli.getOptionValue(OUTPUT_DIR, ""))
          .build();
            
        }
        
    }
    
    public Options getOptions(){
        
        List<Option> optionList = new ArrayList<>();
        
        optionList.add(
                Option.builder()
                .longOpt(INTERNAL_OUTPUT)
                .hasArg(true)
                .desc("Path of the internal output")
                .type(String.class)
                .required(false)
                .build()
                );
        optionList.add(
                Option.builder()
                .longOpt(VISUM_PATH)
                .hasArg(true)
                .desc(String.format("Path of visum output"))
                .type(String.class)
                .required(false)
                .build()
                );
        optionList.add(
                Option.builder()
                .longOpt(OUTPUT_DIR)
                .hasArg(true)
                .desc(String.format("Output directory. Default current dir"))
                .type(String.class)
                .required(false)
                .build()
                );
//        optionList.add(
//                Option.builder()
//                .longOpt(TILE_DIAG_NAME)
//                .hasArg(true)
//                .desc(String.format("Diagonal of each tile. DEfault: %d", TILE_DIAG_DEFAULT))
//                .type(String.class)
//                .required(false)
//                .build()
//                );

        
        
        
        Options options = new Options();
        for (Option option : optionList)
            options.addOption(option);
        
        return options;
        
    }
    
}
