package ir.divar.interviewtask.placeslist

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dagger.hilt.android.AndroidEntryPoint
import ir.divar.domain.place.model.Place
import ir.divar.interviewtask.R
import ir.divar.interviewtask.databinding.FragmentPlacesListBinding
import ir.divar.interviewtask.util.UserLocationSharedPreferences


/**
 * Represents the Fragment that contains list of nearby places.
 *
 * @author Sepi 6/4/22
 */
@AndroidEntryPoint
class PlacesListFragment : Fragment(), PlaceAdapter.OnItemClickListener {

    // region of properties
    private val placesListViewModel by viewModels<PlacesListViewModel>()
    private var _binding: FragmentPlacesListBinding? = null
    private val binding get() = _binding!!

    private val userLocationSharedPreferences
            by lazy { UserLocationSharedPreferences.initialWith(requireContext()) }
    private var currentLocation: LatLng? = null
    private lateinit var locationRequest: LocationRequest
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            currentLocation = locationResult.lastLocation.run { LatLng(latitude, longitude) }
            Toast.makeText(requireContext(), "$currentLocation", Toast.LENGTH_LONG).show()

            if (isNetworkConnected()) checkIfApiCallIsRequired()
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        // TODO: handle if fine location permission is not granted
        if (it.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false))
            requestLocation()
    }

    // END of region of properties

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isLocationGranted()) requestLocationAccess()

        locationRequest = LocationRequest.create().apply {
            interval = 5000L
            fastestInterval = 5000L
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun requestLocationAccess() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        if (isLocationGranted()) {
            // getting user location
            LocationServices.getFusedLocationProviderClient(requireContext())
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    private fun isLocationGranted() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    /**
     * Checks the distance between current location of the user and the last location of the API call
     *  to decide if new API call is required or not.
     */
    private fun checkIfApiCallIsRequired() {
        userLocationSharedPreferences.getLastLocation()?.also {
            val distance = SphericalUtil.computeDistanceBetween(
                currentLocation,
                userLocationSharedPreferences.getLastLocation()
            )

            // TODO: handle showing places from API call or database
            if (distance > 100) {
                currentLocation?.let(userLocationSharedPreferences::saveLocation)
            }
        }
    }

    override fun onItemClickListener(place: Place) {
        currentLocation?.also {
            findNavController().navigate(
                PlacesListFragmentDirections
                    .actionPlacesListFragmentToPlaceDetailsFragment(place, it)
            )
        } ?: apply {
            Toast.makeText(requireContext(), R.string.no_location, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}