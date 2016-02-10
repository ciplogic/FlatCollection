package com.khlud.ciprian.flatcollection.codegen;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ciprian on 1/23/2016.
 */
public class CodeGenerator {
    FlatClassDescription _description;
    MustacheTemplateEngine _engineArrayList = new MustacheTemplateEngine();

    public CodeGenerator(FlatClassDescription description) {
        _description = description;

    }

    static final String intToString(int value) {
        Integer val = value;
        return val.toString();
    }

    public String generateArrayList() {
        Map<String, String> valuesView = new HashMap<>();

        valuesView.put("typeName", _description.getSimpleName());
        valuesView.put("valueType", _description.getFieldTypeName());
        valuesView.put("packageName", "FlatCollections");
        valuesView.put("countFields", intToString(_description.getFieldNames().size()));

        ModelAndView modelAndView = new ModelAndView(valuesView, "ArrayListOfValue.mustache");
        String iteratorCode = _engineArrayList.render(modelAndView);
        StringBuilder sb = new StringBuilder();
        sb.append(iteratorCode);

        return sb.toString();
    }

    public String generateIterator() {
        String fieldSection = generate();
        Map<String, String> valuesView = new HashMap<>();

        valuesView.put("typeName", _description.getSimpleName());
        valuesView.put("valueType", _description.getFieldTypeName());
        valuesView.put("packageName", "FlatCollections");
        valuesView.put("countFields", intToString(_description.getFieldNames().size()));

        ModelAndView modelAndView = new ModelAndView(valuesView, "flatCursor.mustache");
        String iteratorCode = _engineArrayList.render(modelAndView);
        StringBuilder sb = new StringBuilder();
        sb.append(iteratorCode);

        sb.append(fieldSection);
        sb.append("\n}\n");
        return sb.toString();
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();
        int[] index = {0};
        Map<String, String> valuesView = new HashMap<>();
        valuesView.put("typeField", _description.getFieldTypeName());
        _description.getFieldNames().forEach(field -> {
            valuesView.put("fieldName", field);
            valuesView.put("index", intToString(index[0]));
            ModelAndView modelAndView = new ModelAndView(valuesView, "fieldGetterSetter.mustache");
            String fieldText = _engineArrayList.render(modelAndView);
            sb.append(fieldText);

            index[0]++;

        });

        return sb.toString();
    }
}
