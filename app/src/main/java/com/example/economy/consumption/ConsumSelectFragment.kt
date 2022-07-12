package com.example.economy.consumption

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.economy.R
import com.example.economy.databinding.FragmentConsumSelectBinding


class ConsumSelectFragment : Fragment() {

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
        val binding=FragmentConsumSelectBinding.inflate(layoutInflater)


        var age=0
        var gender=""
        var genderCode=""

        binding.man.setOnClickListener {
            binding.manCheck.visibility=View.VISIBLE
            binding.womanCheck.visibility=View.INVISIBLE
            gender="남성"
            genderCode="M"
        }
        binding.woman.setOnClickListener {
            binding.manCheck.visibility=View.INVISIBLE
            binding.womanCheck.visibility=View.VISIBLE
            gender="여성"
            genderCode="W"
        }

        binding.btnAge20.setOnClickListener {
            age=binding.btnAge20.text.toString().toInt()
            binding.btnAge30.isEnabled=false
            binding.btnAge40.isEnabled=false
            binding.btnAge50.isEnabled=false
            binding.btnAge60.isEnabled=false
        }
        binding.btnAge30.setOnClickListener {
            age=binding.btnAge30.text.toString().toInt()
            binding.btnAge20.isEnabled=false
            binding.btnAge40.isEnabled=false
            binding.btnAge50.isEnabled=false
            binding.btnAge60.isEnabled=false
        }
        binding.btnAge40.setOnClickListener {
            age=binding.btnAge40.text.toString().toInt()
            binding.btnAge30.isEnabled=false
            binding.btnAge20.isEnabled=false
            binding.btnAge50.isEnabled=false
            binding.btnAge60.isEnabled=false
        }
        binding.btnAge50.setOnClickListener {
            age=binding.btnAge50.text.toString().toInt()
            binding.btnAge30.isEnabled=false
            binding.btnAge40.isEnabled=false
            binding.btnAge20.isEnabled=false
            binding.btnAge60.isEnabled=false
        }
        binding.btnAge60.setOnClickListener {
            age=binding.btnAge60.text.toString().toInt()
            binding.btnAge30.isEnabled=false
            binding.btnAge40.isEnabled=false
            binding.btnAge50.isEnabled=false
            binding.btnAge20.isEnabled=false
        }

        binding.btnNext.setOnClickListener {
            if (!gender.equals("") && age != 0) {
                val bundle=Bundle()
                bundle.putString("gender",gender)
                bundle.putInt("age",age)
                bundle.putString("genderCode",genderCode)
                dataSendFragment(ConsumMapFragment(), bundle)
            } else {
                Toast.makeText(
                    requireActivity().getApplicationContext(),
                    "성별, 연령을 선택하세요.",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }

        return binding.root
    }

}