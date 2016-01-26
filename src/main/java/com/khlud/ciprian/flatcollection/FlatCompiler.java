/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection;

import com.khlud.ciprian.flatcollection.utils.*;
import spark.template.mustache.MustacheTemplateEngine;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 * @author Ciprian
 */
class FlatCompiler {

    MustacheTemplateEngine _engineArrayList;
    public FlatCompiler(){
        _engineArrayList = new MustacheTemplateEngine();
    }
    void buildType(String typeName) {

        ClassExtracter extracter = new ClassExtracter();
        extracter.build(typeName);
    }
    
}
