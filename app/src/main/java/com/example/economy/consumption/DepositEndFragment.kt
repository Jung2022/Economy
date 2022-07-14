package com.example.economy.consumption

import android.Manifest
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.economy.MainFragment
import com.example.economy.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.*


class DepositEndFragment:Fragment(),OnMapReadyCallback,GoogleMap.OnMarkerClickListener{
    //lateinit var binding:FragmentDepositEndBinding
    private lateinit var mapView : MapView
    //private lateinit var map:GoogleMap
    val REQUIRED_PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION ;Manifest.permission.ACCESS_COARSE_LOCATION
    }

    //뷰생성함수
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_deposit_end,container,false)
//        getbranchList()
        mapView = rootView.findViewById(R.id.mapFragment) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        rootView.findViewById<View>(R.id.btnHome1).setOnClickListener {
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
        map.setInfoWindowAdapter(object : InfoWindowAdapter {
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


//    private fun getbranchList(){
//        val networkService = (context?.applicationContext as SpringApplication).networkService
//        val branchListCall=networkService.doGetBranchList()
//        branchListCall.enqueue(object:Callback<BranchListModel>{
//
//            override fun onResponse(call: Call<BranchListModel>, response: Response<BranchListModel>
//            ) {
//                val branch = response.body()
//
//            }
//            override fun onFailure(call: Call<BranchListModel>, t: Throwable) {
//            }
//
//        })
//    }

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

    // 홈함수
    private fun changeFragment(frag: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mainFrame, frag)
            .commit()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Toast.makeText(
                context,
                "${marker.title} has been clicked $newClickCount times.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

}







