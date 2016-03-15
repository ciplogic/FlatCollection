package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.EachInClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.MethodModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MethodSemanticMatcher extends FoldParseHandler {

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {

        List<String> arguments = tokensToContentList(macro._attributeTokens, true);

        MethodSignature signature = buildSignature(arguments);
        if(nodeModel instanceof  ClassModel) {
            ClassModel classModel = (ClassModel) nodeModel;
            MethodModel method = classModel.addMethod(signature);
            method.body = macro._childrenTokens;
        } else
        if(nodeModel instanceof EachInClassModel) {

            EachInClassModel eachInClassModel = (EachInClassModel) nodeModel;
            MethodModel method = eachInClassModel.addMethod(signature);
        }
    }

    private MethodSignature buildSignature(List<String> arguments) {
        MethodSignature signature = new MethodSignature();
        signature.setName(arguments.get(0));
        if (arguments.size() == 1) {
            signature.returnType.setVoid();
            return signature;
        }
        int openParenIndex = arguments.indexOf("(");

        int closeParenIndex = arguments.indexOf(")");
        if (openParenIndex > 0) {
            List<String> tokensArguments = new ArrayList<>();
                    IntStream.rangeClosed(openParenIndex+1, closeParenIndex-1)
                    .forEach(it -> tokensArguments.add(arguments.get(it)));
            signature.buildArguments(tokensArguments);
        }
        if (arguments.size() > 2 && (closeParenIndex > 0)) {
            signature.returnType.fillType(arguments, closeParenIndex + 2, arguments.size() - 1);
            return signature;
        }
        return signature;
    }
}
