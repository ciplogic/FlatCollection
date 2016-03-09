package com.khlud.ciprian.flatcollection.compiler.lexer.matchers;

import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.model.PairT;

/**
 * Created by Ciprian on 2/27/2016.
 */
public interface ITokenMatcher {

    public PairT<Integer, FlatTokenKind> matches(String line);
}
