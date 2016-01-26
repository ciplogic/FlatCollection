package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.codegen.CodeGenerator;
import com.khlud.ciprian.flatcollection.utils.OsUtils;
import com.khlud.ciprian.flatcollection.utils.ReflectionResolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 1/22/2016.
 */
public class ClassExtracter {
    CompileMode Mode = CompileMode.Fields;

    public void build(String typeName) {

        ReflectionResolver resolver = new ReflectionResolver(new ArrayList<>());
        Class fullType = resolver.getClassByFullName(typeName);
        Field[] fields = fullType.getFields();
        if(fields.length!=0){
            buildFieldsStructure(fullType, fields);
        }
    }

    private void buildFieldsStructure(Class fullType, Field[] fields) {
        List<String> fieldNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toList());
        String fieldTypeName = fields[0].getType().getName();

        CodeGenerator codeGenerator = new CodeGenerator(
                fullType,
                fieldNames,
                fieldTypeName);
        String generatedCode = codeGenerator.generateIterator();

        String _fileName ="FlatCursor" +fullType.getSimpleName()+".java";
        OsUtils.writeAllText(_fileName, generatedCode);


        String arrayCode = codeGenerator.generateArrayList();

        String arrayFileName ="ArrayListOf" +fullType.getSimpleName()+".java";
        OsUtils.writeAllText(arrayFileName, arrayCode);


    }
}
