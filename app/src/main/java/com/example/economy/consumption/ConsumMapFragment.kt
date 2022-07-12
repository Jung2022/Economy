package com.example.economy.consumption

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.economy.R
import com.example.economy.databinding.FragmentConsumMapBinding
import com.example.economy.model.ConsumePatternListModel
import com.example.economy.spring.SpringApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ConsumMapFragment : Fragment() {

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
        var binding=FragmentConsumMapBinding.inflate(layoutInflater)
        var gender=requireArguments().getString("gender")
        var age=requireArguments().getInt("age")
        var genderCode=requireArguments().getString("genderCode")
        var prefix=getString(R.string.ip_address)
        var query="resource/consume/chart/"
        var select=genderCode+age
        var suffix=".png"
        var url=prefix+query+select+suffix

        Glide.with(requireActivity().applicationContext).load(url)
            .placeholder(R.drawable.base)
            .error(R.drawable.bnk)
            .into(binding.imgConsumChart)

        var preAge=age+10
        binding.tvCRF2.text="10년 후 "+preAge+"대 소비비중현황"

        var select2=genderCode+preAge
        var url2=prefix+query+select2+suffix
        Glide.with(requireActivity().applicationContext).load(url2)
            .placeholder(R.drawable.base)
            .error(R.drawable.bnk)
            .into(binding.imgConsumChart2)


        var money=0
        var product=""

        val textWatcherMoney=object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                money = if(p0==null || p0.toString()==""){
                    0
                }else{
                    p0.toString().toInt()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }

        val networkService=(requireActivity().applicationContext as SpringApplication).networkService
        val patternListCall= networkService.doGetPattern(gender!!,preAge)
        patternListCall.enqueue(object : Callback<ConsumePatternListModel>{
            override fun onResponse(
                call: Call<ConsumePatternListModel>,
                response: Response<ConsumePatternListModel>
            ) {
                var productList=ArrayList<String>()
                var percentList=ArrayList<Int>()
                var spinnerList=ArrayList<String>()
                var patterns=response.body()!!.patterns
                for(pattern in patterns){
                    productList.add(pattern.product)
                    percentList.add(pattern.percent)
                    spinnerList.add(pattern.product+"("+pattern.percent+"%)")
                }
                val adapter= ArrayAdapter<String>(requireActivity().applicationContext,android.R.layout.simple_list_item_1,spinnerList)
                binding.chartSpinner.adapter=adapter
                binding.chartSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        product=productList[p2]
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
            }
            override fun onFailure(call: Call<ConsumePatternListModel>, t: Throwable) {
                call.cancel()
                Log.d("jjk","실패")
            }
        })

        binding.eTMoney.addTextChangedListener(textWatcherMoney)

        binding.tvCRF.text=gender+", "+age+"대 소비비중현황"
        binding.btnCPF.setOnClickListener {
            if(money==0){
                Toast.makeText(requireActivity().applicationContext,"목표 금액을 입력하세요.",Toast.LENGTH_SHORT).show()
            }else{
                val bundle=Bundle()
                bundle.putString("product",product)
                bundle.putInt("money",money)
                bundle.putString("gender",gender)
                bundle.putInt("age",age)
                bundle.putString("genderCode",genderCode)
                dataSendFragment(DepositListFragment(), bundle)
            }
        }

        return binding.root
    }

}