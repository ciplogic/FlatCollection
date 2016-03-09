package com.khlud.ciprian.flatcollection.compiler.codegen;

import com.khlud.ciprian.flatcollection.compiler.codeModel.TypeDescription;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ciprian on 3/6/2016.
 */
public class ReifiedUtils {

    public static List<TokenDefinition> specializeGenerics(List<TokenDefinition> tokens, Map<String, Object> generics) {
        List<TokenDefinition> result = tokens.stream()
                .map(token -> {
                            TokenDefinition cloneToken = token.clone();
                            cloneToken.Content = getSpecializedString(generics, token.Content);
                            return cloneToken;
                        }
                ).collect(Collectors.toList());
        return result;
    }

    public static List<String> specializeGenericTexts(List<String> tokens, Map<String, Object> generics) {
        List<String> result = specializeGenericTexts(tokens.stream(), generics)
                .collect(Collectors.toList());
        return result;
    }

    public static Stream<String> specializeGenericTexts(Stream<String> tokens, Map<String, Object> generics) {
        Stream<String> result = tokens
                .map(content -> getSpecializedString(generics, content)
                );
        return result;
    }

    private static String getSpecializedString(Map<String, Object> generics, String content) {
        if ("@".equals(content)) {
            return "";
        }
        Object mappedName = generics.getOrDefault(content, content);
        String newName = mappedName.toString();

        return newName;
    }

    public static void writeVariables(List<List<String>> variables, Map<String, Object> generics, StringBuilder stringBuilder) {
        variables.stream()
                .forEach(
                        rowVariables -> {
                            List<String> tokensGenerics = specializeGenericTexts(rowVariables, generics);

                            tokensGenerics.stream()
                                    .forEach(
                                            it -> stringBuilder.append(it)
                                    );
                            stringBuilder.append(";\n");
                        }
                );

    }

    public static void writeConstants(List<List<String>> variables, Map<String, Object> generics, StringBuilder stringBuilder) {
        variables.stream()
                .forEach(
                        rowVariables -> {
                            List<String> tokensGenerics = specializeGenericTexts(rowVariables, generics);

                            stringBuilder.append("static final ");
                            tokensGenerics.stream()
                                    .forEach(
                                            it -> stringBuilder.append(it)
                                    );
                            stringBuilder.append(";\n");
                        }
                );
    }

    public static List<String> specializeGenericWords(TypeDescription tokens, Map<String, Object> generics) {
        List<String> result = specializeGenericTexts(tokens.TypeElements.stream(), generics)
                .collect(Collectors.toList());
        return result;
    }

    public static void writeTexts(StringBuilder stringBuilder, Stream<String> stream) {
        stream.forEach(it
                -> stringBuilder.append(it)
        );
    }
}
