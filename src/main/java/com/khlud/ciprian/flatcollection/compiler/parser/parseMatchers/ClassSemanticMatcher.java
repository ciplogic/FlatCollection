package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ClassModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.PackageModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.TypeDescription;
import com.khlud.ciprian.flatcollection.compiler.parser.FlatSemanticParser;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ciprian on 3/2/2016.
 */
public class ClassSemanticMatcher
        extends FoldParseHandler {

    @Override
    public void parseMacro(NodeModel nodeModel, FoldedMacro macro) {
        PackageModel programModel = (PackageModel) nodeModel;
        List<String> attributes = FlatSemanticParser.getIdentifiers(macro._attributeTokens);
        String className = attributes.get(0);
        attributes.remove(0);
        List<TypeDescription> genericAttributes = attributes.stream()
                .map(arg->new TypeDescription(arg))
                .collect(Collectors.toList());
        ClassModel classModel = programModel.addClassModel(className, genericAttributes);
        try {
            FlatSemanticParser.parse(classModel, macro._childrenMacros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
