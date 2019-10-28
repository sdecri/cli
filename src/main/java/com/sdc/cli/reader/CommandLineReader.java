package com.sdc.cli.reader;

import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author Simone De Cristofaro
 * Sep 20, 2018
 * @param <C> 
 */
public abstract class CommandLineReader<C> {

    private static final Logger LOG = LoggerFactory.getLogger(CommandLineReader.class);
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String PRINT_PARAMETERS_SEPARATOR = " -> ";
    
    private static final Option HELP_OPTION = Option.builder("h").longOpt("help").hasArg(false).desc("shows this message").build();
    
    private Options options;
    private String commandName;
    private String helpMessage;
    
    public CommandLineReader(Options options, String commandName, String helpMessage) {
        super();
        this.commandName = commandName;
        this.helpMessage = helpMessage;
        this.options = new Options();
        for (Option opt : options.getOptions()) {
            this.options.addOption(opt);
        }
    }


    protected abstract C createContext(CommandLine cli); 
    
    
    public Optional<C> read(String [] args) throws ParseException  {
        
        LOG.info("Reading command line parameters...");

        boolean hasHelp = checkForHelp(args);

        if(!hasHelp) {

            CommandLineParser commandLineParser = new DefaultParser();
            
            CommandLine cli;
            try {
                cli = commandLineParser.parse(options, args);
                LOG.info(getCurrentConfigParameterMessage(cli));
            }
            catch (ParseException e) {
                printHelp();
                throw e;
            }
            

            return Optional.of(createContext(cli));
            
        }
        else
            return Optional.empty();
        
        
        
    }
    
    private StringBuilder createUsageMessage() {

        StringBuilder usage = new StringBuilder(helpMessage);
        usage.append(NEW_LINE);
        usage.append(commandName).append(" [OPTIONS]");
        return usage;
    }
    
    private void printHelp() {

        HelpFormatter helpFormatter = new HelpFormatter();
        Options allOptions = new Options();
        for (Option opt : this.options.getOptions()) {
            allOptions.addOption(opt);
        }
        helpFormatter.printHelp(createUsageMessage().toString(), allOptions);
    }
    
    /**
     * check if the command line has help parameter. If so print it in the System.out.
     * @param arguments 
     * 
     * @return <code>true</code> if the command line has help parameter
     * @see HelpFormatter#printHelp(String, Options)
     */
    private boolean checkForHelp(String[] arguments) {
        CommandLine commandLine = null;
        Options options = new Options();
        options.addOption(HELP_OPTION);
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            commandLine = commandLineParser.parse(options, arguments, true);
        }
        catch (ParseException e) {
            LOG.warn(String.format("Error checking for help option"), e);
        }
        boolean hasHelp = commandLine.hasOption(HELP_OPTION.getOpt()); 
        if (hasHelp)
            printHelp();
        
        return hasHelp;

    }

    private static String getCurrentConfigParameterMessage(CommandLine cli) {
        
        String separator = PRINT_PARAMETERS_SEPARATOR;
        
        StringBuilder sb = new StringBuilder("Service was called with the following CLI params:").append(NEW_LINE);
        for (Option option : cli.getOptions()) {
            String valueString = option.hasArg() ? String.join(",", option.getValuesList()) : "";
            sb.append(String.format("'%s'%s'%s'", option.getLongOpt(), separator, valueString)).append(NEW_LINE);
        }
        return sb.toString();
    }
}