package com.khlud.ciprian.flatcollection.compiler.lexer;

import com.khlud.ciprian.flatcollection.utils.OsUtils;

import java.util.List;

/**
 * Created by Ciprian on 2/27/2016.
 */
public class ReifiedLexer {
    public List<TokenDefinition> getTokens(String fileName){
        String content = OsUtils.readAllText(fileName);

        return null;
    }
}
