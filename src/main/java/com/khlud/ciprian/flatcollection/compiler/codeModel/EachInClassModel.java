package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers.MethodSignature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 3/10/2016.
 */
public class EachInClassModel extends NodeModel {
    public String CollectionName;
    public String ItemName;
    public String IndexName;
    public List<MethodModel> Methods = new ArrayList<>();

    EachInClassModel(ClassModel aThis) {
        _parent = aThis;
    }

    public MethodModel addMethod(MethodSignature signature) {
        MethodModel methodModel = new MethodModel(this);
        Methods.add(methodModel);
        methodModel.setSignature(signature);
        return methodModel;
    }
}
