package com.khlud.ciprian.flatcollection.compiler.codeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramModel extends NodeModel {


    public ProgramModel() {
        this.name = "Main Program";
    }

    List<PackageModel> PackageList = new ArrayList<>();

    public NodeModel locate(String name) {
        for(PackageModel packageModel : PackageList){
            NodeModel nodeModel = packageModel.locate(name);
            if(nodeModel!=null)
                return nodeModel;
        }
        return null;
    }

    public Iterable<SpecializeModel> getSpecializations() {
        List<SpecializeModel> specializations = new ArrayList<>();
        for(PackageModel packageModel:PackageList){
            packageModel.addSpecializations(specializations);
        }
        return specializations;
    }

    public PackageModel addPackage(List<String> arguments) {
        TypeDescription typeDescription = new TypeDescription(arguments.stream());
        PackageModel item = new PackageModel(typeDescription);
        PackageList.add(item);
        return item;
    }
}
