package com.example.economy.model

import java.io.Serializable

data class DepositModel(
    var id:Long,
    var bank:String,
    var depositName:String,
    var minPeriod:Int,
    var maxPeriod:Int,
    var monthPayment:String,
    var maxRate:Float,
    var minRate:Float,
    var target:String
):Serializable
