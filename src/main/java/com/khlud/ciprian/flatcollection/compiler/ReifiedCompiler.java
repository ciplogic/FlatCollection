/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khlud.ciprian.flatcollection.compiler;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.compiler.codegen.ReifiedCodeGen;
import com.khlud.ciprian.flatcollection.compiler.lexer.ReifiedLexer;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FlatPreParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ciprian
 */
public class ReifiedCompiler {

    public ReifiedLexer lexer = new ReifiedLexer();
    FlatSemanticParser semanticParser = new FlatSemanticParser();
    FlatPreParser preParser = new FlatPreParser();
    ReifiedCodeGen codeGen = new ReifiedCodeGen();

    public void initialize() {
        lexer.initialize();
        semanticParser.initialize(preParser);
    }

    public List<List<TokenDefinition>> tokenize(List<String> lines) throws Exception {
        List<List<TokenDefinition>> rowTokens = new ArrayList<>();
        for (String line : lines) {
            List<TokenDefinition> tokens = lexer.tokenizeRow(line);
            rowTokens.add(tokens);
        }
        return rowTokens;
    }

    public void parseFull(String flatFile) throws Exception {
        List<TokenDefinition> tokenFlat = lexer.getTokens(flatFile, false);
        List<FoldedMacro> foldedMacros = preParser.preParse(tokenFlat);
        semanticParser.parseMacros(foldedMacros);

    }

    public FlatSemanticParser getSemanticParser() {
        return semanticParser;
    }

    public ProgramModel programModel() {
        return semanticParser.get_programModel();
    }

    public void generateCode(String flatCollections, ProgramModel programModel) {
        codeGen.generateCode(flatCollections, programModel);
    }
}
