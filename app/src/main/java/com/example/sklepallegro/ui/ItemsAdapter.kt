package com.example.sklepallegro.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sklepallegro.R
import com.example.sklepallegro.model.Offer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.item_name_txv

class ItemsAdapter(
    private val context: Context,
    private val offerList: List<Offer>,
    private val clickListener: (Offer) -> Unit
) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_photo_img)
        val offerName: TextView = view.findViewById(R.id.item_name_txv)
        val offerPrice: TextView = view.findViewById(R.id.item_price_txv)
        fun bind(offer: Offer, click: (Offer) -> Unit) {
            itemView.item_name_txv.text = offer.name
            itemView.setOnClickListener {
                click(offer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )

    override fun getItemCount(): Int = offerList.size

    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        Picasso.with(context)
            .load(offerList[position].thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.image)
        holder.offerName.text = offerList[position].name
        holder.offerPrice.text =
            offerList[position].price.amount.toString() + " " + offerList[position].price.currency
        holder.bind(offerList[position], clickListener)
    }
}