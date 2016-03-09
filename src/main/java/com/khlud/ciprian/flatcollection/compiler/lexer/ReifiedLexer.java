package com.khlud.ciprian.flatcollection.compiler.lexer;

import com.khlud.ciprian.flatcollection.compiler.lexer.matchers.ITokenMatcher;
import com.khlud.ciprian.flatcollection.compiler.lexer.matchers.IdentifierWordMatcher;
import com.khlud.ciprian.flatcollection.compiler.lexer.matchers.NumberMatcher;
import com.khlud.ciprian.flatcollection.model.PairT;
import com.khlud.ciprian.flatcollection.utils.OsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 2/27/2016.
 */
public class ReifiedLexer {

    List<ITokenMatcher> _matchers = new ArrayList<>();

    public void initialize() {
        registerReservedWord("\t", FlatTokenKind.Space);
        registerReservedWord(" ", FlatTokenKind.Space);
        registerReservedWord(",", FlatTokenKind.Comma);

        registerReservedWord("<<", FlatTokenKind.ShiftLeft);
        registerReservedWord(">>", FlatTokenKind.ShiftRight);
        registerReservedWord("+=", FlatTokenKind.PlusEqualOp);
        registerReservedWord("==", FlatTokenKind.AreEqualOp);

        registerReservedWord("<", FlatTokenKind.LessThan);
        registerReservedWord(">", FlatTokenKind.GreaterThan);

        registerReservedWord("*", FlatTokenKind.Mul);
        registerReservedWord("/", FlatTokenKind.Div);
        registerReservedWord("-", FlatTokenKind.Sub);
        registerReservedWord("+", FlatTokenKind.Plus);

        registerReservedWord("@", FlatTokenKind.AtOp);

        registerReservedWord("(", FlatTokenKind.ParenOpen);
        registerReservedWord(")", FlatTokenKind.ParenClose);
        registerReservedWord("[", FlatTokenKind.SquareOpen);
        registerReservedWord("]", FlatTokenKind.SquareClose);
        registerReservedWord("{", FlatTokenKind.CurlyOpen);
        registerReservedWord("}", FlatTokenKind.CurlyClose);

        registerReservedWord(":", FlatTokenKind.Colon);
        registerReservedWord(";", FlatTokenKind.SemiColon);
        registerReservedWord("=", FlatTokenKind.AssingOp);
        registerReservedWord(".", FlatTokenKind.DotOp);

        _matchers.add(new IdentifierWordMatcher());
        _matchers.add(new NumberMatcher());
    }

    void registerReservedWord(String _word, FlatTokenKind tokenKind) {
        _matchers.add((line) -> {
            if (line.startsWith(_word)) {
                return new PairT<>(_word.length(), tokenKind);
            }
            return null;
        });
    }

    public List<TokenDefinition> getTokens(String fileName, boolean skipSpaces) throws Exception {
        List<String> lines = OsUtils.readAllLines(fileName);

        List<List<TokenDefinition>> tokens = tokenize(lines);
        List<TokenDefinition> tokenFlat = new ArrayList<>();
        tokens.stream().forEach((collection)
                -> tokenFlat.addAll(collection.stream().filter(
                        tokenDefinition -> !skipSpaces || tokenDefinition.Kind != FlatTokenKind.Space)
                        .collect(Collectors.toList())));
        return tokenFlat;
    }

    public List<List<TokenDefinition>> tokenize(List<String> lines) throws Exception {

        List<List<TokenDefinition>> rowTokens = new ArrayList<>();
        for (String line : lines) {
            List<TokenDefinition> tokens = tokenizeRow(line);
            rowTokens.add(tokens);
        }
        return rowTokens;
    }

    public List<TokenDefinition> tokenizeRow(String line) throws Exception {
        List<TokenDefinition> result = new ArrayList<>();
        while (!line.isEmpty()) {
            boolean found = false;
            for (ITokenMatcher matcher : _matchers) {
                PairT<Integer, FlatTokenKind> matchResult = matcher.matches(line);
                if (matchResult == null) {
                    continue;
                }
                line = addToken(line, result, matchResult);
                found = true;
                break;
            }
            if (!found) {
                throw new Exception("Invalid row: '" + line + "'");
            }
        }
        TokenDefinition eoln = new TokenDefinition();
        eoln.Content = "";
        eoln.Kind = FlatTokenKind.Eoln;
        result.add(eoln);
        return result;
    }

    private String addToken(String line, List<TokenDefinition> tokens, PairT<Integer, FlatTokenKind> matchResult) {
        String result = line.substring(matchResult._key);
        TokenDefinition token = new TokenDefinition();
        token.Content = line.substring(0, matchResult._key);
        token.Kind = matchResult._value;
        tokens.add(token);
        return result;

    }
}
