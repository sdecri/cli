# Cli
This utility allows to read command line parameters in a smart way. There are two possible way to read and manage command line parameters: simple **reader** and **manager** way. It is based on the apache [commons-cli](https://commons.apache.org/proper/commons-cli/) library.

[![](https://jitpack.io/v/sdecri/cli.svg)](https://jitpack.io/#sdecri/cli)

## Reader
Create a "Context" class
```java
import java.util.List;

public class Context {

    private String stringPar;
    private int intPar;
    private List<Integer> listPar;
    
    public String getStringPar() {
        
        return stringPar;
    }

    public int getIntPar() {
        
        return intPar;
    }

    public List<Integer> getListPar() {
    
        return listPar;
    }
    
    public static Context.Builder newBuilder() {
        return new Builder();
    }
    
    static class Builder{
        
        private Context context;
        
        private Builder() {
            context = new Context();
        }
        
        public Context.Builder withStringPar(String stringPar) {
            context.stringPar = stringPar;
            return this;
        }
        
        public Context.Builder withIntPar(int intPar) {
            context.intPar = intPar;
            return this;
        }

        public Context.Builder withListPar(List<Integer> listPar) {
            context.listPar = listPar;
            return this;
        }
        
        public Context build() {
            return context;
        }
    }

}
```
Extends the **CommandLineReader** class
```java
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
```

Use this class in the main method, creating the needed options
```java

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public static void main(String[] args)[
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
    
    
    CommandLineReader<Context> cli = new CommandLineReaderImpl(options, "test", "help message");
    Optional<Context> contextOp = cli.read(args);

}

```

## Manager
Create the parameters extending the "Parameter" class
```java
public class FakeStringParameter extends Parameter<String>{

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

    /**
    * {@inheritDoc}
    */
    @Override
    public Function<String[], String> getParsingFunction() {

        return s -> s[0];
        
    }
```
```java
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
```
You can also extend one of the available parameter template: ParameterString, ParameterInteger, ParameterDouble, ParameterFloat, ParameterBoolean, ParameterDuration, ParameterLocalDateTime and ParameterStrinList.
```java
public class FakeIntegerParameter extends ParameterInteger{

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

        return "fake integer parameter";
        
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getLongOpt() {

        return "fake-integer-parameter";
        
    }

}
```
Use the "CommandLineManager" in the main method, specifying the packages where to search for the needed parameters
```java
public static void main(String[] args){
    CommandLineManager clm = new CommandLineManager("test", "help message", args
    , "com.sdc.cli.manager.parameter");

	System.out.println("Integer parameter value: " + clm.getParameterValue(FakeIntegerParameter.class));
    System.out.println("String parameter value: " + clm.getParameterValue(FakeStringParameter.class));
    System.out.println("List parameter value: " + clm.getParameterValue(FakeArrayParameter.class));
}
```
