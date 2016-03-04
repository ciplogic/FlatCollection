package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;

/**
 * Created by Ciprian on 3/2/2016.
 */
public class ClassSemanticMatcher extends FoldParseHandler {
    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        ProgramModel programModel = (ProgramModel)nodeModel;
        List<String> attributes= FlatSemanticParser.getIdentifiers(macro._attributeTokens);
        String className = attributes.get(0);
        attributes.remove(0);
        ClassModel classModel = programModel.addClass(className, attributes);
        parseChildren(classModel, macro);
        try {
            FlatSemanticParser.parse(classModel,macro._childrenMacros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
