package com.example.economy.spring

import com.example.economy.model.*
import retrofit2.Call
import retrofit2.http.*

interface INetworkService {
    @GET("test/test")
    fun doTest(): Call<MessageModel>

    @GET("img/chart")
    fun doGetChart():Call<MessageModel>

    @GET("deposit/all")
    fun doGetDepositList():Call<DepositListModel>

    @GET("loan/all")
    fun doGetLoanList():Call<LoanListModel>

    @GET("loan/find")
    fun doGetLoanFindList(@Query("credit")credit:Int,
                            @Query("money")money:Long):Call<LoanListModel>

    @GET("credit/find")
    fun doGetCredit(@Query("age")age:Int,
                    @Query("region")region:String):Call<CreditModel>

    @GET("credit/pattern")
    fun doGetPattern(@Query("gender")gender:String,
                    @Query("age")age:Int):Call<ConsumePatternListModel>
}