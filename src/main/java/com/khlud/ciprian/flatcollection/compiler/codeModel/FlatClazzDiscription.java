package com.khlud.ciprian.flatcollection.compiler.codeModel;

/**
 * Created by Ciprian on 2/27/2016.
 */
public class FlatClazzDiscription {
    public String simpleName;
    public String[] fieldNames;
    public String fieldTypeName;

    public FlatClazzDiscription(String simpleName, String[] fieldNames, String fieldTypeName) {

        this.simpleName = simpleName;
        this.fieldNames = fieldNames;
        this.fieldTypeName = fieldTypeName;
    }
}
