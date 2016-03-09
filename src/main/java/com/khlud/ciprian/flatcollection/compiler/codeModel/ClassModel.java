package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers.MethodSignature;
import com.khlud.ciprian.flatcollection.model.PairT;

import java.util.ArrayList;
import java.util.List;

public class ClassModel extends NodeModel {

    public final List<String> genericArguments;
    public List<List<String>> Constants;
    public List<PairT<String, List<String>>> Definitions;
    public List<List<String>> Variables;
    public List<List<TokenDefinition>> Validations;
    public List<TypeDescription> Imports = new ArrayList<>();

    public List<MethodModel> Methods = new ArrayList<>();

    public ClassModel(String className, List<String> genericArguments) {
        this.genericArguments = genericArguments;
        name = className;
    }

    public MethodModel addMethod(MethodSignature signature) {
        MethodModel methodModel = new MethodModel(this);
        Methods.add(methodModel);
        methodModel.setSignature(signature);
        return methodModel;
    }
}
