package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 3/2/2016.
 */
public abstract class FoldParseHandler implements IFoldParseHandler {

    void parseChildren(NodeModel nodeModel, FoldedMacro macro) {
        try {
            FlatSemanticParser.parse(nodeModel, macro._childrenMacros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static List<List<String>> getListsOfTexts(List<List<TokenDefinition>> rowDefinitions, boolean skipSpaces) {
        List<List<String>> definitions = new ArrayList<>();
        rowDefinitions.stream().forEach(
                row -> {
                    List<String> tokenContent = tokensToContentList(row, skipSpaces);
                    if (tokenContent.size() == 0) {
                        return;
                    }

                    definitions.add(tokenContent);
                }
        );
        return definitions;
    }

    protected static List<String> tokensToContentList(List<TokenDefinition> tokensList, boolean skipSpaces) {
        List<String> definition = tokensList.stream()
                .filter(tok -> skipSpaces ? tok.Kind != FlatTokenKind.Space : true)
                .map(token -> token.Content)
                .collect(Collectors.toList());
        return definition;
    }

    List<List<TokenDefinition>> splitTokensInParts(List<TokenDefinition> tokens, FlatTokenKind tokenKind) {
        List<List<TokenDefinition>> rowsResult = new ArrayList<>();

        List<TokenDefinition> rowData = new ArrayList<>();
        for (TokenDefinition token : tokens) {
            if (token.Kind != tokenKind) {
                rowData.add(token);
                continue;
            }
            if (rowData.size() > 0) {
                rowsResult.add(rowData);
                rowData = new ArrayList<>();
            }
        }

        if (rowData.size() > 0) {
            rowsResult.add(rowData);
        }
        return rowsResult;
    }

    List<List<TokenDefinition>> splitTokensInRows(List<TokenDefinition> tokens) {
        return splitTokensInParts(tokens, FlatTokenKind.Eoln);
    }
}