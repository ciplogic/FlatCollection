package com.khlud.ciprian.flatcollection

/**
 * @author Ciprian
 */
internal class FlatCompiler {

    fun buildType(typeName: String, outputPath: String) {

        val extracter = ClassExtractor()
        val layout = extracter.build(typeName)

        ClassExtractor.WriteLayoutToPath(outputPath, layout )
    }

}
