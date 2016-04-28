package com.khlud.ciprian.flatcollection.flat_handlers;

import com.khlud.ciprian.flatcollection.templating.TemplateDescription;
import com.khlud.ciprian.flatcollection.templating.TemplateMaster;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ciprian on 4/28/2016.
 */
public class FlatObjectToList {
    public static String writeFlatListCursor(String packageName, String className, String primitiveTypeName, List<String> fieldNames){

        List<Object> objectArgs = Arrays.asList(packageName, className, primitiveTypeName, fieldNames.size(), fieldNames);

        TemplateDescription templateDescription = TemplateMaster.loadTemplate("codeSections/FlatCursor");
        String renderedText = templateDescription.renderObjectList(objectArgs);
        return renderedText;
    }
    public static String writeFlatArrayList(String packageName, String className, String primitiveTypeName, int countFields){

        List<Object> objectArgs = Arrays.asList(packageName, className, primitiveTypeName, countFields);

        TemplateDescription templateDescription = TemplateMaster.loadTemplate("codeSections/ArrayListSection");
        String renderedText = templateDescription.renderObjectList(objectArgs);
        return renderedText;
    }
}
