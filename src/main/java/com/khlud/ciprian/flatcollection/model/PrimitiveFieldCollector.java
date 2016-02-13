package com.khlud.ciprian.flatcollection.model;

import com.khlud.ciprian.flatcollection.typedesc.TypeCode;
import com.khlud.ciprian.flatcollection.utils.ReflectionResolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 2/13/2016.
 */
public class PrimitiveFieldCollector {
    private static boolean isNonObjectType(Class<?> clazz) {
        String name = clazz.getCanonicalName();
        TypeCode typeCode = TypeCode.getTypeCodeByName(name);
        return typeCode != TypeCode.Object;
    }

    public CompilerLayoutDescription build(String typeName) {
        ReflectionResolver resolver = new ReflectionResolver(new ArrayList<>());
        Class fullType = resolver.getClassByFullName(typeName);
        List<Field> fields = getFieldsAsList(fullType);
        List<Pair<Field, String>> populateFieldsList = new ArrayList<>();
        for (Field field : fields)
            populateFields(populateFieldsList, "", field);
        if(populateFieldsList.size()==0)
            return null;
        CompilerLayoutDescription result = new CompilerLayoutDescription();
        result.typeName = fullType.getSimpleName();


        List<String> combinedFieldNames =
                populateFieldsList.stream()
                        .map(pair -> pair.getValue() + pair.getKey().getName())
                        .collect(Collectors.toList());
        result.fields = toStringArray(combinedFieldNames);
        result.fieldType = populateFieldsList.get(0).getKey().getType().getSimpleName();

        return result;
    }

    private static String[] toStringArray(List<String> stringList) {
        String[] resuStrings = new String[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            String item = stringList.get(i);
            resuStrings[i] = item;
        }
        return resuStrings;
    }

    private static List<Field> getFieldsAsList(Class fullType) {
        return Arrays.stream(fullType.getFields())
                .collect(Collectors.toList());
    }

    private void populateFields(List<Pair<Field, String>> populateFieldsList, String prefix, Field field) {
        Class fieldType = field.getType();
        if (isNonObjectType(fieldType)) {
            populateFieldsList.add(new Pair<>(field, prefix));
            return;
        }
        List<Field> fieldsOfField = getFieldsAsList(fieldType);
        for (Field fieldOfField : fieldsOfField) {
            String prefixFieldName = prefix + field.getName();
            populateFields(populateFieldsList, prefixFieldName, fieldOfField);
        }
    }
}
