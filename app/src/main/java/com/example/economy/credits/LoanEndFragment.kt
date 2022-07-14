package com.example.economy.credits

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.economy.MainFragment
import com.example.economy.R
import com.example.economy.databinding.FragmentLoanEndBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class LoanEndFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView : MapView
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
        val rootView = inflater.inflate(R.layout.fragment_loan_end,container,false)
//        getbranchList()
        mapView = rootView.findViewById(R.id.mapFragment) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        rootView.findViewById<View>(R.id.btnHome2).setOnClickListener {
            changeFragment(MainFragment())
        }


        return rootView
    }

    override fun onMapReady(map: GoogleMap) {

        val point = LatLng(35.1559998 ,129.059584)
        val point1 = LatLng(35.1582178,129.058236) //부전동
        var point2 = LatLng(35.154547,129.0572446)//서면
        var point3 = LatLng(35.1543315,129.0657916)//전포

        val marker = MarkerOptions()
            .position(point)
            .title("부산it")
            .snippet("현재위치")

        //마커추가
        map.addMarker(MarkerOptions().position(point1).title("부산은행 부전동지점")
            .snippet("주소:부산광역시 부산진구 새싹로1\n번호:051-803-0851"))
        map.addMarker(MarkerOptions().position(point2).title("부산은행 서면지점")
            .snippet("주소:부산광역시 부산진구 동천로 116\n번호:051-811-1120"))
        map.addMarker(MarkerOptions().position(point3).title("부산은행 전포역지점")
            .snippet("주소: 부산광역시 부산진구 전포동 전포대로 200 \n 번호: 051-802-1236"))
        map.addMarker(marker)

        //카메라의 위치
        val cameraOption = CameraPosition.Builder()
            .target(point)
            .zoom(15f)
            .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        map.moveCamera(camera)

        // Set a listener for marker click.
        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val info = LinearLayout(context)
                info.orientation = LinearLayout.VERTICAL
                val title = TextView(context)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title
                val snippet = TextView(context)
                snippet.setTextColor(Color.GRAY)
                snippet.gravity = Gravity.LEFT
                snippet.text = marker.snippet
                info.addView(title)
                info.addView(snippet)
                return info
            }
        })
    }
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onLowMemory()
    }

}