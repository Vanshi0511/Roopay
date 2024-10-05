package com.vanshika.roopay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.library.slider.SliderViewAdapter


class ImageSliderAdapter(private val context: Context, private val images: List<Any>) :
    SliderViewAdapter<ImageSliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.auto_image_slider, null)
        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        val image = images[position]

        // Handle URL images
        if (image is String) {
            val imageUrl = image
            if (imageUrl.isNotEmpty()) {
                Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.img) // Placeholder image
                    .error(R.drawable.image_2) // Error image
                    .into(viewHolder.photoView)
            } else {
                Glide.with(context).load(R.drawable.image_2).into(viewHolder.photoView)
            }
        } else if (image is Int) {
            Glide.with(context).load(image).into(viewHolder.photoView)
            viewHolder.photoView.setOnClickListener {
                // Optional: Define what happens when a drawable image is clicked
            }
        }
    }

    override fun getCount(): Int {
        return images.size
    }

    class SliderViewHolder(itemView: View) : ViewHolder(itemView) {
        var photoView: ImageView = itemView.findViewById(R.id.imageViewSlider)
    }
}
