package com.khlud.ciprian.flatcollection.compiler.parser.parseMatchers;

import com.khlud.ciprian.flatcollection.compiler.codeModel.NodeModel;
import com.khlud.ciprian.flatcollection.compiler.preParser.FoldedMacro;

/**
 * Created by Ciprian on 2/29/2016.
 */
public interface IFoldParseHandler {
    void parseMacro(NodeModel nodeModel, FoldedMacro macro);
}
