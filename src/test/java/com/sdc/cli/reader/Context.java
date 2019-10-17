/**
 * Context.java
 */
package com.sdc.cli.reader;

import com.sdc.cli.OptionsProviderImplOld;

public class Context {

    private String internalOutputPath;
    private String visumBasePath;
    private String outputDir;
    
    /**
    * {@inheritDoc}
    */
    @Override
    public String toString() {
    
        return String.format("%s: %s, %s: %s"
                , OptionsProviderImplOld.INTERNAL_OUTPUT, internalOutputPath
                , OptionsProviderImplOld.VISUM_PATH, visumBasePath
                );
                
        
    }
    
    /**
     * @return the {@link Context#internalOutputPath}
     */
    public String getInternalOutputPath() {
    
        return internalOutputPath;
    }
    

    /**
     * @return the {@link Context#visumBasePath}
     */
    public String getVisumBasePath() {
    
        return visumBasePath;
    }
    
    
    /**
     * @return the {@link Context#outputDir}
     */
    public String getOutputDir() {
    
        return outputDir;
    }
    
    public static Context.Builder newBuilder() {
        return new Builder();
    }
    
    static class Builder{
        
        private Context context;
        
        private Builder() {
            context = new Context();
        }
        
        public Context.Builder withInternalOutputPath(String internalOutputPath) {
            context.internalOutputPath = internalOutputPath;
            return this;
        }
        
        public Context.Builder withVisumBasePath(String visumBasePath) {
            context.visumBasePath = visumBasePath;
            return this;
        }
        
        public Context.Builder withOutputDir(String outputDir) {
            context.outputDir = outputDir;
            return this;
        }
        
        public Context build() {
            return context;
        }
    }


    
    

}