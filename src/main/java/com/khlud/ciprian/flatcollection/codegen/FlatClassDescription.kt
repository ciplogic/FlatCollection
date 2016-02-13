package com.khlud.ciprian.flatcollection.codegen

/**
 * Created by ciprian on 2/10/16.
 */
data class FlatClassDescription(var simpleName: String,
                                val fieldNames: Array<String>,
                                val fieldTypeName: String);