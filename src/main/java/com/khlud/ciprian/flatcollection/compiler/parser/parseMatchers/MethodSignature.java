package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.TypeDescription;
import com.khlud.ciprian.flatcollection.model.PairT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 3/2/2016.
 */
public class MethodSignature {

    public boolean isConstructor;
    public List<PairT<String, TypeDescription>> arguments = new ArrayList<>();
    public TypeDescription returnType = new TypeDescription();
    public TypeDescription methodName = new TypeDescription();

    public void buildArguments(List<String> arguments) {
        if (arguments.size() == 0) {
            return;
        }
        int startPos = 0;
        int pos = 0;
        for (String arg : arguments) {
            if (",".equals(arg)) {
                TypeDescription typeDescription = new TypeDescription(arguments, startPos, pos - 2);
                PairT<String, TypeDescription> argument = new PairT<>(arguments.get(pos - 1), typeDescription);
                this.arguments.add(argument);
                startPos = pos + 1;

            }
            pos++;
        }
        TypeDescription typeDescription = new TypeDescription(arguments, startPos, pos - 2);
        PairT<String, TypeDescription> argument = new PairT<>(arguments.get(pos - 1), typeDescription);
        this.arguments.add(argument);
    }

    @Override
    public String toString() {
        return methodName.getFullName() + "(" + arguments.size() + ")";
    }

    public void setName(String name) {
        methodName = new TypeDescription(name);
        isConstructor = "initialize".equals(name);
    }
}
