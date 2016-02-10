/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection;

import com.google.gson.Gson;
import com.khlud.ciprian.flatcollection.model.CompilerConfig;
import com.khlud.ciprian.flatcollection.utils.OsUtils;

import java.util.Arrays;

/**
 * @author Ciprian
 */
public class Program {

    public static void main(String[] args) throws Exception {
        FlatCompiler compiler = new FlatCompiler();

        CompilerConfig config = readConfig();

        Arrays.stream(config.Types).forEach(typeDesc -> {
            compiler.buildType(typeDesc.typeName, config.outputPath);
        });
        System.out.println("Success");
    }

    public static CompilerConfig readConfig() throws Exception {
        CompilerConfig config;
        Gson gson = new Gson();
        String json = OsUtils.readAllText("flatcfg.json");
        if (OsUtils.isNullOrEmpty(json)) {
            throw new Exception("define types inside flatc.conf");
        } else {
            config = gson.fromJson(json, CompilerConfig.class);
        }
        return config;
    }
}
