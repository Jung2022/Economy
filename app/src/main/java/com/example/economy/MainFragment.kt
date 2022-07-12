package com.example.economy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.economy.consumption.ConsumSelectFragment
import com.example.economy.credits.CreditSelectFragment
import com.example.economy.databinding.FragmentMainBinding
import com.example.economy.model.MessageModel
import com.example.economy.spring.SpringApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    private fun changeFragment(frag: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrame, frag)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_consum_map,null)
        val binding=FragmentMainBinding.inflate(layoutInflater)


        binding.consumption.setOnClickListener {
            changeFragment(ConsumSelectFragment())
        }

        binding.credit.setOnClickListener {
            changeFragment(CreditSelectFragment())
        }


        return binding.root
    }
}