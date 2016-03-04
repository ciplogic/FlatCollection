package com.khlud.ciprian.flatcollection.compiler.parser;

import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.TokenDefinition;
import com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers.*;
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

    static {
        initialize();
    }
    public static void initialize(){

        _handlers.put("flat", new FlatSemanticMatcher());
        _handlers.put("class", new ClassSemanticMatcher());
        _handlers.put("define", new DefineSemanticMatcher());
        _handlers.put("sub", new MethodSemanticMatcher());
        _handlers.put("const", new ConstSemanticMatcher());

        _handlers.put("var", new VarSemanticMatcher());
        _handlers.put("validate", new ValidateSemanticMatcher());
        _handlers.put("specialize", new SpecializeSemanticMatcher());
    }

    public static List<String> getIdentifiers(List<TokenDefinition> tokens){
        return TokenDefinition.getTokensOfKind(tokens, FlatTokenKind.Identifier);
    }

    public static void parseMacro(NodeModel nodeModel, FoldedMacro macro) throws Exception {
        String macroName =macro.getName();
        if(!_handlers.containsKey(macroName))
            throw new Exception("Macro: '"+ macroName +"' not found");
        IFoldParseHandler handler = _handlers.get(macroName);
        out.println("Macro: '"+ macroName +"' found and is parsed");
        handler.parseMacro(nodeModel, macro);
    }



    public void parseMacros(List<FoldedMacro> foldedMacros) throws Exception {
        parse(_programModel, foldedMacros);
    }

    public static void parse(NodeModel nodeModel, List<FoldedMacro> macros) throws Exception {
        for(FoldedMacro macro:macros){
            parseMacro(nodeModel, macro);
        }
    }
}
