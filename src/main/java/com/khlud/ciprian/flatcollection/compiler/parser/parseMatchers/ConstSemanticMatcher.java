package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;

/**
 * Created by Ciprian on 3/2/2016.
 */
public class ConstSemanticMatcher
        extends FoldParseHandler {

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        List<List<TokenDefinition>> rowDefinitions = splitTokensInRows(macro._childrenTokens);
        ClassModel classModel = (ClassModel) nodeModel;

        List<List<String>> definitions = getListsOfTexts(rowDefinitions, true);

        definitions.stream().forEach(list -> list.add(1, " "));

        classModel.Constants = definitions;
    }
}
