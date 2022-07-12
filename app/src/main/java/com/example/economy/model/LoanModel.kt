package com.example.economy.model

import java.io.Serializable

data class LoanModel(
    var id:Long,
    var bank:String,
    var loanName:String,
    var minPeriod:Int,
    var maxPeriod:Int,
    var minRate:Float,
    var lessCredit:Int,
    var overCredit:Int,
    var maxMoney:Int
):Serializable
