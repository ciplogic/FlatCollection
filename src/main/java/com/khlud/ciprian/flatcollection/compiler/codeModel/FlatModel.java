package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.google.common.base.Joiner;

import java.util.List;

/**
 * Created by Ciprian on 2/29/2016.
 */
public class FlatModel extends NodeModel {

    public List<String> fieldNames;
    public String fieldTypeName;

    public FlatModel(String simpleName, List<String> fieldNames, String fieldTypeName) {

        this.name = simpleName;
        this.fieldNames = fieldNames;
        this.fieldTypeName = fieldTypeName;
    }

    @Override
    public String resolveExpression(String itemExpression) throws Exception {
        switch (itemExpression) {
            case "valueType":
                return fieldTypeName;

            case "countFields":
                return new Integer(fieldNames.size()).toString();
            case "fieldNames":
                return Joiner.on(",").join(fieldNames);
        }
        return super.resolveExpression(itemExpression);
    }
}
