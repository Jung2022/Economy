package com.example.economy.consumption

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.economy.R
import com.example.economy.adapter.DepositListAdapter
import com.example.economy.databinding.FragmentDepositListBinding
import com.example.economy.model.DepositListModel
import com.example.economy.spring.SpringApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class DepositListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentDepositListBinding.inflate(layoutInflater)

        val product=requireArguments().getString("product")
        val money=requireArguments().getInt("money")
        val gender=requireArguments().getString("gender")
        val age=requireArguments().getInt("age")

        binding.tvDepositCondition.text=gender+" "+age+"대"
        binding.tvDepositProduct.text=product
        binding.tvDLMoney.text=NumberFormat.getCurrencyInstance(Locale.getDefault()).format(money)

        val networkService=(requireActivity().applicationContext as SpringApplication).networkService
        val depositListCall=networkService.doGetDepositList()

        depositListCall.enqueue(object :Callback<DepositListModel>{
            override fun onResponse(
                call: Call<DepositListModel>,
                response: Response<DepositListModel>
            ) {
                if(response.isSuccessful){
                    val layoutManager=LinearLayoutManager(this@DepositListFragment.context, LinearLayoutManager.VERTICAL,false)
                    binding.depositRecycler.layoutManager=layoutManager
                    binding.depositRecycler.adapter=
                        this@DepositListFragment.context?.let { DepositListAdapter(it,response.body()!!.deposits, money) }
                }
            }

            override fun onFailure(call: Call<DepositListModel>, t: Throwable) {
                call.cancel()
            }
        })



        return binding.root
    }

}