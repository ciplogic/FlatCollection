package com.khlud.ciprian.flatcollection.compiler.codeModel;

import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Ciprian on 3/4/2016.
 */
public class TypeDescription {

    public List<String> TypeElements = new ArrayList<>();

    public TypeDescription() {
        setVoid();
    }

    public TypeDescription(List<String> arguments, int startPos, int endPos) {
        fillType(arguments, startPos, endPos);
    }

    public TypeDescription(String type) {
        TypeElements.clear();
        TypeElements.add(type);
    }

    public TypeDescription(List<TokenDefinition> elements) {
        TypeElements.clear();
        elements.stream()
                .filter(it -> it.Kind != FlatTokenKind.Space)
                .forEach(it -> TypeElements.add(it.Content));
    }

    public void setVoid() {
        TypeElements.clear();
        TypeElements.add("void");
    }

    public String getSimpleName() {
        return TypeElements.get(TypeElements.size() - 1);
    }

    public void fillType(List<String> arguments, int start, int end) {
        TypeElements.clear();
        IntStream.rangeClosed(start, end).forEach(
                i -> {
                    String argContent = arguments.get(i);
                    TypeElements.add(argContent);
                }
        );
        if (TypeElements.size() == 0) {
            setVoid();
        }
    }

    @Override
    public String toString() {
        return getSimpleName();
    }
}
