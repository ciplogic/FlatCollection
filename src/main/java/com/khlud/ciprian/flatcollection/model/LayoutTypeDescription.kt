package com.khlud.ciprian.flatcollection.model

import java.util.*

/**
 * Created by Ciprian on 2/11/2016.
 */
class LayoutTypeDescription(val sizeFields:Int ) {
    val fields : MutableList<LayoutFieldDescription> = ArrayList()

}

class LayoutFieldDescription {
    var refType: LayoutTypeDescription? = null
    var Name: String = ""
    var Type: String = ""
    var Size = 0
    var Offset = 0
}


