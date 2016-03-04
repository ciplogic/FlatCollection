package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.TypeDescription;
import com.khlud.ciprian.flatcollection.model.PairT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 3/2/2016.
 */ public class MethodSignature {
    public String name;
    public List<PairT<String, String>> arguments = new ArrayList<>();
    public TypeDescription returnType = new TypeDescription();
}
