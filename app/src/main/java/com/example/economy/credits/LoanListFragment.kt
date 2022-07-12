package com.example.economy.credits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.economy.R
import com.example.economy.adapter.DepositListAdapter
import com.example.economy.adapter.LoanListAdapter
import com.example.economy.databinding.FragmentDepositListBinding
import com.example.economy.databinding.FragmentLoanListBinding
import com.example.economy.model.LoanListModel
import com.example.economy.spring.SpringApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class LoanListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentLoanListBinding.inflate(layoutInflater)

//        val region=requireArguments().getString("region")
//        val age=requireArguments().getString("age")
        val money=requireArguments().getLong("money")
        val credit=requireArguments().getInt("credit")
        var comment=""
        var str="신용 점수 "+credit+"점은 "
        when {
            credit>=870 -> {
                comment="최우량등급"
            }
            credit>=805 -> {
                comment="우량등급"
            }
            credit>=665 -> {
                comment="보통등급"
            }
            credit>=515 -> {
                comment="주의등급"
            }
            else -> {
                comment="위험등급"
            }
        }
        str+=comment

        binding.tvLoanListMoney.text="대출 금액 : "+ NumberFormat.getCurrencyInstance(Locale.getDefault()).format(money)
        binding.tvLoanListCredit.text=str

        val networkService=(requireActivity().applicationContext as SpringApplication).networkService
        val loanListCall=networkService.doGetLoanFindList(credit, money)
        loanListCall.enqueue(object :Callback<LoanListModel>{
            override fun onResponse(call: Call<LoanListModel>, response: Response<LoanListModel>) {
                val layoutManager=
                    LinearLayoutManager(this@LoanListFragment.context, LinearLayoutManager.VERTICAL,false)
                binding.loanRecycler.layoutManager=layoutManager
                binding.loanRecycler.adapter=
                    this@LoanListFragment.context?.let { LoanListAdapter(it,response.body()!!.loans, money) }
            }

            override fun onFailure(call: Call<LoanListModel>, t: Throwable) {
                call.cancel()
            }
        })


        return binding.root
    }
}