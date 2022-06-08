package ir.divar.interviewtask.placedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.divar.interviewtask.R
import ir.divar.interviewtask.databinding.FragmentPlaceDetailsBinding


/**
 * Represents the Fragment that contains details of a place.
 *
 * @author Sepi 6/4/22
 */
@AndroidEntryPoint
class PlaceDetailsFragment : Fragment(), View.OnClickListener {

    // region of properties
    private val placeDetailsViewModel by viewModels<PlaceDetailsViewModel>()
    private val args by navArgs<PlaceDetailsFragmentArgs>()
    private var _binding: FragmentPlaceDetailsBinding? = null
    private val binding get() = _binding!!

    // END of region of properties

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
    }

    fun initialView() {
        with(binding) {
            with(args.place) {
                tvPlaceDistance.text = distance.toString()
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