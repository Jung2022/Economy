package com.example.economy.consumption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.economy.MainFragment
import com.example.economy.R
import com.example.economy.databinding.FragmentDepositEndBinding


class DepositEndFragment : Fragment() {

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
        val binding=FragmentDepositEndBinding.inflate(layoutInflater)

        binding.btnHome1.setOnClickListener {
            changeFragment(MainFragment())
        }

        return binding.root
    }

}