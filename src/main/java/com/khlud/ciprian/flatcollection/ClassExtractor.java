package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.codegen.ListOfCodeGenerator;
import com.khlud.ciprian.flatcollection.codegen.FlatClazzDiscription;
import com.khlud.ciprian.flatcollection.model.CompilerLayoutDescription;
import com.khlud.ciprian.flatcollection.model.PrimitiveFieldCollector;

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
        FlatClazzDiscription description = new FlatClazzDiscription(
                layoutDescription.simpleTypeName(),
                layoutDescription.fields,
                layoutDescription.fieldType);
        ListOfCodeGenerator codeGenerator = new ListOfCodeGenerator(description);

        String generatedCode = codeGenerator.generateIterator();

        String _fileName = "FlatCursor" + layoutDescription.simpleTypeName() + ".java";
        writeAllText(pathCombine( outputPath, _fileName), generatedCode);

        String arrayCode = codeGenerator.generateArrayList();

        String arrayFileName = "ArrayListOf" + layoutDescription.simpleTypeName() + ".java";
        writeAllText(pathCombine( outputPath, arrayFileName), arrayCode);
    }
}
