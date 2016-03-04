package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers.MethodSignature;

import java.util.ArrayList;
import java.util.List;

public class MethodModel extends NodeModel {
    public List<ArgumentType> Arguments = new ArrayList<>();
    public NodeModel ReturnType;
    private MethodSignature signature;

    public MethodModel(ClassModel classModel){
        _parent = classModel;
    }

    public void setSignature(MethodSignature signature) {
        this.signature = signature;
        name = signature.name;
    }
}
