package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers.MethodSignature;

import java.util.List;

public class MethodModel extends NodeModel {

    public MethodSignature signature;
    public List<TokenDefinition> body;

    public MethodModel(ClassModel classModel) {
        _parent = classModel;
    }
    public MethodModel(EachInClassModel classModel) {
        _parent = classModel;
    }

    public void setSignature(MethodSignature signature) {
        this.signature = signature;
        name = signature.methodName.getFullName();
    }
}
