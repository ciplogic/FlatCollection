package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.TypeDescription;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;
import com.khlud.ciprian.flatcollection.model.PairT;

import java.util.ArrayList;
import java.util.List;

public class MethodSemanticMatcher extends FoldParseHandler {

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        ClassModel classModel = (ClassModel) nodeModel;


        List<String> arguments = tokensToContentList(macro._attributeTokens, true);

        MethodSignature signature = buildSignature(arguments);
        classModel.addMethod(signature);
    }

    private MethodSignature buildSignature(List<String> arguments) {
        MethodSignature signature = new MethodSignature();
        signature.name = arguments.get(0);
        if(arguments.size()==1){
            signature.returnType.setVoid();
            return signature;
        }
        int openParenIndex = arguments.indexOf("(");

        int closeParenIndex = arguments.indexOf(")");
        if(openParenIndex > 0) {
            List<String> tokensArguments = arguments.subList(openParenIndex+1, closeParenIndex-openParenIndex+1);
            signature.buildArguments(tokensArguments);
        }
        if(arguments.size()>2 && (closeParenIndex >0)){
            signature.returnType.fillType(arguments, closeParenIndex+1, arguments.size()-1);
            return signature;
        }
        return signature;
    }
}
