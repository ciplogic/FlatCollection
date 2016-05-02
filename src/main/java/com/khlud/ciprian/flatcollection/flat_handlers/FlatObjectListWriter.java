package com.khlud.ciprian.flatcollection.flat_handlers;

import com.khlud.ciprian.flatcollection.utils.OsUtils;

import java.util.List;

/**
 * Created by Ciprian on 5/2/2016.
 */
public class FlatObjectListWriter {
    public static void writeFlatCollection(String destination,
                                       String packageName, String className, String primitiveTypeName, List<String> fieldNames) {
        String cursorGeneratedText = FlatObjectToList.
                writeFlatListCursor(packageName, className, primitiveTypeName, fieldNames);
        OsUtils.createPath(destination);
        OsUtils.writeAllText(destination + "/FlatCursor" + className + ".java", cursorGeneratedText);

        String listGeneratedText = FlatObjectToList.
                writeFlatArrayList(packageName, className, primitiveTypeName, fieldNames.size());
        OsUtils.writeAllText(destination + "/ArrayListOf" + className + ".java", listGeneratedText);
    }
}
