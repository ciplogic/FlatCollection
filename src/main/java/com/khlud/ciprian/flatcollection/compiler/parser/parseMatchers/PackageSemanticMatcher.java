/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.*;
import com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;

/**
 *
 * @author Ciprian
 */
public class PackageSemanticMatcher 
        extends FoldParseHandler {

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        final ProgramModel classModel = (ProgramModel) nodeModel;

        List<String> arguments = tokensToContentList(macro._attributeTokens, true);

        PackageModel eachModel = classModel.addPackage(arguments);

        try {
            FlatSemanticParser.parse(eachModel, macro._childrenMacros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
