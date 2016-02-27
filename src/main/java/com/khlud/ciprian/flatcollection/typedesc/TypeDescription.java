package com.khlud.ciprian.flatcollection.typedesc;

/**
 * Created by Ciprian on 1/22/2016.
 */
public class TypeDescription {
    String _fullName;
    TypeCode _typeCode;

    public TypeDescription(final String fullName) {
        _fullName = fullName;
        _typeCode = TypeCode.Object;
    }

    public TypeDescription(final TypeCode typeCode) {
        _typeCode = typeCode;
    }
}
