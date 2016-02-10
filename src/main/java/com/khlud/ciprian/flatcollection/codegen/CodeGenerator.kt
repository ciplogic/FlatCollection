package com.khlud.ciprian.flatcollection.codegen

import spark.ModelAndView
import spark.template.mustache.MustacheTemplateEngine
import java.util.*

/**
 * Created by Ciprian on 1/23/2016.
 */
class CodeGenerator(val _description: FlatClassDescription) {
    internal var _engineArrayList = MustacheTemplateEngine()

    private fun intToString(value: Int): String {
        return value.toString()
    }

    fun generateArrayList(): String {
        val valuesView: MutableMap<String, String> = HashMap()

        valuesView["typeName"] = _description.simpleName
        valuesView["valueType"] = _description.fieldTypeName
        valuesView["packageName"] = "FlatCollections"
        valuesView["countFields"] = intToString(_description.fieldNames.size)

        val modelAndView = ModelAndView(valuesView, "ArrayListOfValue.mustache")
        val iteratorCode = _engineArrayList.render(modelAndView)
        val sb = StringBuilder()
        sb.append(iteratorCode)

        return sb.toString()
    }

    fun generateIterator(): String {
        val fieldSection = generate()
        val valuesView = HashMap<String, String>()

        valuesView["typeName"] = _description.simpleName
        valuesView["valueType"] = _description.fieldTypeName
        valuesView["packageName"] = "FlatCollections"
        valuesView["countFields"] = intToString(_description.fieldNames.size)

        val modelAndView = ModelAndView(valuesView, "flatCursor.mustache")
        val iteratorCode = _engineArrayList.render(modelAndView)

        return iteratorCode +
                fieldSection +
                "\n}\n"
    }

    fun generate(): String {
        val sb = StringBuilder()
        val index = intArrayOf(0)
        val valuesView = HashMap<String, String>()
        valuesView["typeField"] = _description.fieldTypeName
        _description.fieldNames.forEach { field ->
            valuesView["fieldName"] = field
            valuesView["index"] = intToString(index[0])
            val modelAndView = ModelAndView(valuesView, "fieldGetterSetter.mustache")
            val fieldText = _engineArrayList.render(modelAndView)
            sb.append(fieldText)

            index[0]++

        }

        return sb.toString()
    }
}