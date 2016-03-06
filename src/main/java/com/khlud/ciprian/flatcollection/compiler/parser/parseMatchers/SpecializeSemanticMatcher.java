package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.*;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;

import static com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser.getIdentifiers;

/**
 * Created by Ciprian on 3/2/2016.
 */
public class SpecializeSemanticMatcher extends FoldParseHandler {
    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        List<List<TokenDefinition>> rowDefinitions = splitTokensInRows(macro._childrenTokens);
        ProgramModel programModel = (ProgramModel)nodeModel;
        List<String> attributes =  getIdentifiers( macro._attributeTokens);

        List<String> children =  getIdentifiers( macro._childrenTokens);

        SpecializeModel specializeModel = new SpecializeModel(attributes.get(0), children.get(0));
        programModel.addSpecializeModel(specializeModel);
        specializeModel.processDefinitions();

    }
}
