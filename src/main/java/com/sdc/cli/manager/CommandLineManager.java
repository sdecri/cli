package com.sdc.cli.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdc.cli.manager.parameter.Parameterizable;
import com.sdc.cli.reader.CommandLineReader;


/**
 * This class is used to read and parse the command line arguments.
 * 
 * @author Simone De Cristofaro
 *         Nov 7, 2017
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class CommandLineManager {

    private static final Logger LOG = LoggerFactory.getLogger(CommandLineManager.class);
    public static final String PRINT_PARAMETERS_SEPARATOR = " -> ";
    public static final String NEW_LINE = System.getProperty("line.separator");

    private String commandName;
    private String helpMessage;
    private String[] arguments;
    private Options options;
    private CommandLineParser commandLineParser;
    private CommandLine cli;
    
    private Map<Class<? extends Parameterizable>, Parameterizable> parameters;
    

	/**
     * <code>true</code> if the last call of the method {@link CommandLineManager#read()}
     * read correctly the command line parameters returning <code>true</code>.
     */
    private boolean isArgRead;
    /**
     * <code>true</code> if the last call to {@link CommandLineManager#read()} assert that
     * the specified arguments contains the help parameter.
     */
    private boolean hasHelp;

    public CommandLineManager(String commandName, String helpMessage, String[] arguments, String...packages) throws CommandLineManagerException {
        this(commandName, helpMessage, arguments, new OptionsProvider(packages));

    }
    
    public CommandLineManager(String commandName, String helpMessage, String[] arguments, OptionsProvider optionsProvider) throws CommandLineManagerException {
        
        this(commandName, helpMessage, arguments, optionsProvider.getOptions());


    }
    
    public CommandLineManager(String commandName, String helpMessage, String[] arguments, List<? extends Parameterizable> parameters) throws CommandLineManagerException {
        
        this.commandName = commandName;
        this.helpMessage = helpMessage;
        this.commandLineParser = new DefaultParser();
        this.arguments = arguments;

        initInternal(parameters);
        read();

    }


    
    private void initInternal(List<? extends Parameterizable> parameters) {

        isArgRead = false;
        hasHelp = false;
        cli = null;
        
        createOptions(parameters);
        
    }


    /**
     * @param parameters
     */
    private void createOptions(List<? extends Parameterizable> parameters) {

        // create options
        this.parameters = new HashMap<>();
        options = new Options();
        for (Parameterizable parameterizable : parameters) {
            this.parameters.put(parameterizable.getClass(), parameterizable);
            options.addOption(parameterizable.createOption());
        }
    }
    
    public <R> Parameterizable getParameter(Class<? extends Parameterizable<?>> parameterType){
        return parameters.get(parameterType);
    }

    public boolean hasOption(Class<? extends Parameterizable<?>> parameterType) {
        Parameterizable<?> parameter = getParameter(parameterType);
        if(parameter != null)
            return cli.hasOption(parameter.getLongOpt());
        else
            LOG.warn(String.format("Parameter of class %s is unknown to the Command Line Manager", parameterType.getName()));
        
        return false;
    }
    
    public <R> R getParameterValue(Class<? extends Parameterizable<R>> parameterType) 
    throws CommandLineManagerException{
        
        Parameterizable<R> parameter = parameters.get(parameterType);
        if(parameter != null) {
            R toReturn = parameter.getDefaultValue();
            if (cli.hasOption(parameter.getLongOpt())) {
                String[] paramStringValues = cli.getOptionValues(parameter.getLongOpt());
                try {
                    toReturn = parameter.getParsingFunction().apply(paramStringValues);
                    // check if it's valid
                    if(!parameter.isValid(toReturn))
                        throw new CommandLineManagerException(String.format("%s is not a valid value for parameter %s. The parameter description is: %s"
                                , toReturn, parameter.getLongOpt(), parameter.getDescription()));
                } catch (Throwable e) {
                    throw new CommandLineManagerException(String.format("Error parsing argument \"%s\" for parameter \"%s\"", Arrays.toString(paramStringValues), parameter.getLongOpt()), e);
                }
            }
            return toReturn;

        }
        else {
            
            String[] availableParameters = parameters.entrySet().stream().map(entry -> entry.getValue().getLongOpt())
                    .toArray(size -> new String[size]);
            
            try {
                String optionName = parameterType.newInstance().getLongOpt();
                throw new CommandLineManagerException(String.format("Parameter %s not found. Available parameters: %s", optionName
                        , Arrays.toString(availableParameters)));
            }
            catch (InstantiationException | IllegalAccessException e) {
                throw new CommandLineManagerException(String.format("Parameter %s not found", parameterType.getSimpleName(), e));
            }
        }
            
        
    }
    
    /**
     * Read the command line parameters filling the internal {@link CommandLine} instance.
     * If the specified arguments contains the help parameter, it prints the help and don't fill the 
     * {@link CommandLine} instance. 
     * @return <code>true</code> if the internal {@link CommandLine} instance has been correctly filled.
     * @throws CommandLineManagerException
     */
    private boolean read() throws CommandLineManagerException {
        LOG.info("Reading command line parameters...");

        hasHelp = checkForHelp();
        
        if(!hasHelp) {
            try {
                cli = commandLineParser.parse(options, arguments);
            }
            catch (Exception e) {
                throw new CommandLineManagerException("Error reading command line arguments", e);
            }

            isArgRead = true;
        }
        return isArgRead;
    }

    /**
     * check if the command line has help parameter. If so print it in the System.out.
     * 
     * @return <code>true</code> if the command line has help parameter
     * @see HelpFormatter#printHelp(String, Options)
     */
    private boolean checkForHelp() {
        CommandLine commandLine = null;
        Options options = new Options();
        Option optionHelp = Option.builder("h").longOpt("help").hasArg(false).desc("shows this message").build();
        options.addOption(optionHelp);
        try {
            commandLine = commandLineParser.parse(options, arguments, true);
        }
        catch (ParseException e) {
            LOG.warn(String.format("Error checking for help option"), e);
        }
        boolean hasHelp = commandLine.hasOption(optionHelp.getOpt()); 
        if (hasHelp)
            printHelp();
        
        return hasHelp;

    }



//    public String getCurrentConfigParameterMessage(String separator) {
//        
//        StringBuilder sb = new StringBuilder("Service was called with the following CLI params:").append(separator);
//        Set<String> processedOptions = new HashSet<>();
//        for (Option option : cli.getOptions()) {
//            if (!processedOptions.contains(option.getLongOpt())) {
//                processedOptions.add(option.getLongOpt());
//                if (option.hasArg()) {
//
//                    String[] values = cli.getOptionValues(option.getLongOpt());
//                    for (String value : values) {
//                        sb.append(String.format("'%s':'%s'", option.getLongOpt(), value)).append(separator);
//                    }
//
//                }
//                else
//                    sb.append(String.format("'%s':'%s'", option.getLongOpt(), "")).append(separator);
//                
//            }
//
//		}
//        return sb.toString();
//    }
    
    public String getCurrentConfigParameterMessage() {
        
        String separator = PRINT_PARAMETERS_SEPARATOR;
        
        StringBuilder sb = new StringBuilder("Service was called with the following CLI params:").append(NEW_LINE).append(NEW_LINE);
        for (Option option : cli.getOptions()) {
            String valueString = option.hasArg() ? String.join(",", option.getValuesList()) : "";
            sb.append(String.format("'%s'%s'%s'", option.getLongOpt(), separator, valueString)).append(NEW_LINE);
        }
        return sb.toString();
    }

    /**
     * 
     * @param optionLongOpt
     * @return String
     * @see CommandLine#getOptionValue(String)
     */
    public String getOptionValue(String optionLongOpt) {

        return cli.getOptionValue(optionLongOpt);
    }


    private void printHelp() {

        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(createUsageMessage().toString(), options);
    }


    private StringBuilder createUsageMessage() {

        StringBuilder usage = new StringBuilder(helpMessage);
        usage.append(CommandLineReader.NEW_LINE);
        usage.append(commandName).append(" [OPTIONS]");
        return usage;
    }

    
    /**
     * @return the {@link CommandLineManager#isArgRead}
     */
    public boolean isArgRead() {
    
        return isArgRead;
    }

    public boolean hasParameter(Class<? extends Parameterizable<?>> parameterClass) {
        try {
            return cli.hasOption(parameterClass.newInstance().getLongOpt());
        }
        catch (InstantiationException | IllegalAccessException e) {
            LOG.error(String.format("Error looking for a parameter of class %s", parameterClass.getName(), e));
        }
        return false;
    }
    
    /**
     * @return the {@link CommandLineManager#hasHelp}
     */
    public boolean hasHelp() {
    
        return hasHelp;
    }

    public CommandLine getCli() {
        return cli;
    }

    
}
