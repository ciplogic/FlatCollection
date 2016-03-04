package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.sun.xml.internal.ws.policy.sourcemodel.ModelNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramModel extends NodeModel {
    public List<FlatModel> FlatList = new ArrayList<>();
    public List<ClassModel> ClassList = new ArrayList<>();
    public List<InterfaceModel> InterfaceList = new ArrayList<>();
    public List<SpecializeModel> Specializations = new ArrayList<>();

    public ProgramModel(){
        this.name = "Main Program";
    }

    public void addFlatType(FlatModel flatDescription) {
        FlatList.add(flatDescription);
        flatDescription._parent = this;
    }

    public ClassModel addClass(String className, List<String> genericArguments) {
        ClassModel result = new ClassModel(className, genericArguments);
        ClassList.add(result);
        result._parent= this;

        return result;
    }

    public void addSpecializeModel(SpecializeModel specializeModel) {
        Specializations.add(specializeModel);
        specializeModel._parent = this;
    }

    public NodeModel locate(String nameToFind){
        NodeModel toFind = locateInList(this.ClassList, nameToFind);
        if(toFind!=null)
            return toFind;
        toFind = locateInList(this.FlatList, nameToFind);
        if(toFind!=null)
            return toFind;

        toFind = locateInList(this.InterfaceList, nameToFind);
        if(toFind!=null)
            return toFind;

        toFind = locateInList(this.Specializations, nameToFind);
        if(toFind!=null)
            return toFind;

        return null;
    }

    static <T extends NodeModel> NodeModel locateInList(List<T> modelList, String nameToFind){
        Optional<T> itemSearch = modelList.stream()
                .filter(nodeModel -> nameToFind.equals( nodeModel.name))
                .findFirst();
        if(itemSearch.isPresent())
            return itemSearch.get();
        return null;
    }

}
