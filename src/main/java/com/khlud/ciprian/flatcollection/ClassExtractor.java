package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.codegen.CodeGenerator;
import com.khlud.ciprian.flatcollection.codegen.FlatClassDescription;
import com.khlud.ciprian.flatcollection.utils.ReflectionResolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.khlud.ciprian.flatcollection.utils.OsUtils.pathCombine;
import static com.khlud.ciprian.flatcollection.utils.OsUtils.writeAllText;

/**
 * Created by Ciprian on 1/22/2016.
 */
public class ClassExtractor {
    public void build(String typeName, String outputPath) {

        ReflectionResolver resolver = new ReflectionResolver(new ArrayList<>());
        Class fullType = resolver.getClassByFullName(typeName);
        List<Field> fields =
                Arrays.stream(fullType.getFields())
                .collect(Collectors.toList());
        if (fields.size() != 0) {
            buildFieldsStructure(fullType, fields, outputPath);
        }
    }

    private void buildFieldsStructure(Class fullType, List<Field> fields, String outputPath) {

        String fieldTypeName = fields.get(0).getType().getName();
        List<String> fieldTypeNames = fields.stream()
                .map(Field::getName)
                .collect(Collectors.toList());

        FlatClassDescription description = new FlatClassDescription(
                fullType.getSimpleName(),
                fieldTypeNames,
                fieldTypeName);
        CodeGenerator codeGenerator = new CodeGenerator(description);
        String generatedCode = codeGenerator.generateIterator();

        String _fileName = "FlatCursor" + fullType.getSimpleName() + ".java";
        writeAllText(pathCombine( outputPath, _fileName), generatedCode);

        String arrayCode = codeGenerator.generateArrayList();

        String arrayFileName = "ArrayListOf" + fullType.getSimpleName() + ".java";
        writeAllText(pathCombine( outputPath, arrayFileName), arrayCode);


    }
}
