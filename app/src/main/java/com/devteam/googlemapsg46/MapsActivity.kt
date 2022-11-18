package com.devteam.googlemapsg46

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.devteam.googlemapsg46.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fuseLocation: FusedLocationProviderClient
    private var mimarcador:Marker?=null
    private var mimarcador2:Marker?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // activar la ubicacion actual
        fuseLocation = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private fun marker(ciudad: String){
        val ubicacion = LatLng(4.65,-74.083)
        val marker:MarkerOptions=MarkerOptions().position(ubicacion).title(ciudad)
        mMap.addMarker(marker)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,10f),5000,null)
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        // activacion
        if(ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1
            )
            return
        }
        mMap.isMyLocationEnabled=true
        fuseLocation.lastLocation.addOnSuccessListener{location->
            if(location !=null){
                val ubication=LatLng(location.latitude,location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubication,18f))
            }
        }
        /////////////////////////////////////////////
        val pos= LatLng(6.2,-75.3)
        mimarcador = googleMap.addMarker(MarkerOptions().position(pos)
            .title("marcador personalizado")
        )
        mMap.uiSettings.isZoomControlsEnabled=true
        val pos2= LatLng(6.9,-76.3)
        mimarcador2 = googleMap.addMarker(MarkerOptions().position(pos2)
            .title("marcador personalizado 2")
        )

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        val bogota = LatLng(4.65,-74.083)
        mMap.addMarker(MarkerOptions().position(bogota).title("Capital Bogota"))
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota))
        val ucaldas = LatLng(5.0556,-75.49)
        mMap.addMarker(MarkerOptions().position(ucaldas).title("Univ Caldas"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ucaldas))
        marker("Bogota")
        marcador()
        // /////////////////////////////////// click en el mapa ///////////////
      /*  mMap.setOnMapClickListener {
            val marker:MarkerOptions=MarkerOptions().position(it)   // extrae pos
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                mMap.addMarker(marker)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
                val lat:String = it.latitude.toString()
                val lon:String = it.longitude.toString()
                Toast.makeText(this, "Posicion" + lat + " " + lon,Toast.LENGTH_LONG).show()
        }
      */
        //////////////////////////////////////////////////////////////////////
        // ubicacion tiempo real, torres gsm, debe haber un permiso en el  manifest
    }

    private fun marcador(){
        val coord = LatLng( 5.067, -75.517)
        val marker:MarkerOptions=MarkerOptions()
            .position(coord)
            .title("Ciudad de manizales")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.orthodoxchurch))
        mMap.addMarker(marker)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coord,13f),5000,null)

    }

    override fun onMarkerClick(p0: Marker): Boolean {
         if(p0.equals(mimarcador)){
            val lat1:String
            val lon1:String
            lat1=p0.position.latitude.toString()
            lon1=p0.position.longitude.toString()
            Toast.makeText(this, "Posicion mark 1" + lat1 + " " + lon1,Toast.LENGTH_LONG).show()
         }
        if(p0.equals(mimarcador2)){
            val lat2:String
            val lon2:String
            lat2=p0.position.latitude.toString()
            lon2=p0.position.longitude.toString()
            Toast.makeText(this, "Posicion mark 2" + lat2 + " " + lon2,Toast.LENGTH_LONG).show()
        }

        return false
    }


}











