package com.khlud.ciprian.flatcollection.compiler.preParser;

import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Ciprian on 2/28/2016.
 */
public class FoldedMacro {

    public TokenDefinition _rootToken;
    public List<TokenDefinition> _attributeTokens = new ArrayList<>();
    public List<TokenDefinition> _childrenTokens = new ArrayList<>();

    public List<FoldedMacro> _childrenMacros = new ArrayList<>();

    public static List<TokenDefinition> getRangeTokens(List<TokenDefinition> nodes, int start, int end) {
        List<TokenDefinition> result = new ArrayList<>();
        IntStream.rangeClosed(start, end).forEach(i -> result.add(nodes.get(i)));
        return result;
    }

    public static void removeRange(List<TokenDefinition> definitionList, int startRange, int endRange) {
        for (int i = endRange; i >= startRange; i--) {
            definitionList.remove(i);
        }
    }

    @Override
    public String toString() {
        return "Macro: '" + _rootToken.Content + "'";
    }

    public String getName() {
        return _rootToken.Content;
    }
}
