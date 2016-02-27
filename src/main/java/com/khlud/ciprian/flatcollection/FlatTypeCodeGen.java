package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.model.CompilerLayoutDescription;

/**
 * Created by Ciprian on 2/27/2016.
 */
public class FlatTypeCodeGen {

    void buildType(String typeName, String outputPath) {

        ClassExtractor extracter = new ClassExtractor();
        CompilerLayoutDescription layout = extracter.build(typeName);

        ClassExtractor.WriteLayoutToPath(outputPath, layout);
    }
}
