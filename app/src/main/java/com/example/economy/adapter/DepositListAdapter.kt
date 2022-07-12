package com.example.economy.adapter

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.economy.MainActivity
import com.example.economy.R
import com.example.economy.consumption.DepositDetailFragment
import com.example.economy.databinding.DepositBinding
import com.example.economy.model.DepositModel

class DepositListViewHolder(val binding:DepositBinding):RecyclerView.ViewHolder(binding.root)

class DepositListAdapter(val context: Context, private val deposits:List<DepositModel>, val money:Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private fun changeFragment(frag: Fragment){
        (context as MainActivity).supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrame, frag)
            .commit()
    }
    private fun dataSendFragment(frag: Fragment, bundle: Bundle){
        frag.arguments=bundle
        changeFragment(frag)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DepositListViewHolder(
            DepositBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as DepositListViewHolder).binding
        binding.depositName.text=deposits[position].depositName
        var str=""
        if (deposits[position].minRate==0f){
            str="최저 -, 최고 "+deposits[position].maxRate
        }else{
            str="최저 "+deposits[position].minRate+", 최고 "+deposits[position].maxRate
        }
        binding.depositRate.text=str
        if(deposits[position].bank=="BNK") {
            binding.imgLogo.setImageResource(R.drawable.bnk)
        }
        binding.imgLogo.background=ShapeDrawable(OvalShape())
        binding.imgLogo.clipToOutline=true

        binding.depositDetail.setOnClickListener {
            val bundle=Bundle()
            bundle.putSerializable("deposit",deposits[position])
            bundle.putInt("money",money)
            dataSendFragment(DepositDetailFragment(), bundle)
        }
    }

    override fun getItemCount(): Int {
        return deposits.size
    }
}