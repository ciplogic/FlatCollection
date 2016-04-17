package com.khlud.ciprian.flatcollection.compiler.codeModel;

import java.util.List;

/**
 * Created by Ciprian on 2/29/2016.
 */
public abstract class NodeModel {

    public String name;
    public NodeModel _parent;
    public List<TypeDescription> GenericArgs;

    @Override
    public String toString() {
        return name;
    }

    public Object resolveExpression(String itemExpression) throws Exception {
        throw new Exception("Invalid expression");
    }


}
