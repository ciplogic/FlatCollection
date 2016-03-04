package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.FlatModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;

import static com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser.getIdentifiers;

/**
 * Created by Ciprian on 3/2/2016.
 */
public class FlatSemanticMatcher
        extends FoldParseHandler {
    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {

        ProgramModel programModel = (ProgramModel)nodeModel;
        List<String> attributes =  getIdentifiers( macro._attributeTokens);

        List<String> children =  getIdentifiers( macro._childrenTokens);
        String childrenType = children.get(children.size()-1);
        children.remove(children.size()-1);

        FlatModel flatDescription = new FlatModel(attributes.get(0), children, childrenType);
        programModel.addFlatType(flatDescription);
    }
}
