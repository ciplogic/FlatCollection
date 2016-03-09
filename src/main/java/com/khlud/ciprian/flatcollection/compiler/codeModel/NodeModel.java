package com.khlud.ciprian.flatcollection.compiler.codeModel;

/**
 * Created by Ciprian on 2/29/2016.
 */
public abstract class NodeModel {

    public String name;
    public NodeModel _parent;

    @Override
    public String toString() {
        return name;
    }

    public String resolveExpression(String itemExpression) throws Exception {
        throw new Exception("Invalid expression");
    }
}
