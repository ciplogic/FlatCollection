package com.khlud.ciprian.flatcollection.compiler.codeModel;

import java.util.ArrayList;
import java.util.List;

public class MethodModel extends NodeModel {
    public List<ArgumentType> Arguments = new ArrayList<>();
    public NodeModel ReturnType;
    public MethodModel(ClassModel classModel){
        _parent = classModel;
    }
}
