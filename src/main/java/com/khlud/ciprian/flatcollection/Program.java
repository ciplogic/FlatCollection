/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.compiler.ReifiedCompiler;
import com.github.mustachejava.*;
import com.google.gson.Gson;
import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.model.CompilerConfig;
import com.khlud.ciprian.flatcollection.utils.OsUtils;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import net.openhft.compiler.CompilerUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ciprian
 */
public class Program {

    public static void main(String[] args) throws Exception {
        Program p = new Program();
        p.parseMustache();

        TemplateMaster templateMaster = new TemplateMaster();
        String[] fieldNames = {"X", "Y"};
        Object[] objects = {"FlatCollections", "Point3D", "int", 3, fieldNames};

        templateMaster.fillTemplate("ArrayListSection", objects);

        //p.parseFlatFile();
        //p.runGeneratedCode();
        //runCompiler();
    }

    void parseMustache() throws IOException {
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("name", "Mustache");
        scopes.put("feature", new Feature("Perfect!"));

        MustacheFactory mf = new DefaultMustacheFactory();
        StringReader reader = new StringReader("{{name}}, {{feature.description}}!");
        Mustache mustache = mf.compile(reader, "scopes");
        StringWriter writer = new StringWriter();
        mustache.execute(writer, scopes);
        writer.flush();
        String result = writer.getBuffer().toString();
        System.out.println("Result:" + result);
    }

    void parseFlatFile() throws Exception {
        ReifiedCompiler compiler = new ReifiedCompiler();
        compiler.initialize();
        String[] directoryFiles = OsUtils.getDirectoryFiles("./", true,
                file -> file.getName().endsWith(".flat"));
        Arrays.stream(directoryFiles).forEach(flatFile -> {
            try {
                compiler.parseFull(flatFile);
            } catch (Exception ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );
        ProgramModel programModel = compiler.programModel();
        compiler.generateCode("FlatCollections", programModel);

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
