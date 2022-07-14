package com.example.economy.model

import java.io.Serializable

data class BranchModel(
    var id:Long,
    var bank:String,
    var branchName:String,
    var address:String,
    var latitute:Float,
    var longitude:Float,
    var Tel:String
): Serializable
