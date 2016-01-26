/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection;


import com.khlud.ciprian.flatcollection.utils.OsUtils;

/**
 *
 * @author Ciprian
 */
public class Program {
    public static void main(String[] args){
        FlatCompiler compiler = new FlatCompiler();
        OsUtils.readAllLines("input.flat").stream().forEach(typeName -> {
            compiler.buildType(typeName);
        });
        System.out.println("Success");
    }
}
