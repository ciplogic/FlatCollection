package com.khlud.ciprian.flatcollection.compiler.lexer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 2/27/2016.
 */
public class TokenDefinition {

    public String Content;
    public FlatTokenKind Kind;

    @Override
    public String toString() {
        return Content + " [" + Kind + "]";
    }

    public static List<String> getTokensOfKind(List<TokenDefinition> tokens, FlatTokenKind kind) {
        return tokens.stream()
                .filter(it -> it.Kind == kind)
                .map(it -> it.Content)
                .collect(Collectors.toList());
    }

    public TokenDefinition clone() {
        TokenDefinition tokenDefinition = new TokenDefinition();
        tokenDefinition.Kind = Kind;
        tokenDefinition.Content = Content;
        return tokenDefinition;
    }
}
