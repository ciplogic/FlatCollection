package com.khlud.ciprian.flatcollection.model

import java.util.*

/**
 * Created by Ciprian on 2/11/2016.
 */
class LayoutTypeDescription(val sizeFields:Int ) {
    val fields : MutableList<LayoutFieldDescription> = ArrayList()

}

data class Pair<TKey, TValue>(val key: TKey, val value: TValue);
