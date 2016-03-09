package com.khlud.ciprian.flatcollection.typedesc;

/**
 * Created by Ciprian on 1/22/2016.
 */
public enum TypeCode {
    Byte,
    Short,
    Int,
    Long,
    Float,
    Double,
    Boolean,
    Char,
    String,
    Object;

    public static TypeCode getTypeCodeByName(String typeName) {
        switch (typeName) {
            case "byte":
                return Byte;
            case "short":
                return Short;
            case "int":
                return Int;
            case "long":
                return Long;
            case "float":
                return Float;
            case "double":
                return Double;
            case "boolean":
                return Boolean;
            case "char":
                return Char;
            case "java.lang.String":
                return String;
            default:
                return Object;
        }
    }
}
