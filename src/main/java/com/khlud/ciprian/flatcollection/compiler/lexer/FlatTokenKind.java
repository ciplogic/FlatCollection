package com.khlud.ciprian.flatcollection.compiler.lexer;

/**
 * Created by Ciprian on 2/27/2016.
 */
public enum FlatTokenKind {
    Space,
    Eoln,
    Eof,
    Identifier,
    ClassOp,
    WhileOp, ForOp,
    IfOp, 
    FlatOp, 
    CurlyOpen, CurlyClose, 
    ShiftLeft, ShiftRight, 
    LessThan, GreaterThan,
    Comma, 
    ParenOpen, ParenClose, 
    Colon, SemiColon, 
    
    AreEqualOp, AssingOp, 
    DotOp, Number, SquareOpen, SquareClose, Mul, Plus, Sub, Div, PlusEqualOp, AtOp,
    

}
