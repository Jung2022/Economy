package com.example.economy.consumption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.example.economy.MainActivity
import com.example.economy.R
import com.example.economy.databinding.FragmentDepositDetailBinding
import com.example.economy.model.DepositModel
import java.text.NumberFormat
import java.util.*

class DepositDetailFragment : Fragment() {

    private fun changeFragment(frag: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrame, frag)
            .commit()
    }

    private fun simpleInterest(month:Int, money:Int, interest:Float): Float {
        var sum=0.0f
        var rate=interest/100
        for (i in 0 until (month)){
            sum += money*rate/12*(i+1)
        }
        sum+=money*month
        return sum
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        리스트에서 bundle로 넘겨주면 굳이 DB안들려도 되지않을까?
//        리스트 부를 때 너무 오래 걸릴려나? 자료가 얼마 안되서 모르겠는데 천개쯤되면 흠,,,
        val binding=FragmentDepositDetailBinding.inflate(layoutInflater)
        val deposit=requireArguments().getSerializable("deposit") as DepositModel

        val goalMoney=requireArguments().getInt("money")
        binding.tvGoalMoney.text="목표 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(goalMoney)


        val minMoney=1000
        var money=minMoney
        val minRate=0.00f
        var rate=minRate
        var period=6
        var sum=0
        var depositPeriod = if (deposit.maxPeriod==deposit.minPeriod){
            deposit.maxPeriod.toString()+"개월"
        }else{
            deposit.maxPeriod.toString()+"~"+deposit.minPeriod.toString()+"개월"
        }

        binding.DepositDetailName.text=deposit.depositName
        if (deposit.bank=="BNK"){
            binding.DepositBank.text="부산은행"
        }
        binding.tvDepositMaxRate.text=deposit.maxRate.toString()+" %"
        binding.tvDepositMinRate.text=deposit.minRate.toString()+" %"
        binding.tvDepositPeriod.text=depositPeriod

        sum=simpleInterest(period,money,rate).toInt()
        binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)

        val sData=resources.getStringArray(R.array.deposit_period_array)
        val adapter=ArrayAdapter<String>(requireActivity().applicationContext,android.R.layout.simple_list_item_1,sData)
        binding.depositPeriodSpinner.adapter=adapter
        binding.depositPeriodSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //p2가 index, position인듯
                when(p2){
                    0 -> {
                        period=6
                        sum=simpleInterest(period,money,rate).toInt()
                        binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)}
                    1 -> {
                        period=12
                        sum=simpleInterest(period,money,rate).toInt()
                        binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)}
                    2 -> {
                        period=24
                        sum=simpleInterest(period,money,rate).toInt()
                        binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)}
                    3 -> {
                        period=36
                        sum=simpleInterest(period,money,rate).toInt()
                        binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)}
                    4 -> {
                        period=60
                        sum=simpleInterest(period,money,rate).toInt()
                        binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)}

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.seekBarRate.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                // p1이 progress 값
                rate= (minRate+0.05*p1).toFloat()
                binding.tvCalRate.text=rate.toString()+" %"
                sum=simpleInterest(period,money,rate).toInt()
                binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }
            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.seekBarMoney.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //p1이 progress
                money=5000*p1
                if(p1==0){
                    money=minMoney
                }
                binding.tvCalMoney.text=money.toString()+" 원"
                sum=simpleInterest(period,money,rate).toInt()
                binding.tvCalResult.text="적금 금액 : "+NumberFormat.getCurrencyInstance(Locale.getDefault()).format(sum)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }
            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.btnDEF.setOnClickListener {
            changeFragment(DepositEndFragment())
        }

        return binding.root
    }

}