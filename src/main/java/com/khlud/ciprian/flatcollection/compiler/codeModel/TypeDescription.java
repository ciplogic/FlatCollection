package com.khlud.ciprian.flatcollection.compiler.codeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Ciprian on 3/4/2016.
 */
public class TypeDescription {
    public List<String> TypeElements = new ArrayList<>();

    public TypeDescription (){
    }

    public TypeDescription(List<String> arguments, int startPos, int endPos) {
        fillType(arguments, startPos, endPos);
    }

    public TypeDescription(String type) {
        TypeElements.clear();
        TypeElements.add(type);
    }

    public void setVoid(){
        TypeElements.clear();
        TypeElements.add("void");
    }

    public String getSimpleName() {
        return TypeElements.get(TypeElements.size()-1);
    }

    public void fillType(List<String> arguments, int start, int end) {
        TypeElements.clear();
        IntStream.rangeClosed(start, end).forEach(
                i -> {
                    String argContent = arguments.get(i);
                    if(argContent.equals(":")) {
                        return;
                    }

                    TypeElements.add(argContent);
                }
        );

    }

    @Override
    public String toString() {
        return getSimpleName();
    }
}
