package com.khlud.ciprian.flatcollection.compiler.codeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 3/4/2016.
 */
public class SpecializeModel extends NodeModel {
    public List<String> specializations = new ArrayList<>();

    public ClassModel definedClass;

    public SpecializeModel(String simpleName, String specializations) {

        this.name = simpleName;
        this.specializations.add(specializations);
    }

    public void processDefinitions(){
        ProgramModel programModel = (ProgramModel)_parent;
        definedClass = (ClassModel)programModel.locate(name);
    }
}
