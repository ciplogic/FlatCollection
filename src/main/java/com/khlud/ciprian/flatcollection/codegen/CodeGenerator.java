package com.khlud.ciprian.flatcollection.codegen;

import com.khlud.ciprian.flatcollection.CompileMode;
import com.khlud.ciprian.flatcollection.utils.OsUtils;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ciprian on 1/23/2016.
 */
public class CodeGenerator {
    private Class<?> _clazz;
    private final List<String> _fieldNames;
    private final String _filedTypeName;


    MustacheTemplateEngine _engineArrayList = new MustacheTemplateEngine();


    public CodeGenerator(Class<?> clazz, List<String> fieldNames, String filedTypeName){
        _clazz = clazz;
        _fieldNames = fieldNames;
        _filedTypeName = filedTypeName;
    }

    public String generateArrayList(){
        Map<String, String> valuesView = new HashMap<>();

        valuesView.put("typeName", _clazz.getSimpleName());
        valuesView.put("valueType", _filedTypeName);
        valuesView.put("packageName", "FlatCollections");
        valuesView.put("countFields",intToString(_fieldNames.size()));

        ModelAndView modelAndView = new ModelAndView(valuesView, "ArrayListOfValue.mustache");
        String iteratorCode = _engineArrayList.render(modelAndView);
        StringBuilder sb = new StringBuilder();
        sb.append(iteratorCode);

        return sb.toString();
    }


    public String generateIterator(){
        String fieldSection = generate();
        Map<String, String> valuesView = new HashMap<>();

        valuesView.put("typeName", _clazz.getSimpleName());
        valuesView.put("valueType", _filedTypeName);
        valuesView.put("packageName", "FlatCollections");
        valuesView.put("countFields",intToString(_fieldNames.size()));

        ModelAndView modelAndView = new ModelAndView(valuesView, "flatCursor.mustache");
        String iteratorCode = _engineArrayList.render(modelAndView);
        StringBuilder sb = new StringBuilder();
        sb.append(iteratorCode);

        sb.append(fieldSection);
        sb.append("\n}\n");
        return sb.toString();
    }

    static final String intToString(int value){
        Integer val = value;
        return val.toString();
    }

    public String generate(){
        StringBuilder sb = new StringBuilder();
        int[]index = {0};
        Map<String, String> valuesView = new HashMap<>();
        valuesView.put("typeField",_filedTypeName);
        _fieldNames.forEach(field->{
            valuesView.put("fieldName",field);
            valuesView.put("index",intToString(index[0]));
            ModelAndView modelAndView = new ModelAndView(valuesView, "fieldGetterSetter.mustache");
            String fieldText = _engineArrayList.render(modelAndView);
            sb.append(fieldText);

            index[0]++;

        });

        return sb.toString();
    }
}
