/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection.codegen;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ciprian
 */
public class ListOfCodeGenerator {

    FlatClazzDiscription _description;

    MustacheTemplateEngine _engineArrayList = new MustacheTemplateEngine();

    public ListOfCodeGenerator(FlatClazzDiscription description) {
        _description = description;
    }

    private String intToString(int value) {
        return new Integer(value).toString();
    }

    public String generateArrayList() {
        Map<String, String> valuesView = new HashMap();

        valuesView.put("typeName", _description.simpleName);
        valuesView.put("valueType", _description.fieldTypeName);
        valuesView.put("packageName", "FlatCollections");
        valuesView.put("countFields", intToString(_description.fieldNames.length));

        ModelAndView modelAndView = new ModelAndView(valuesView, "ArrayListOfValue.mustache");
        String iteratorCode = _engineArrayList.render(modelAndView);
        StringBuilder sb = new StringBuilder();
        sb.append(iteratorCode);

        return sb.toString();
    }

    public String generateIterator() {
        String fieldSection = generate();
        Map<String, String> valuesView = new HashMap<>();

        valuesView.put("typeName", _description.simpleName);
        valuesView.put("valueType", _description.fieldTypeName);
        valuesView.put("packageName", "FlatCollections");
        valuesView.put("countFields", intToString(_description.fieldNames.length));

        ModelAndView modelAndView = new ModelAndView(valuesView, "flatCursor.mustache");
        String iteratorCode = _engineArrayList.render(modelAndView);

        return iteratorCode
                + fieldSection
                + "\n}\n";
    }

    String generate() {
        StringBuilder sb = new StringBuilder();
        int[] index = {0};
        HashMap<String, String> valuesView = new HashMap<>();
        valuesView.put("typeField", _description.fieldTypeName);
        Arrays.stream(_description.fieldNames).forEach(
                field
                        -> {
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
