/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection;

import com.google.gson.Gson;
import com.khlud.ciprian.flatcollection.compiler.ReifiedCompiler;
import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.model.CompilerConfig;
import com.khlud.ciprian.flatcollection.utils.OsUtils;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ciprian
 */
public class Program {

    public static void main(String[] args) throws Exception {
        Program p = new Program();
        p.parseFlatFile();

        //runCompiler();
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

}
