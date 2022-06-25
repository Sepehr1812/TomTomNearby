package ir.divar.interviewtask.placeslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.divar.domain.place.model.Place
import ir.divar.interviewtask.R
import ir.divar.interviewtask.databinding.ItemPlaceBinding

class PlaceAdapter(
    private val placeList: List<Place>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val place = placeList[position]

            tvPlaceTitle.text = place.name
            tvPlaceCategory.text = place.categories
            tvPlaceLocalityDistance.text = root.context.getString(
                R.string.locality_distance_placeholder,
                place.address.region,
                place.distance
            )

            root.setOnClickListener { onItemClickListener.onItemClickListener(place) }
        }
    }

    override fun getItemCount() = placeList.size

    interface OnItemClickListener {
        fun onItemClickListener(place: Place)
    }
}