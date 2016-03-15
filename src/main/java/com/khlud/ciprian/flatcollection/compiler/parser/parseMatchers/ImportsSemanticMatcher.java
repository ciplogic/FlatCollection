package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.TypeDescription;
import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
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
                .map(tokens -> tokens.stream()
                        .filter(tok->tok.Kind!= FlatTokenKind.Space)
                        .collect(Collectors.toList()))
                .filter(tokens -> tokens.size()>0)
                .map(TypeDescription::new)
                .collect(Collectors.toList());

        classModel.Imports = imports;
    }
}
