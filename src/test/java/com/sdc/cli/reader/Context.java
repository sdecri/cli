/**
 * Context.java
 */
package com.sdc.cli.reader;

import java.util.List;

public class Context {

    private String stringPar;
    private int intPar;
    private List<Integer> listPar;
    private boolean boolPar;
    
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
        
        public Context.Builder withBoolPar(boolean boolPar) {
            context.boolPar = boolPar;
            return this;
        }
        
        public Context build() {
            return context;
        }
    }

    
    /**
     * @return the {@link Context#boolPar}
     */
    public boolean isBoolPar() {
    
        return boolPar;
    }

}