package com.khlud.ciprian.flatcollection.compiler.codegen;

import com.khlud.ciprian.flatcollection.compiler.codeModel.ProgramModel;
import com.khlud.ciprian.flatcollection.compiler.codeModel.SpecializeModel;
import com.khlud.ciprian.flatcollection.utils.OsUtils;

/**
 * Created by Ciprian on 3/4/2016.
 */
public class ReifiedCodeGen {
    public void generateCode(String path, ProgramModel programModel){
        OsUtils.createPath(path);

        specializeProgram(programModel);
    }

    private void specializeProgram(ProgramModel programModel) {
        for(SpecializeModel specializeModel:programModel.Specializations){
            specialize(specializeModel);
        }
    }

    private void specialize(SpecializeModel specializeModel) {

    }

}
