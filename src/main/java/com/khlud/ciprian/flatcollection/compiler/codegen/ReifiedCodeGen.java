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
        Iterable<SpecializeModel> specializations = programModel.getSpecializations();
        for (SpecializeModel specializeModel : specializations) {
            specialize(programModel, specializeModel);
        }
    }

    private void specialize(ProgramModel programModel, SpecializeModel specializeModel) {
        try {
            specializeType(programModel, specializeModel.definedClass, specializeModel.specializations);
        } catch (Exception ex) {
            Logger.getLogger(ReifiedCodeGen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void specializeType(ProgramModel programModel, ClassModel classModel, List<String> specialization) throws Exception {
        String className = classModel.name;
        PackageModel packageModel = (PackageModel) classModel._parent;

        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> generics = buildArguments(classModel, specialization);
        generateDefinitions(programModel, classModel, generics);
        stringBuilder
                .append("package ")
                .append(packageModel._typeDescription.getFullName())
                .append(";\n\n");

        writeImports(classModel, stringBuilder);
        String combinedClassName = className + translateGenericList(classModel.GenericArgs, generics, "");

        stringBuilder
                .append("public class ")
                .append(combinedClassName);

        stringBuilder.append("{ \n");

        ReifiedUtils.writeVariables(classModel.Variables, generics, stringBuilder);
        ReifiedUtils.writeConstants(classModel.Constants, generics, stringBuilder);
        writeMethods(classModel.Methods, generics, combinedClassName, stringBuilder);
        writeEach(classModel.EachModels, generics, combinedClassName, stringBuilder);

        stringBuilder.append("}\n");

        out.println(stringBuilder);
        OsUtils.writeAllText(combinedClassName + ".java", stringBuilder.toString());
    }

    private void writeEach(List<EachInClassModel> eachModels, Map<String, Object> generics, String combinedClassName, StringBuilder stringBuilder) {

        eachModels.stream()
                .forEach(
                        each ->{
                            int[] index = {0};
                            List <String> eachFields = (List<String>) generics.get(each.CollectionName);
                            eachFields.forEach(
                                    eachField ->{
                                        generics.put(each.ItemName, eachField);
                                        generics.put(each.IndexName, Integer.toString(index[0]));
                                        writeMethods(each.Methods, generics, combinedClassName, stringBuilder);

                                        index[0]++;
                                    }
                            );
                            generics.remove(each.ItemName);
                            generics.remove(each.IndexName);
                        }
                );
    }

    private void writeImports(ClassModel classModel, StringBuilder stringBuilder) {
        classModel.Imports.stream()
                .forEach(
                        typeDescription -> {
                            stringBuilder.append(
                                    "import ")
                                    .append(Joiner.on("").join(typeDescription.TypeElements))
                                    .append(";\n\n");
                        }
                );
    }

    private void generateDefinitions(ProgramModel programModel, ClassModel classModel, Map<String, Object> generics) throws Exception {
        for (PairT<String, List<String>> definition : classModel.Definitions) {
            List<String> expressionValue = definition.getValue();

            Object resolvedValue = resolveExpression(programModel, expressionValue, generics);
            generics.put(definition.getKey(), resolvedValue);
        }
    }

    private Object resolveExpression(ProgramModel programModel, List<String> expressionValue, Map<String, Object> generics) throws Exception {
        String typeToResolve = expressionValue.get(0);
        Object typeDefinition = generics.get(typeToResolve);
        String typeDefinitionName = typeDefinition.toString();
        NodeModel typeModel = programModel.locate(typeDefinitionName);
        String verbToResolve = expressionValue.get(2);
        Object result = typeModel.resolveExpression(verbToResolve);
        return result;
    }

    private String translateGenericList(List<TypeDescription> genericArguments, Map<String, Object> generics, String joinText) {
        List<String> translatedGenerics = genericArguments.stream()
                .map(generic -> translateGeneric(generic, generics))
                .collect(Collectors.toList());

        return Joiner.on(joinText).join(translatedGenerics);
    }

    private String translateGeneric(TypeDescription genericArgument, Map<String, Object> generics) {
        Object description = generics.get(genericArgument.getSimpleName());

        return description.toString();
    }

    private Map<String, Object> buildArguments(ClassModel classModel, List<String> specialization) {
        Map<String, Object> result = new HashMap<>();
        int pos = 0;
        for (TypeDescription genericName : classModel.GenericArgs) {
            result.put(genericName.getSimpleName(), specialization.get(pos));
            pos++;
        }
        return result;
    }

    private void writeMethods(List<MethodModel> Methods, Map<String, Object> generics, String className, StringBuilder stringBuilder) {
        Methods.stream()
                .forEach(
                        methodModel -> {
                            writeMethodSignature(methodModel, generics, className, stringBuilder);
                            writeMethodBody(methodModel, generics, stringBuilder);
                            stringBuilder.append("}\n");
                        }
                );

    }

    private void writeMethodBody(MethodModel methodModel, Map<String, Object> generics, StringBuilder stringBuilder) {
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

    private void writeMethodSignature(MethodModel methodModel, Map<String, Object> generics, String className, StringBuilder stringBuilder) {
        stringBuilder.append(" public ");
        MethodSignature signature = methodModel.signature;

        out.println("Writing signature: " + methodModel._parent.name + "." + signature);
        List<String> returnType
                = ReifiedUtils.specializeGenericWords(signature.returnType, generics);

        if (signature.isConstructor) {
            stringBuilder.append(" ").append(className).append("(");
        } else {
            ReifiedUtils.writeTexts(stringBuilder, returnType.stream());
            List<String> methodNameElements = new ArrayList<>();
            specializeGenericTexts(
                    signature.methodName.TypeElements.stream(),
                    generics)
                    .forEach(methodNameElements::add);
            String specializedMethodName = Joiner.on("").join(methodNameElements);
            stringBuilder.append(" ").append(specializedMethodName).append("(");
        }

        List<String> argumentTexts = signature.arguments.stream().map(
                pair -> {
                    List<String> argTexts = new ArrayList<>();
                    specializeGenericTexts(
                            pair._value.TypeElements.stream(),
                            generics)
                            .forEach(argTexts::add);
                    argTexts.add(" ");
                    argTexts.add(pair.getKey());
                    String argument = Joiner.on("").join(argTexts);
                    return argument;
                }
        ).collect(Collectors.toList());
        String joinedTexts = Joiner.on(", ").join(argumentTexts);
        stringBuilder.append(joinedTexts)
                .append(") {");
    }

}
