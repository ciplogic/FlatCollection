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
    public void setVoid(){
        TypeElements.clear();
        TypeElements.add("void");
    }

    public void fillType(List<String> arguments, int start, int end) {
        TypeElements.clear();
        IntStream.rangeClosed(start, end).forEach(
                i -> {
                    TypeElements.add(arguments.get(i));
                }
        );
    }
}
