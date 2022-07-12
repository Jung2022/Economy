package com.example.economy.credits

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.economy.R
import com.example.economy.databinding.FragmentCreditMapBinding
import com.example.economy.model.CreditModel
import com.example.economy.spring.SpringApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditMapFragment : Fragment() {

    private fun changeFragment(frag: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrame, frag)
            .commit()
    }
    private fun dataSendFragment(frag:Fragment, bundle: Bundle){
        frag.arguments=bundle
        changeFragment(frag)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentCreditMapBinding.inflate(layoutInflater)
        var region=requireArguments().getString("region")
        var age=requireArguments().getInt("age")
        var money:Long=0
        var credit:Int=0

        var prefix=getString(R.string.ip_address)
        var query="resource/credit/map/"
        var query2="resource/credit/graph/"
        var select=age.toString()+"s"
        var suffix=".png"
        var url=prefix+query+select+suffix
        var url2=prefix+query2+select+suffix

        binding.tvCreditMap.text=age.toString()+"대 신용점수"

        Glide.with(requireActivity().applicationContext).load(url)
            .placeholder(R.drawable.base)
            .error(R.drawable.bnk)
            .into(binding.imgCreditMap)
        Glide.with(requireActivity().applicationContext).load(url2)
            .placeholder(R.drawable.base)
            .error(R.drawable.bnk)
            .into(binding.imgCreditPredictGraph)

        val networkService=(requireActivity().applicationContext as SpringApplication).networkService
        val creditFindCall=networkService.doGetCredit(age, region!!)
        creditFindCall.enqueue(object :Callback<CreditModel>{
            override fun onResponse(call: Call<CreditModel>, response: Response<CreditModel>) {
                var str=region+", "+age+"대 평균 신용점수 : "+response.body()!!.credit+"점"
                binding.tvCreditAvg.text = str
            }
            override fun onFailure(call: Call<CreditModel>, t: Throwable) {
                call.cancel()
            }
        })

        val textWatcherCredit=object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                credit = if (p0==null || p0.toString()==null || p0.toString()==""){
                    0
                }else{
                    p0.toString().toInt()
                }
            }
        }
        val textWatcherMoney=object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                money = if (p0==null || p0.toString()==null || p0.toString()==""){
                    0
                }else{
                    p0.toString().toLong()
                }
            }
        }
        binding.eTCredit.addTextChangedListener(textWatcherCredit)
        binding.eTLoan.addTextChangedListener(textWatcherMoney)


        binding.btnCreditPF.setOnClickListener {
            if(money==0L || credit==0){
                Toast.makeText( requireActivity().getApplicationContext(),
                    "신용점수, 대출 금액을 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                val bundle=Bundle()
                bundle.putLong("money",money)
                bundle.putInt("credit",credit)
                dataSendFragment(LoanListFragment(), bundle)
            }
        }

        return binding.root
    }

}