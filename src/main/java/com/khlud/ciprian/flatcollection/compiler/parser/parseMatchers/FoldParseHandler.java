package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 3/2/2016.
 */
public abstract class FoldParseHandler implements IFoldParseHandler {
    void parseChildren(NodeModel nodeModel, FoldedMacro macro){
        try {
            FlatSemanticParser.parse(nodeModel,macro._childrenMacros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<List<TokenDefinition>> splitTokensInRows(List<TokenDefinition> tokens) {
        List<List<TokenDefinition>> rowsResult = new ArrayList<>();

        List<TokenDefinition> rowData = new ArrayList<>();
        for (TokenDefinition token : tokens) {
            if (token.Kind != FlatTokenKind.Eoln) {
                rowData.add(token);
                continue;
            }
            if (rowData.size() > 0) {
                rowsResult.add(rowData);
                rowData = new ArrayList<>();
            }
        }

        if(rowData.size()>0){
            rowsResult.add(rowData);
        }
        return rowsResult;
    }
}
