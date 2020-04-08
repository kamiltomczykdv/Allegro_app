package com.example.sklepallegro.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sklepallegro.R
import com.example.sklepallegro.model.Offer
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.details_fragment, container, false)
        val offer: Offer? = requireArguments().getParcelable(MainActivity.argumentName)
        initUi(offer, view)
        return view
    }

    private fun initUi(offer: Offer?, view: View) {
        Picasso.with(activity)
            .load(offer?.thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(view.findViewById<ImageView>(R.id.item_fragment_photo_img))
        view.findViewById<TextView>(R.id.item_price_fragment_txv).text =
            offer?.price?.amount.toString() + " " + offer?.price?.currency
        view.findViewById<TextView>(R.id.item_description_txv).text =
            Html.fromHtml(offer?.description, Html.FROM_HTML_MODE_LEGACY)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.arrow)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}