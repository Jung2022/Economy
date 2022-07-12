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
import com.example.economy.credits.LoanDetailFragment
import com.example.economy.databinding.LoanItemsBinding
import com.example.economy.model.LoanModel

class LoanListViewHolder(val binding: LoanItemsBinding): RecyclerView.ViewHolder(binding.root)

class LoanListAdapter(val context: Context, private val loans:List<LoanModel>, val money:Long):
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
        LoanListViewHolder(
            LoanItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as LoanListViewHolder).binding
        binding.loanName.text=loans[position].loanName
        binding.loanRate.text="금리 : "+loans[position].minRate+" ~"
        if(loans[position].bank=="BNK"){
            binding.imgLogoLoan.setImageResource(R.drawable.bnk)
        }
        binding.imgLogoLoan.background= ShapeDrawable(OvalShape())
        binding.imgLogoLoan.clipToOutline=true

        binding.loanDetail.setOnClickListener {
            val bundle=Bundle()
            bundle.putSerializable("loan",loans[position])
            bundle.putLong("money",money)
            dataSendFragment(LoanDetailFragment(), bundle)
        }
    }

    override fun getItemCount(): Int {
        return loans.size
    }
}