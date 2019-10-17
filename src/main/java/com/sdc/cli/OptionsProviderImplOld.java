/**
 * OptionsProviderImpl.java
 */
package com.sdc.cli;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionsProviderImplOld {
        
        public static final String PARAMETER_PREFIX = "--";

        public static final String INTERNAL_OUTPUT = "internal-output-path";
        public static final String VISUM_PATH = "visum-path";
        public static final String OUTPUT_DIR = "output-dir";

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
//            optionList.add(
//                    Option.builder()
//                    .longOpt(TILE_DIAG_NAME)
//                    .hasArg(true)
//                    .desc(String.format("Diagonal of each tile. DEfault: %d", TILE_DIAG_DEFAULT))
//                    .type(String.class)
//                    .required(false)
//                    .build()
//                    );

            
            
            
            Options options = new Options();
            for (Option option : optionList)
                options.addOption(option);
            
            return options;
            
        }
        
    }