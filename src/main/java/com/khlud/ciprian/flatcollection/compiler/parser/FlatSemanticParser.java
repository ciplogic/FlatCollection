package com.khlud.ciprian.flatcollection.compiler.parser;

import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers.*;
import com.khlud.ciprian.flatcollection.compiler.preParser.FlatPreParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * Created by Ciprian on 2/29/2016.
 */
public class FlatSemanticParser {

    public ProgramModel _programModel = new ProgramModel();

    public ProgramModel get_programModel() {
        return _programModel;
    }

    static Map<String, IFoldParseHandler> _handlers = new HashMap<>();

    public static void initialize(FlatPreParser preParser) {

        addHandler("flat", new FlatSemanticMatcher());
        addHandler("class", new ClassSemanticMatcher());
        addHandler("where", new DefineSemanticMatcher());
        addHandler("sub", new MethodSemanticMatcher());
        addHandler("const", new ConstSemanticMatcher());
        addHandler("each", new EachSemanticMatcher());

        addHandler("var", new VarSemanticMatcher());
        addHandler("validate", new ValidateSemanticMatcher());
        addHandler("specialize", new SpecializeSemanticMatcher());
        addHandler("imports", new ImportsSemanticMatcher());

        preParser.initialize(_handlers.keySet().stream());
    }

    private static void addHandler(String tokenName, FoldParseHandler handler) {
        _handlers.put(tokenName, handler);
    }

    public static List<String> getIdentifiers(List<TokenDefinition> tokens) {
        return TokenDefinition.getTokensOfKind(tokens, FlatTokenKind.Identifier);
    }

    public static void parseMacro(NodeModel nodeModel, FoldedMacro macro) throws Exception {
        String macroName = macro.getName();
        if (!_handlers.containsKey(macroName)) {
            throw new Exception("Macro: '" + macroName + "' not found");
        }
        IFoldParseHandler handler = _handlers.get(macroName);
        out.println("Macro: '" + macroName + "' found and is parsed");
        handler.parseMacro(nodeModel, macro);
    }

    public void parseMacros(List<FoldedMacro> foldedMacros) throws Exception {
        parse(_programModel, foldedMacros);
    }

    public static void parse(NodeModel nodeModel, List<FoldedMacro> macros) throws Exception {
        for (FoldedMacro macro : macros) {
            parseMacro(nodeModel, macro);
        }
    }
}
