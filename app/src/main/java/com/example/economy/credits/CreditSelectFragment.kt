package com.example.economy.credits

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.economy.R
import com.example.economy.consumption.ConsumMapFragment
import com.example.economy.databinding.FragmentCreditSelectBinding

class CreditSelectFragment : Fragment() {

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
        val binding=FragmentCreditSelectBinding.inflate(layoutInflater)

        val items=arrayOf<String>("중구","서구","동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구",
            "금정구", "강서구", "연제구", "수영구", "사상구", "기장군")
        var region=""
        var age=0
        binding.selectRegionCredit.setOnClickListener {
            AlertDialog.Builder(activity).run {
                setTitle("주거 행정구를 선택하세요.")
                setSingleChoiceItems(items, 0,
                    DialogInterface.OnClickListener{ dialogInterface, i ->
                        region=items[i]
                    }
                )
                setPositiveButton("닫기", DialogInterface.OnClickListener { dialogInterface, i ->
                    binding.selectedRegionCredit.setText(region)
                })
                show()
            }
        }

        binding.btnAge20Credit.setOnClickListener {
            age=binding.btnAge20Credit.text.toString().toInt()
            binding.btnAge30Credit.isEnabled=false
            binding.btnAge40Credit.isEnabled=false
            binding.btnAge50Credit.isEnabled=false
            binding.btnAge60Credit.isEnabled=false
            binding.btnAge70Credit.isEnabled=false
        }
        binding.btnAge30Credit.setOnClickListener {
            age=binding.btnAge30Credit.text.toString().toInt()
            binding.btnAge20Credit.isEnabled=false
            binding.btnAge40Credit.isEnabled=false
            binding.btnAge50Credit.isEnabled=false
            binding.btnAge60Credit.isEnabled=false
            binding.btnAge70Credit.isEnabled=false
        }
        binding.btnAge40Credit.setOnClickListener {
            age=binding.btnAge40Credit.text.toString().toInt()
            binding.btnAge30Credit.isEnabled=false
            binding.btnAge20Credit.isEnabled=false
            binding.btnAge50Credit.isEnabled=false
            binding.btnAge60Credit.isEnabled=false
            binding.btnAge70Credit.isEnabled=false
        }
        binding.btnAge50Credit.setOnClickListener {
            age=binding.btnAge50Credit.text.toString().toInt()
            binding.btnAge30Credit.isEnabled=false
            binding.btnAge40Credit.isEnabled=false
            binding.btnAge20Credit.isEnabled=false
            binding.btnAge60Credit.isEnabled=false
            binding.btnAge70Credit.isEnabled=false
        }
        binding.btnAge60Credit.setOnClickListener {
            age=binding.btnAge60Credit.text.toString().toInt()
            binding.btnAge30Credit.isEnabled=false
            binding.btnAge40Credit.isEnabled=false
            binding.btnAge50Credit.isEnabled=false
            binding.btnAge20Credit.isEnabled=false
            binding.btnAge70Credit.isEnabled=false
        }
        binding.btnAge70Credit.setOnClickListener {
            age=binding.btnAge70Credit.text.toString().toInt()
            binding.btnAge30Credit.isEnabled=false
            binding.btnAge40Credit.isEnabled=false
            binding.btnAge50Credit.isEnabled=false
            binding.btnAge60Credit.isEnabled=false
            binding.btnAge20Credit.isEnabled=false
        }

        binding.btnCMF.setOnClickListener {
            if (!region.equals("") && age != 0) {
                val bundle=Bundle()
                bundle.putString("region",region)
                bundle.putInt("age",age)
                dataSendFragment(CreditMapFragment(), bundle)
            } else {
                Toast.makeText(
                    requireActivity().getApplicationContext(),
                    "행정구, 연령을 선택하세요.",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }

        return binding.root
    }
}