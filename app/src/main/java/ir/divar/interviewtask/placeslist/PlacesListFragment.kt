package ir.divar.interviewtask.placeslist

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import ir.divar.interviewtask.util.Constants
import ir.divar.interviewtask.util.DialogHelper
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

    private lateinit var placeAdapter: PlaceAdapter
    private val placeList = mutableListOf<Place>()

    // the next page link we use for implement pagination for places list
    private var nextLink: String? = null

    private var currentLocation: LatLng? = null
    private lateinit var locationRequest: LocationRequest
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            currentLocation = locationResult.lastLocation.run { LatLng(latitude, longitude) }
            Toast.makeText(requireContext(), "$currentLocation", Toast.LENGTH_SHORT).show()

            if (isNetworkConnected()) checkIfApiCallIsRequired()
            // obtain data from local Db when there is no Internet connection
            else placesListViewModel.getLocalPlaceList()
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false))
            requestLocation()
        else {
            userLocationSharedPreferences.setLocationPermissionDenied()
            showPermissionRequestDialog(
                shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    // END of region of properties

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationRequest = LocationRequest.create().apply {
            interval = 10000L
            fastestInterval = 10000L
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isLocationGranted()) {
            if (userLocationSharedPreferences.isLocationPermissionDeniedBefore())
                showPermissionRequestDialog(
                    shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            else requestLocationAccess()
        } else requestLocation()

        initialView()
        subscribeView()
    }

    private fun initialView() {
        binding.rvPlaceList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            placeAdapter = PlaceAdapter(placeList, this@PlacesListFragment)
            adapter = placeAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!canScrollVertically(1 /* scroll down */))
                    // request for the next page of places if available
                        nextLink?.also { placesListViewModel.getServerPlaceListByLink(it) }
                }
            })
        }
    }

    private fun subscribeView() {
        placesListViewModel.getLocalPlaceListResponse.observe(viewLifecycleOwner) {
            placeList.clear()
            placeList.addAll(it)
            placeAdapter.notifyDataSetChanged()

            displayNoPlacesTextView()
        }

        placesListViewModel.getLocalPlaceListError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), R.string.no_data, Toast.LENGTH_SHORT).show()

            placeList.clear()
            placeAdapter.notifyDataSetChanged()

            displayNoPlacesTextView()
        }

        placesListViewModel.getServerPlaceListResponse.observe(viewLifecycleOwner) {
            placeList.clear()
            placeList.addAll(it)
            placeAdapter.notifyDataSetChanged()

            displayNoPlacesTextView()

            placesListViewModel.clearPlaceList()
        }

        placesListViewModel.getServerPlaceListError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

            placesListViewModel.getLocalPlaceList()
        }

        placesListViewModel.getServerPlaceListNextUrlResponse.observe(viewLifecycleOwner) {
            nextLink = it
        }

        placesListViewModel.getServerPlaceListByLinkResponse.observe(viewLifecycleOwner) {
            val oldSize = placeList.size
            placeList.addAll(it)
            placeAdapter.notifyItemRangeInserted(oldSize, placeList.size)

            placesListViewModel.insertPlaceList(it)
        }

        placesListViewModel.getServerPlaceListByLinkError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        placesListViewModel.insertPlaceListResponse.observe(viewLifecycleOwner) {

        }

        placesListViewModel.insertPlaceListError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), R.string.problem_occurred, Toast.LENGTH_SHORT).show()
        }

        placesListViewModel.clearPlaceListResponse.observe(viewLifecycleOwner) {
            placesListViewModel.insertPlaceList(placeList)
        }

        placesListViewModel.clearPlaceListError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), R.string.problem_occurred, Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayNoPlacesTextView() {
        binding.tvNoPlaces.visibility = if (placeList.isNotEmpty()) View.GONE else View.VISIBLE
    }

    private fun requestLocationAccess() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    /**
     * Shows an informing dialog about our need to have location access.
     *
     * @param needSettings `true` if OS does not allow showing permission dialog and
     *  user needs to go to Settings to grant permission.
     */
    private fun showPermissionRequestDialog(needSettings: Boolean) {
        DialogHelper.showInfoMessage(
            requireActivity(),
            getString(R.string.request_permission),
            getString(R.string.grant_permission)
        ) {
            if (!needSettings)
                requestLocationAccess()
            else openSettings()
        }
    }

    /** Opens App Info page in Settings to help user granting the permission. */
    private fun openSettings() {
        startActivity(
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts(
                    Constants.PACKAGE,
                    requireContext().packageName,
                    null
                )
            }
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

            if (distance > 100) {
                /*
                 * distance is more than 100 meters and the Internet is connected,
                 * so we get places data from server
                 */
                currentLocation?.let { loc ->
                    userLocationSharedPreferences.saveLocation(loc)
                    placesListViewModel.getServerPlaceList(loc)
                }
            } else if (placeList.isEmpty()) { // this means user has opened the app just now
                /*
                 * we obtain data from local Db just at the beginning of app opening.
                 * the distance with last session location of the user is less than 100 meters
                 * so we do not need to retrieve data from server.
                 */
                placesListViewModel.getLocalPlaceList()
            }
        } ?:
        /* it is the first time user uses the app, so we get places data from server */
        currentLocation?.let { loc ->
            userLocationSharedPreferences.saveLocation(loc)
            placesListViewModel.getServerPlaceList(loc)
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