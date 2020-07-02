package com.example.geomob.ui.mainActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.geomob.R
import com.example.geomob.data.entity.Country
import com.example.geomob.data.entity.CountryWithAllAttributes
import java.util.*
import kotlin.collections.ArrayList


@Suppress("UNCHECKED_CAST")
class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryHolder>(), Filterable {

    private var countries: MutableList<CountryWithAllAttributes> = ArrayList()
    private var countriesFull: MutableList<CountryWithAllAttributes> = ArrayList()
    private var listener: OnItemClickListener? = null
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CountryHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_item, parent, false)
        return CountryHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: CountryHolder, position: Int) {
        val currentCountry = countries[position]
        holder.flagView.setImageResource(currentCountry.country.flag)
        holder.countryNameView.text = currentCountry.country.name
        val description = if (currentCountry.country.countryDescription.length > 20) {
            "${currentCountry.country.countryDescription.substring(0, 20)}..."
        } else {
            currentCountry.country.countryDescription
        }
        holder.descriptionView.text = description
        holder.favouriteView.setImageResource(
            if (currentCountry.country.favourite) {
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_favorite_border
            }
        )
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun setCountries(countries: List<CountryWithAllAttributes>) {
        this.countries = countries as MutableList<CountryWithAllAttributes>
        this.countriesFull = ArrayList(countries)
        notifyDataSetChanged()
    }

    fun getCountryAt(position: Int): CountryWithAllAttributes? {
        return countries[position]
    }

    inner class CountryHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal val flagView: ImageView = itemView.findViewById(R.id.flag_view)
        internal val countryNameView: TextView = itemView.findViewById(R.id.country_name_view)
        internal val favouriteView: ImageView = itemView.findViewById(R.id.favorite_view)
        internal val descriptionView: TextView = itemView.findViewById(R.id.description_view)

        init {

            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(countries[position])
                }
            }

            favouriteView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onFavoriteClick(countries[position].country)
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(country: CountryWithAllAttributes)
        fun onFavoriteClick(country: Country)

    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun getFilter(): Filter {
        return countriesFilter
    }

    private val countriesFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<CountryWithAllAttributes> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(countriesFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in countriesFull) {
                    if (item.country.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            countries.clear()
            countries.addAll(results.values as List<CountryWithAllAttributes>)
            notifyDataSetChanged()
        }
    }


}
