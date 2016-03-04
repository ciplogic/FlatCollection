package com.khlud.ciprian.flatcollection.compiler.codeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 2/29/2016.
 */
public abstract class NodeModel {
    public String name;
    public NodeModel _parent;

    @Override
    public String toString() {
        return  name;
    }
}

