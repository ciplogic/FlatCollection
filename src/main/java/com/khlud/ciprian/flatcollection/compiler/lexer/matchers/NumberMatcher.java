package com.khlud.ciprian.flatcollection.compiler.lexer.matchers;

import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.compiler.lexer.matchers.ITokenMatcher;
import com.khlud.ciprian.flatcollection.model.PairT;

/**
 * Created by Ciprian on 2/28/2016.
 */
public class NumberMatcher implements ITokenMatcher {
    @Override
    public PairT<Integer, FlatTokenKind> matches(String line) {
        char firstChar = line.charAt(0);
        if(!Character.isDigit(firstChar))
            return null;
        int pos = 1;
        while(pos < line.length() && Character.isDigit(line.charAt(pos)))
            pos++;
        return new PairT<>(pos, FlatTokenKind.Number);
    }
}
