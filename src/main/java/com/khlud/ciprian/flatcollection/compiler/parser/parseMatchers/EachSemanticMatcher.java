package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.EachInClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.MethodModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;

/**
 * Created by Ciprian on 3/5/2016.
 */
public class EachSemanticMatcher extends FoldParseHandler {

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        final ClassModel classModel = (ClassModel) nodeModel;

        List<String> arguments = tokensToContentList(macro._attributeTokens, true);
        
        EachInClassModel eachModel = classModel.addEachDefinition();
        eachModel.CollectionName = arguments.get(0);
        eachModel.ItemName = arguments.get(2);
        eachModel.IndexName = arguments.get(4);

        try {
            FlatSemanticParser.parse(eachModel, macro._childrenMacros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
