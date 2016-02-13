package com.khlud.ciprian.flatcollection.model;

/**
 * Created by ciprian on 2/11/16.
 */
public class CompilerLayoutDescription {
    public String typeName;
    public String fieldType;
    public String[] fields = new String[0];
    public String simpleTypeName(){
        int indexOf = typeName.lastIndexOf(".");
        String remaining = typeName.substring(indexOf+1);

        return remaining;
    }
}


