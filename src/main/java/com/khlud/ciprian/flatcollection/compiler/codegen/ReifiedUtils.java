package com.khlud.ciprian.flatcollection.compiler.codegen;

import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 3/6/2016.
 */
public class ReifiedUtils {

    public static List<TokenDefinition> specializeGenerics(List<TokenDefinition> tokens, Map<String, String> generics){
        List<TokenDefinition> result =  tokens.stream()
                .map( token->{
                            String newName = generics.getOrDefault(token.Content, token.Content);
                            TokenDefinition cloneToken = token.clone();
                            cloneToken.Content = newName;
                            return cloneToken;
                        }
                ).collect(Collectors.toList());
        return result;
    }
    
    public static void writeVariables(List<List<TokenDefinition>> variables, Map<String, String> generics, StringBuilder stringBuilder) {
        variables.stream()
                .forEach(
                        rowVariables -> {
                            List<TokenDefinition> tokensGenerics = specializeGenerics(rowVariables, generics);
                            tokensGenerics.stream()
                                    .forEach(
                                            it->stringBuilder.append(it.Content)
                                    );
                            stringBuilder.append(";\n");
                        }
                );
    
    }
    public static  void writeConstants(List<List<TokenDefinition>> variables, Map<String, String> generics, StringBuilder stringBuilder)
 {
        variables.stream()
                .forEach(
                        rowVariables -> {
                            List<TokenDefinition> tokensGenerics = specializeGenerics(rowVariables, generics);

                            stringBuilder.append("static final ");
                            tokensGenerics.stream()
                                    .forEach(
                                            it->stringBuilder.append(it.Content)
                                    );
                            stringBuilder.append(";\n");
                        }
                );
    }
}
