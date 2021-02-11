package com.scb.googleplace.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.scb.googleplace.R
import com.scb.googleplace.Utils.Dist
import com.scb.googleplace.Utils.formatDistance
import com.scb.googleplace.adapter.AdapterRcPlaces
import com.scb.placemaps.models.Place


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationPermissionGranted = false
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private lateinit var  _adapter_places : AdapterRcPlaces
    lateinit var recview: RecyclerView
    lateinit var progressBar: ProgressBar
    var valueLocalUrlCurrent = "";
    var valuePlaceSelectCurrent = "bar,cafe,restaurant";
    var valueDistCurrent = "10000";


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val root: View = inflater.inflate(R.layout.main_fragment, container, false)

        initUi(root)


        getLastKnownLocation()

        return root
    }

    fun initUi(view: View){

        recview = view.findViewById(R.id.rc_places) as RecyclerView
        progressBar = view.findViewById(R.id.progressBar) as ProgressBar

        val spinner: Spinner = view.findViewById(R.id.places_spinner) as Spinner
        ArrayAdapter.createFromResource(
            context!!,
            R.array.list_spinner,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val spinnerdist: Spinner = view.findViewById(R.id.dist_spinner) as Spinner
        ArrayAdapter.createFromResource(
            context!!,
            R.array.dist_spinner,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerdist.adapter = adapter
        }
        spinnerdist.setSelection(2)

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                    valuePlaceSelectCurrent = "bar,cafe,restaurant"
                else {
                    val array = resources.getStringArray(R.array.list_spinner)
                    valuePlaceSelectCurrent = array.get(position).toLowerCase()
                }
                getPlaces(valueLocalUrlCurrent, valueDistCurrent, valuePlaceSelectCurrent)

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        spinnerdist.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                valueDistCurrent= formatDistance().getDist(position)
                getPlaces (valueLocalUrlCurrent,valueDistCurrent,valuePlaceSelectCurrent)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        startObserver();

    }

    fun startObserver(){

        val PlacesObserver = Observer<Place> { places ->
            setListPlaces(places);
        }

        viewModel.response.observe(this, PlacesObserver)
    }

    fun setListPlaces(list : Place){

        _adapter_places = AdapterRcPlaces(list.results!!,context!!)
        recview = view!!.findViewById(R.id.rc_places)
        recview.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        recview.setLayoutManager(LinearLayoutManager(context))
        recview.setAdapter(_adapter_places)
        progressBar.visibility=View.INVISIBLE;
    }

    fun getLastKnownLocation() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission()
            return
        }

        fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
                        var  localurl :String = location.latitude.toString()+","+location.longitude.toString();
                        valueLocalUrlCurrent = localurl;
                        getPlaces (valueLocalUrlCurrent,valueDistCurrent,valuePlaceSelectCurrent)
                    }
                }
    }

    fun getPlaces(location : String, dist : String, type : String){
        if (location!="") {
            progressBar.visibility=View.VISIBLE;
            viewModel.getPlaces(location, dist, type);
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION )
            getLastKnownLocation()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {

        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}