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
                comment="최우량등급입니다. 오랜 신용거래 경력과 다양하고 우량한 신용거래 실적을 보유하고 있어 부실화 가능성이 매우 낮으므로 좋은 조건으로 대출받을 수 있습니다."
            }
            credit>=805 -> {
                comment="우량등급입니다. 활발한 신용거래 실적은 없으나 꾸준하게 우량 거래를 지속한다면 상위등급 진입 가능하며 부실화 가능성은 낮은 수준이라 수월하게 대출을 받을 수 있습니다."
            }
            credit>=665 -> {
                comment="보통등급입니다. 비교적 금리가 높은 금융업권과의 거래가 있는 고객으로 단기연체 경험이 있으며 부실화 가능성은 일반적인 수준입니다"
            }
            credit>=515 -> {
                comment="주의등급입니다. 비교적 금리가 높은 금융업권과의 거래가 많은 고객으로 단기연체 경험을 비교적 많이 보유하고 있어 부실화 가능성이 높으므로 대출받을 때 유의하시길 바랍니다."
            }
            else -> {
                comment="위험등급입니다. 현재 연체 중이거나 매우 심각한 연체 경험을 보유하고 있어 부실화 가능성이 매우 높으므로 대출을 받기가 까다롭습니다."
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