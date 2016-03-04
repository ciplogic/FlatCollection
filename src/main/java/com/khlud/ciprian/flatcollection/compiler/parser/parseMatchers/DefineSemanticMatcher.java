package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;
import com.khlud.ciprian.flatcollection.model.PairT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 3/2/2016.
 */
public class DefineSemanticMatcher extends FoldParseHandler {
    public List<PairT<String, List<String>>> definitions = new ArrayList<>();

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        List<List<TokenDefinition>> rowDefinitions = splitTokensInRows(macro._childrenTokens);
        parseRows(rowDefinitions);
        ClassModel classModel = (ClassModel) nodeModel;
        classModel.Definitions = definitions;
    }

    private void parseRows(List<List<TokenDefinition>> rowDefinitions) {
        rowDefinitions.stream().forEach(
            row -> {
                String definitionName = row.get(0).Content;
                List<String> definition = row.stream()
                        .skip(2)
                        .map(token->token.Content)
                        .collect(Collectors.toList());
                definitions.add(new PairT<>(definitionName, definition));
            }
        );
    }
}
