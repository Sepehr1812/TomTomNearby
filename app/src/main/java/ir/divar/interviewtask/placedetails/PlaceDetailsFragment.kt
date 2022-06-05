package ir.divar.interviewtask.placedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.divar.interviewtask.databinding.FragmentPlaceDetailsBinding


/**
 * Represents the Fragment that contains details of a place.
 *
 * @author Sepi 6/4/22
 */
@AndroidEntryPoint
class PlaceDetailsFragment : Fragment() {

    // region of properties
    private val placeDetailsViewModel by viewModels<PlaceDetailsViewModel>()
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
}