/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection;

import spark.template.mustache.MustacheTemplateEngine;


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
