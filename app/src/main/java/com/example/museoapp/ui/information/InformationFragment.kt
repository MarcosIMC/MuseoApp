package com.example.museoapp.ui.information

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.museoapp.R
import com.example.museoapp.databinding.FragmentInformationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class InformationFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private var _binding: FragmentInformationBinding? = null
    lateinit var map: GoogleMap
    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    enum class RRSS (val rrss:String){
        TWITTER("Twitter"),
        FACEBOOK("Facebook"),
        INSTRAGRAM("Instagram"),
        PINTEREST("Pinterest")
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val informationViewModel =
            ViewModelProvider(this).get(InformationViewModel::class.java)

        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Control de los botones RRSS en el VM
        binding.imageButtonFacebook.setOnClickListener { informationViewModel.openRRSS(RRSS.FACEBOOK) }
        binding.imageButtonInstagram.setOnClickListener { informationViewModel.openRRSS(RRSS.INSTRAGRAM) }
        binding.imageButtonPinterest.setOnClickListener { informationViewModel.openRRSS(RRSS.PINTEREST) }
        binding.imageButtonTwitter.setOnClickListener { informationViewModel.openRRSS(RRSS.TWITTER) }

        //Solicitamos los permisos de la ubicación
        isPermissionsGranted()

        //Creación del MapView
        createMapFragment()

        return root
    }

    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        activity!!,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(activity, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    private fun createMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap){
        map = googleMap
        createMarker()
        map.setOnMyLocationButtonClickListener(this)
        enableMyLocation()
    }

    private fun createMarker(){
        val museumUbication = LatLng(28.14133868080328, -15.429726122261174)
        map.addMarker(MarkerOptions().position(museumUbication).title("Museum"))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(museumUbication, 18f), 4000, null
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            }else {
                Toast.makeText(activity, "Para poder visualizar el mapa, debes aceptar los permisos.", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isPermissionsGranted()){
            if (!::map.isInitialized) return
            map.isMyLocationEnabled = false
            Toast.makeText(activity, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }
}