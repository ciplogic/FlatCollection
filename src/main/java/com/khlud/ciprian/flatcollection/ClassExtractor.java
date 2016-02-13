package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.codegen.CodeGenerator;
import com.khlud.ciprian.flatcollection.codegen.FlatClassDescription;
import com.khlud.ciprian.flatcollection.model.CompilerLayoutDescription;
import com.khlud.ciprian.flatcollection.model.PrimitiveFieldCollector;
import com.khlud.ciprian.flatcollection.typedesc.TypeCode;
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
    public CompilerLayoutDescription build(String typeName) {
        PrimitiveFieldCollector primitiveFieldCollector = new PrimitiveFieldCollector();
        CompilerLayoutDescription layoutDescription = primitiveFieldCollector.build(typeName);

        return layoutDescription;
    }


    public static void WriteLayoutToPath(String outputPath, CompilerLayoutDescription layoutDescription) {
        FlatClassDescription description = new FlatClassDescription(
                layoutDescription.simpleTypeName(),
                layoutDescription.fields,
                layoutDescription.fieldType);
        CodeGenerator codeGenerator = new CodeGenerator(description);

        String generatedCode = codeGenerator.generateIterator();

        String _fileName = "FlatCursor" + layoutDescription.simpleTypeName() + ".java";
        writeAllText(pathCombine( outputPath, _fileName), generatedCode);

        String arrayCode = codeGenerator.generateArrayList();

        String arrayFileName = "ArrayListOf" + layoutDescription.simpleTypeName() + ".java";
        writeAllText(pathCombine( outputPath, arrayFileName), arrayCode);
    }
}
