package ir.divar.interviewtask.placedetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import ir.divar.interviewtask.R
import ir.divar.interviewtask.databinding.FragmentPlaceDetailsBinding
import ir.divar.interviewtask.util.toGmsLatLng


/**
 * Represents the Fragment that contains details of a place.
 *
 * @author Sepi 6/4/22
 */
@AndroidEntryPoint
class PlaceDetailsFragment : Fragment(), View.OnClickListener, OnMapReadyCallback {

    // region of properties
    private val placeDetailsViewModel by viewModels<PlaceDetailsViewModel>()
    private val args by navArgs<PlaceDetailsFragmentArgs>()
    private var _binding: FragmentPlaceDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    // END of region of properties

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)

        (childFragmentManager.findFragmentById(R.id.fcvPlaceMap) as SupportMapFragment)
            .getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
    }

    private fun initialView() {
        with(binding) {
            with(args.place) {
                tvPlaceDistance.text = getString(R.string.distance_placeholder, distance)
                tvPlaceCategory.text = categories
                tvPlaceTitle.text = name
                tvPlaceRegion.text = getString(
                    R.string.region_country_placeholder,
                    location.region,
                    location.country
                )
                tvPlaceAddress.text = location.formattedAddress

                imgPlaceBack.setOnClickListener(this@PlaceDetailsFragment)
            }
        }
    }

    private fun addPlaceMarker(latLng: LatLng) {
        map.addMarker(
            MarkerOptions().apply {
                position(latLng)
                icon(
                    BitmapDescriptorFactory.fromBitmap(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_pin,
                            requireContext().theme
                        )!!.toBitmap()
                    )
                )
            })
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        addPlaceMarker(args.place.geocode.toGmsLatLng())

        map.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            isMyLocationEnabled = true

            // move camera to current location bindings to display user and place location in a frame
            moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    LatLngBounds.Builder().apply {
                        include(args.place.geocode.toGmsLatLng())
                        include(args.currentLocation)
                    }.build(),
                    8
                )
            )

            uiSettings.apply {
                isMyLocationButtonEnabled = true
                isRotateGesturesEnabled = true
                isScrollGesturesEnabled = true
                isMapToolbarEnabled = true
                isZoomControlsEnabled = true
                isZoomGesturesEnabled = true
                isCompassEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.imgPlaceBack.id -> findNavController().navigateUp()
        }
    }
}