package com.khlud.ciprian.flatcollection.compiler.codegen;

import com.google.common.base.Joiner;
import com.khlud.ciprian.flatcollection.compiler.codeModel.*;
import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers.MethodSignature;
import com.khlud.ciprian.flatcollection.model.PairT;
import com.khlud.ciprian.flatcollection.utils.OsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.khlud.ciprian.flatcollection.compiler.codegen.ReifiedUtils.specializeGenericTexts;
import static com.khlud.ciprian.flatcollection.compiler.codegen.ReifiedUtils.specializeGenerics;
import static java.lang.System.out;

public class ReifiedCodeGen {

    public void generateCode(String path, ProgramModel programModel) {
        OsUtils.createPath(path);

        specializeProgram(programModel);
    }

    private void specializeProgram(ProgramModel programModel) {
        for (SpecializeModel specializeModel : programModel.Specializations) {
            specialize(specializeModel);
        }
    }

    private void specialize(SpecializeModel specializeModel) {
        try {
            specializeType(specializeModel.definedClass, specializeModel.specializations);
        } catch (Exception ex) {
            Logger.getLogger(ReifiedCodeGen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void specializeType(ClassModel classModel, List<String> specialization) throws Exception {
        String className = classModel.name;
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> generics = buildArguments(classModel, specialization);
        generateDefinitions(classModel, generics);
        String combinedClassName = className + translateGenericList(classModel.genericArguments, generics, "");
        stringBuilder
                .append("class ")
                .append(combinedClassName);

        stringBuilder.append("{ \n");

        ReifiedUtils.writeVariables(classModel.Variables, generics, stringBuilder);
        ReifiedUtils.writeConstants(classModel.Constants, generics, stringBuilder);
        writeMethods(classModel.Methods, generics, combinedClassName, stringBuilder);

        stringBuilder.append("}\n");

        out.println(stringBuilder);
        OsUtils.writeAllText(combinedClassName + ".java", stringBuilder.toString());
    }

    private void generateDefinitions(ClassModel classModel, Map<String, String> generics) throws Exception {
        ProgramModel programModel = (ProgramModel) classModel._parent;
        for (PairT<String, List<String>> definition : classModel.Definitions) {
            List<String> expressionValue = definition.getValue();

            String resolvedValue = resolveExpression(programModel, expressionValue, generics);
            generics.put(definition.getKey(), resolvedValue);
        }
    }

    private String resolveExpression(ProgramModel programModel, List<String> expressionValue, Map<String, String> generics) throws Exception {
        String typeToResolve = expressionValue.get(0);
        String typeDefinition = generics.get(typeToResolve);
        NodeModel typeModel = programModel.locate(typeDefinition);
        String verbToResolve = expressionValue.get(2);
        String result = typeModel.resolveExpression(verbToResolve);
        return result;
    }

    private String translateGenericList(List<String> genericArguments, Map<String, String> generics, String joinText) {
        List<String> translatedGenerics = genericArguments.stream()
                .map(generic -> translateGeneric(generic, generics))
                .collect(Collectors.toList());

        return Joiner.on(joinText).join(translatedGenerics);
    }

    private String translateGeneric(String genericArgument, Map<String, String> generics) {
        String description = generics.get(genericArgument);

        return description;
    }

    private Map<String, String> buildArguments(ClassModel classModel, List<String> specialization) {
        Map<String, String> result = new HashMap<>();
        int pos = 0;
        for (String genericName : classModel.genericArguments) {
            result.put(genericName, specialization.get(pos));
            pos++;
        }
        return result;
    }

    private void writeMethods(List<MethodModel> Methods, Map<String, String> generics, String className, StringBuilder stringBuilder) {
        Methods.stream()
                .forEach(
                        methodModel -> {
                            writeMethodSignature(methodModel, generics, className, stringBuilder);
                            writeMethodBody(methodModel, generics, stringBuilder);
                            stringBuilder.append("}\n");
                        }
                );

    }

    private void writeMethodBody(MethodModel methodModel, Map<String, String> generics, StringBuilder stringBuilder) {
        List<TokenDefinition> bodyTokens = methodModel.body;
        List<TokenDefinition> specializedBody = specializeGenerics(bodyTokens, generics);
        specializedBody.stream()
                .forEach(token -> {
                    if (token.Kind == FlatTokenKind.Eoln) {
                        token.Content = "\n";
                    }
                });
        specializedBody.stream().forEach(
                token -> {
                    stringBuilder.append(token.Content);
                }
        );

    }

    private void writeMethodSignature(MethodModel methodModel, Map<String, String> generics, String className, StringBuilder stringBuilder) {
        stringBuilder.append(" public ");
        MethodSignature signature = methodModel.signature;

        out.println("Writing signature: " + methodModel._parent.name + "." + signature);
        List<String> returnType
                = ReifiedUtils.specializeGenericWords(signature.returnType, generics);

        if (signature.isConstructor) {
            stringBuilder.append(" ").append(className).append("(");
        } else {
            ReifiedUtils.writeTexts(stringBuilder, returnType.stream());
            stringBuilder.append(" ").append(signature.name).append("(");
        }

        List<String> argumentTexts = signature.arguments.stream().map(
                pair -> {
                    List<String> argTexts = new ArrayList<>();
                    specializeGenericTexts(
                            pair._value.TypeElements.stream(),
                            generics)
                    .forEach(it -> argTexts.add(it));
                    argTexts.add(" ");
                    argTexts.add(pair.getKey());
                    String argument = Joiner.on("").join(argTexts);
                    return argument;
                }
        ).collect(Collectors.toList());
        String joinedTexts = Joiner.on(", ").join(argumentTexts);
        stringBuilder.append(joinedTexts)
                .append(") {\n");
    }

}
