package com.scb.googleplace.adapter

import android.R.attr.data
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.googleplace.R
import com.scb.placemaps.models.Candidates


class AdapterRcPlaces (val candidates: List<Candidates>, val context : Context) : RecyclerView.Adapter<AdapterRcPlaces.PlacesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : AdapterRcPlaces.PlacesHolder {
        val inflatedView = parent.inflate(R.layout.item_rc_places, false)
        return PlacesHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return candidates.size

    }

    override fun onBindViewHolder(holder: AdapterRcPlaces.PlacesHolder, position: Int) {
        holder.nameTextView.text = candidates.get(position).name
        Glide.with(context).load(candidates.get(position).icon).into(holder.icon)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }


class PlacesHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
    // Your holder should contain and initialize a member variable
    // for any view that will be set as you render a row
    val nameTextView = itemView.findViewById<TextView>(R.id.name_place)
    val icon = itemView.findViewById<ImageView>(R.id.imageView)
}

}
