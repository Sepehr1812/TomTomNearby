package ir.divar.interviewtask.placeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.divar.domain.place.model.Place
import ir.divar.interviewtask.databinding.FragmentPlacesListBinding


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

    // END of region of properties

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onItemClickListener(place: Place) {
        findNavController().navigate(
            PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(place)
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}