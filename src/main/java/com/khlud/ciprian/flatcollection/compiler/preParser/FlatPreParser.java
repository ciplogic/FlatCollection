package com.khlud.ciprian.flatcollection.compiler.preParser;

import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Ciprian on 2/28/2016.
 */
public class FlatPreParser {

    Set<String> _reserved = new HashSet<>();

    public void initialize(Stream<String> tokens) {
        tokens.forEach(this::addReserved);
    }

    private void addReserved(String flatStr) {
        _reserved.add(flatStr);
    }

    public List<FoldedMacro> preParse(List<TokenDefinition> tokenDefinitionList) {
        List<TokenDefinition> definitionList = tokenDefinitionList;
        List<FoldedMacro> result = new ArrayList<>();
        while (true) {

            int nextFoldedPosition = nextIndexOf(definitionList, 0, astNode
                    -> _reserved.contains(astNode.Content)
            );
            if (nextFoldedPosition == -1) {
                break;
            }
            int lookForCloseCurly = nextFoldedPosition + 1;
            int nextEndCurly;
            do {
                nextEndCurly = nextIndexOf(definitionList, lookForCloseCurly, astNode
                        -> astNode.Kind == FlatTokenKind.CurlyClose);
                if (nextEndCurly == -1) {
                    return result;
                }

                int countOpenCurly = countInstances(definitionList, nextFoldedPosition, nextEndCurly, astNode
                        -> astNode.Kind == FlatTokenKind.CurlyOpen);
                int countCloseCurly = countInstances(definitionList, nextFoldedPosition, nextEndCurly, astNode
                        -> astNode.Kind == FlatTokenKind.CurlyClose);
                if (countOpenCurly == countCloseCurly) {
                    break;
                }
                lookForCloseCurly = nextEndCurly + 1;
            } while (true);
            FoldedMacro macro = new FoldedMacro();
            macro._rootToken = definitionList.get(nextFoldedPosition);
            int nextOpenCurly = nextIndexOf(definitionList, nextFoldedPosition, astNode
                    -> astNode.Kind == FlatTokenKind.CurlyOpen);

            macro._attributeTokens = FoldedMacro.getRangeTokens(definitionList, nextFoldedPosition + 1, nextOpenCurly - 1);
            macro._childrenTokens = FoldedMacro.getRangeTokens(definitionList, nextOpenCurly + 1, nextEndCurly - 1);
            FoldedMacro.removeRange(definitionList, nextFoldedPosition, nextEndCurly);
            macro._childrenMacros = this.preParse(macro._childrenTokens);
            result.add(macro);
        }

        return result;
    }

    <T> int previousIndexOf(List<T> tokens, int index, Predicate<T> matches) {
        for (int i = index; i >= 0; i--) {
            if (matches.test(tokens.get(i))) {
                return i;
            }
        }
        return -1;
    }

    <T> int nextIndexOf(List<T> tokens, int index, Predicate<T> matches) {
        for (int i = index; i < tokens.size(); i++) {
            if (matches.test(tokens.get(i))) {
                return i;
            }
        }
        return -1;
    }

    <T> int countInstances(List<T> tokens, int start, int end, Predicate<T> matches) {
        int result = 0;
        for (int i = start; i <= end; i++) {
            if (matches.test(tokens.get(i))) {
                result++;
            }
        }
        return result;
    }
}
