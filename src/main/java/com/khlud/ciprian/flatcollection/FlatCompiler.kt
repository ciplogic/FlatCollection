package com.khlud.ciprian.flatcollection


import spark.template.mustache.MustacheTemplateEngine


/**
 * @author Ciprian
 */
internal class FlatCompiler {

    fun buildType(typeName: String, outputPath: String) {

        val extracter = ClassExtractor()
        extracter.build(typeName, outputPath)
    }

}
