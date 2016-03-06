package com.khlud.ciprian.flatcollection.compiler.codegen;

import com.google.common.base.Joiner;
import com.khlud.ciprian.flatcollection.compiler.codeModel.*;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.model.PairT;
import com.khlud.ciprian.flatcollection.utils.OsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.khlud.ciprian.flatcollection.compiler.codegen.ReifiedUtils.specializeGenerics;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ciprian on 3/4/2016.
 */
public class ReifiedCodeGen {
    public void generateCode(String path, ProgramModel programModel){
        OsUtils.createPath(path);

        specializeProgram(programModel);
    }

    private void specializeProgram(ProgramModel programModel) {
        for(SpecializeModel specializeModel:programModel.Specializations){
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
        generateDefinitions(classModel, generics );
        stringBuilder
                .append("class ")
                .append(className)
                .append(translateGenericList(classModel.genericArguments, generics, ""));

        stringBuilder.append("{ \n");

        ReifiedUtils.writeVariables(classModel.Variables, generics, stringBuilder);
        ReifiedUtils.writeConstants(classModel.Constants, generics, stringBuilder);
        writeMethods(classModel.Methods, generics, stringBuilder);



        stringBuilder.append("}\n");

        out.println(stringBuilder);
    }



    private void generateDefinitions(ClassModel classModel, Map<String, String> generics) throws Exception {
        ProgramModel programModel = (ProgramModel) classModel._parent;
        for(PairT<String, List<String>> definition : classModel.Definitions){
            List<String> expressionValue = definition.getValue();

            String resolvedValue = resolveExpression(programModel, expressionValue, generics);
            generics.put(definition.getKey(), resolvedValue);
            //programModel.locate();
        }
    }

    private String resolveExpression(ProgramModel programModel, List<String> expressionValue, Map<String, String> generics) throws Exception {
        String typeToResolve = expressionValue.get(0);
        String typeDefinition = generics.get(typeToResolve);
        NodeModel typeModel = programModel.locate(typeDefinition);
        String verbToResolve = expressionValue.get(2);
        String result  = typeModel.resolveExpression(verbToResolve);
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
        for(String genericName: classModel.genericArguments){
            result.put(genericName, specialization.get(pos));
            pos++;
        }
        return result;
    }   
    private void writeMethods(List<MethodModel> Methods, Map<String, String> generics, StringBuilder stringBuilder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
