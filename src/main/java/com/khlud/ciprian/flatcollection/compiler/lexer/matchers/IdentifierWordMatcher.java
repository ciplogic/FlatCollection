package com.khlud.ciprian.flatcollection.compiler.lexer.matchers;

import com.khlud.ciprian.flatcollection.compiler.lexer.FlatTokenKind;
import com.khlud.ciprian.flatcollection.model.PairT;

/**
 * Created by Ciprian on 2/27/2016.
 */
public class IdentifierWordMatcher implements ITokenMatcher {
   

    public IdentifierWordMatcher() {

    }

    @Override
    public PairT<Integer, FlatTokenKind> matches(String line) {
       char firstChar = line.charAt(0);
       if(!Character.isLetter(firstChar) && (firstChar!='_')) {
           return null;
       }
       int size = 1;
       while(size < line.length() && isAlphaNueric(line.charAt(size))) {
           size++;
       }
       return new PairT<>(size, FlatTokenKind.Identifier);
    }
    
    static boolean isAlphaNueric(char ch){
        if(Character.isLetterOrDigit(ch)) {
            return true;
        }
        if(Character.isAlphabetic(ch)) {
            return true;
        }
        
        return false;
    }
}
