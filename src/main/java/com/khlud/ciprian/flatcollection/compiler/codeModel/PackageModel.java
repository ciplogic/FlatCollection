package com.khlud.ciprian.flatcollection.compiler.codeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PackageModel extends NodeModel{
    public final TypeDescription _typeDescription;

    public PackageModel(TypeDescription nameDescription) {
        _typeDescription = nameDescription;
        name = nameDescription.getSimpleName();
    }

    public ProgramModel getProgramModel(){
        return (ProgramModel) _parent;
    }

    public List<PackageModel> PackageList = new ArrayList<>();

    public List<FlatModel> FlatList = new ArrayList<>();
    public List<ClassModel> ClassList = new ArrayList<>();
    public List<InterfaceModel> InterfaceList = new ArrayList<>();
    public List<SpecializeModel> Specializations = new ArrayList<>();

    public ClassModel addClassModel(String className, List<TypeDescription> genericArgs){
        ClassModel item = new ClassModel(className, genericArgs);
        item._parent = this;
        ClassList.add(item);
        return item;
    }

    public NodeModel locate(String nameToFind) {
        for(PackageModel packageModel : PackageList){
            NodeModel nodeModel = packageModel.locate(name);
            if(nodeModel!=null)
                return nodeModel;
        }

        NodeModel toFind = locateInList(this.ClassList, nameToFind);
        if (toFind != null) {
            return toFind;
        }
        toFind = locateInList(this.FlatList, nameToFind);
        if (toFind != null) {
            return toFind;
        }

        toFind = locateInList(this.InterfaceList, nameToFind);
        if (toFind != null) {
            return toFind;
        }

        toFind = locateInList(this.Specializations, nameToFind);
        if (toFind != null) {
            return toFind;
        }

        return null;
    }

    static <T extends NodeModel> NodeModel locateInList(List<T> modelList, String nameToFind) {
        Optional<T> itemSearch = modelList.stream()
                .filter(nodeModel -> nameToFind.equals(nodeModel.name))
                .findFirst();
        if (itemSearch.isPresent()) {
            return itemSearch.get();
        }
        return null;
    }
    public void addSpecializeModel(SpecializeModel specializeModel) {
        Specializations.add(specializeModel);
        specializeModel._parent = this;
    }

    public void addFlatType(FlatModel flatDescription) {
        FlatList.add(flatDescription);
        flatDescription._parent = this;
    }

    void addSpecializations(List<SpecializeModel> specializations) {
        specializations.addAll(Specializations);
        PackageList.stream().forEach(
            pkg->pkg.addSpecializations(specializations)
        );
    }


}
