package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.model.PairT;

import java.util.ArrayList;
import java.util.List;

public class ClassModel extends NodeModel{
    public List<List<TokenDefinition>> Constants;
    public List<PairT<String, List<String>>> Definitions;
    public List<List<TokenDefinition>> Variables;
    public List<List<TokenDefinition>> Validations;

    public List<MethodModel> Methods = new ArrayList<>();

    public List<String> GenericArguments = new ArrayList<>();

    public ClassModel(String className, List<String> genericArguments){
        name = className;
    }

    public MethodModel addMethod(String methodName, List<PairT<String, String>> arguments, String returnType){
        MethodModel methodModel = new MethodModel(this);
        Methods.add(methodModel);
        return methodModel;

    }
}

