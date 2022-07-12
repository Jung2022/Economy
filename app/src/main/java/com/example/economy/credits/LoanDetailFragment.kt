package com.example.economy.credits

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.economy.R
import com.example.economy.consumption.DepositEndFragment
import com.example.economy.databinding.FragmentLoanDetailBinding
import com.example.economy.model.DepositModel
import com.example.economy.model.LoanModel
import java.text.NumberFormat
import java.util.*

class LoanDetailFragment : Fragment() {

    private fun changeFragment(frag: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrame, frag)
            .commit()
    }

    private fun monthPayment(month: Int, money: Long, interest: Float): Int {
        var month_payment = 0
        var rate = interest / 100
        var month_rate = rate / 12

        month_payment =
            ((money * month_rate * Math.pow(((1 + month_rate).toDouble()), month.toDouble())) /
                    (Math.pow(((1 + month_rate).toDouble()), month.toDouble()) - 1)).toInt()
        return month_payment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoanDetailBinding.inflate(layoutInflater)

        val loan=requireArguments().getSerializable("loan") as LoanModel
        val goalMoney=requireArguments().getLong("money")
        binding.tvGoalMoney2.text=NumberFormat.getCurrencyInstance(Locale.getDefault()).format(goalMoney)

        binding.LoanDetailName.text=loan.loanName
        binding.LoanBank.text=loan.bank
        binding.tvLoanRate.text=loan.minRate.toString()+"% ~"
        binding.tvLoanMoney.text=NumberFormat.getCurrencyInstance(Locale.getDefault()).format(loan.maxMoney)
        if (loan.minPeriod==0){
            binding.tvLoanPeriod.text="~"+loan.maxPeriod+"년"
        } else{
            binding.tvLoanPeriod.text=loan.minPeriod.toString()+"~"+loan.maxPeriod+"년"
        }
        if(loan.lessCredit==0){
            binding.tvLoanCondition.text="신용등급 "+loan.overCredit+" 이상"
        }else{
            binding.tvLoanCondition.text="신용등급 "+loan.lessCredit+" 이하"
        }

        var year = 0
        var month = year*12
        var money:Long = 0
        var interest = 0.0f

        val textWatcherYear = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 == null || p0.toString() == "" || p0.toString()==null) {
                    year=0
                    binding.tvLoanCalResult.text="₩0"
                } else {
                    year=p0.toString().toInt()
                    month=year*12
                    binding.tvLoanCalResult.text =
                        NumberFormat.getCurrencyInstance(Locale.getDefault())
                            .format(monthPayment(month, money, interest)).toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        val textWatcherMoney = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 == null || p0.toString() == ""|| p0.toString()==null) {
                    money=0
                    binding.tvLoanCalResult.text="₩0"
                } else {
                    money=p0.toString().toLong()
                    binding.tvLoanCalResult.text =
                        NumberFormat.getCurrencyInstance(Locale.getDefault())
                            .format(monthPayment(month, money, interest)).toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        val textWatcherInterest = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 == null || p0.toString() == ""|| p0.toString()==null) {
                    interest=0.0f
                    binding.tvLoanCalResult.text="₩0"
                } else {
                    interest=p0.toString().toFloat()
                    binding.tvLoanCalResult.text =
                        NumberFormat.getCurrencyInstance(Locale.getDefault())
                            .format(monthPayment(month, money, interest)).toString()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.editYear.addTextChangedListener(textWatcherYear)
        binding.editMoney.addTextChangedListener(textWatcherMoney)
        binding.editInterest.addTextChangedListener(textWatcherInterest)


        binding.btnLEF.setOnClickListener {
            changeFragment(LoanEndFragment())
        }
        return binding.root
    }

}