/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.flat_handlers.FlatObjectListWriter;
import com.khlud.ciprian.flatcollection.flat_handlers.FlatObjectToList;
import net.openhft.compiler.CompilerUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ciprian
 */
public class Program {

    public static void main(String[] args) throws Exception {
        List<String> fieldNames = Arrays.asList("X", "Y");
        FlatObjectListWriter.writeFlatCollection("BuildPoints/src/flatcollections", "flatcollections", "Point3D", "int", fieldNames );
        FlatObjectListWriter.writePrimitiveBuilder("BuildPoints/src/flatcollections", "flatcollections", "Byte", "byte");


        //p.parseFlatFile();
        //p.runGeneratedCode();
        //runCompiler();
    }

    void runGeneratedCode() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // dynamically you can call
        String className = "mypackage.MyClass";
        String javaCode = "package mypackage;\n"
                + "public class MyClass implements Runnable {\n"
                + "    public void run() {\n"
                + "        System.out.println(\"Hello World\");\n"
                + "    }\n"
                + "}\n";
        Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
        Runnable runner = (Runnable) aClass.newInstance();
        runner.run();
    }

}
