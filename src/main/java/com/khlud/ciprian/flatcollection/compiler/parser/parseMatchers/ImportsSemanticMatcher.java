package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.TypeDescription;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 3/9/2016.
 */
public class ImportsSemanticMatcher extends FoldParseHandler {

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {

        ClassModel classModel = (ClassModel) nodeModel;

        List<TypeDescription> imports
                = splitTokensInRows(macro._childrenTokens)
                .stream()
                .map(TypeDescription::new)
                .filter(typeDescription -> typeDescription.TypeElements.size() > 0)
                .collect(Collectors.toList());

        classModel.Imports = imports;
    }
}
